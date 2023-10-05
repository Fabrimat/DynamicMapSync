package me.fabrimat.dynmapsync.dynmap.command;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapJson;
import me.fabrimat.dynmapsync.dynmap.DynmapManager;
import me.fabrimat.dynmapsync.dynmap.DynmapUtils;
import me.fabrimat.dynmapsync.dynmap.SourceMap;
import me.fabrimat.dynmapsync.dynmap.json.DynmapPlayer;
import me.fabrimat.dynmapsync.dynmap.json.DynmapWorld;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DynmapCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) throws IOException, InterruptedException {
        if (args != null && args.length > 0) {
            switch (args[0].toUpperCase(Locale.ROOT)) {
                case "SYNC":
                    switch (args[1].toUpperCase(Locale.ROOT)) {
                        case "PLAYERS":
                            syncPlayers();
                            break;
                        case "CONFIG":
                            // TODO
                        case "TILES":
                            // TODO
                        case "MARKERS":
                            // TODO
                        default:
                            return false;
                    }
                    break;
                case "CLEAR":
                    // TODO
                default:
                    return false;
            }
        }
        return true;
    }

    private void syncConfig() throws InterruptedException, IOException {
        DynmapManager dynmapManager = DynmapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getConfigFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock config file");
        }
    }

    private void syncPlayers() throws IOException, InterruptedException {
        DynmapManager dynmapManager = DynmapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getWorldFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock world file");
        }

        for (String worldName : dynmapManager.getWorlds()) {
            DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
            DynmapJson dynmapJson = new DynmapJson(config.getDestinationPath(), DynmapJson.FileType.WORLD, worldName);

            Map<String, SourceMap> sourceMaps = config.getSourceMaps();
            sourceMaps = sourceMaps.entrySet().stream()
                    .filter(entry -> entry.getValue().syncPlayers())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            List<String> priority = config.getPriority();

            List<DynmapPlayer> players = new ArrayList<>();
            if (!priority.isEmpty()) {
                for (String map : priority) {
                    if (sourceMaps.containsKey(map)) {
                        SourceMap sourceMap = sourceMaps.get(map);
                        sourceMaps.remove(map);
                        DynmapJson dynmapJsonSource = new DynmapJson(sourceMap.path(), DynmapJson.FileType.WORLD, worldName);
                        if (dynmapJsonSource.getFile().exists()) {
                            players.addAll(Arrays.stream(((DynmapWorld) dynmapJsonSource.getDynmapFile()).getPlayers()).toList());
                        }
                    }
                }
            }

            for (Map.Entry<String, SourceMap> entry : sourceMaps.entrySet()) {
                SourceMap sourceMap = entry.getValue();
                DynmapJson dynmapJsonSource = new DynmapJson(sourceMap.path(), DynmapJson.FileType.WORLD, worldName);
                if (dynmapJsonSource.getFile().exists()) {
                    players.addAll(Arrays.stream(((DynmapWorld) dynmapJsonSource.getDynmapFile()).getPlayers()).toList());
                }
            }

            players.forEach(player ->
                    player.setWorld(DynmapUtils.rewriteWorldName(player.getWorld(), config.getWorldRewrites())));

            ((DynmapWorld) dynmapJson.getDynmapFile()).setPlayers(players.toArray(new DynmapPlayer[0]));
            dynmapJson.writeFile();
        }
        dynmapManager.setTimestamp("playerSync", Timestamp.from(Instant.now()));
        dynmapManager.getWorldFileLock().unlock();
    }

    @Override
    public String getCommand() {
        return "DYNMAP";
    }
}
