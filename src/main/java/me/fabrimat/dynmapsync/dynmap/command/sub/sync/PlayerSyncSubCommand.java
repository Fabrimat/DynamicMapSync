package me.fabrimat.dynmapsync.dynmap.command.sub.sync;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapJson;
import me.fabrimat.dynmapsync.dynmap.DynmapManager;
import me.fabrimat.dynmapsync.dynmap.utils.DynmapUtils;
import me.fabrimat.dynmapsync.dynmap.SourceMap;
import me.fabrimat.dynmapsync.dynmap.command.sub.DynmapSubCommand;
import me.fabrimat.dynmapsync.dynmap.json.world.DynmapPlayer;
import me.fabrimat.dynmapsync.dynmap.json.DynmapWorldFile;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class PlayerSyncSubCommand implements DynmapSubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        DynmapManager dynmapManager = DynmapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getWorldFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock world file");
        }

        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        for (String worldName : dynmapManager.getWorlds()) {
            DynmapJson destinationJson = new DynmapJson(config.getDestinationPath(), DynmapJson.FileType.WORLD, DynmapUtils.rewriteWorldName(worldName, config.getWorldRewrites()));

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
                        DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.WORLD, worldName);
                        if (sourceJson.getFile().exists()) {
                            players.addAll(Arrays.stream(((DynmapWorldFile) sourceJson.getDynmapFile()).getPlayers()).toList());
                        }
                    }
                }
            }

            for (Map.Entry<String, SourceMap> entry : sourceMaps.entrySet()) {
                SourceMap sourceMap = entry.getValue();
                DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.WORLD, worldName);
                if (sourceJson.getFile().exists()) {
                    players.addAll(Arrays.stream(((DynmapWorldFile) sourceJson.getDynmapFile()).getPlayers()).toList());
                }
            }

            players.forEach(player ->
                    player.setWorld(DynmapUtils.rewriteWorldName(player.getWorld(), config.getWorldRewrites())));

            ((DynmapWorldFile) destinationJson.getDynmapFile()).setPlayers(players.toArray(new DynmapPlayer[0]));
            destinationJson.writeFile();
        }
        dynmapManager.setTimestamp("playerSync", Timestamp.from(Instant.now()));
        dynmapManager.getWorldFileLock().unlock();
        return true;
    }
}

