package me.fabrimat.dynamicmapsync.dynmap.command.sub.sync;

import me.fabrimat.dynamicmapsync.DynamicMapSync;
import me.fabrimat.dynamicmapsync.config.DynmapConfigSection;
import me.fabrimat.dynamicmapsync.dynmap.DynmapJson;
import me.fabrimat.dynamicmapsync.dynmap.DynmapManager;
import me.fabrimat.dynamicmapsync.dynmap.utils.DynmapUtils;
import me.fabrimat.dynamicmapsync.dynmap.SourceMap;
import me.fabrimat.dynamicmapsync.dynmap.command.sub.DynmapSubCommand;
import me.fabrimat.dynamicmapsync.dynmap.json.world.DynmapPlayer;
import me.fabrimat.dynamicmapsync.dynmap.json.DynmapWorldFile;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.step.Step;

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
        DynmapManager dynmapManager = DynamicMapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getWorldFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock world file");
        }

        DynmapConfigSection config = DynamicMapSync.getInstance().getMainConfig().getDynmapConfig();
        for (String worldName : dynmapManager.getWorlds().keySet()) {
            DynmapJson destinationJson = new DynmapJson(config.getDestinationPath(),
                    DynmapJson.FileType.WORLD,
                    DynmapUtils.rewriteWorldName(worldName, config.getWorldRewrites()),
                    true);

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
                        DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.WORLD, worldName, false);
                        if (sourceJson.getFile().exists()) {
                            players.addAll(Arrays.stream(((DynmapWorldFile) sourceJson.getDynmapFile()).getPlayers()).toList());
                        }
                    }
                }
            }

            for (Map.Entry<String, SourceMap> entry : sourceMaps.entrySet()) {
                SourceMap sourceMap = entry.getValue();
                DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.WORLD, worldName, false);
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

