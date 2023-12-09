package me.fabrimat.dynamicmapsync.dynmap.command.sub.sync;

import com.google.common.base.Preconditions;
import me.fabrimat.dynamicmapsync.AppServer;
import me.fabrimat.dynamicmapsync.DynamicMapSync;
import me.fabrimat.dynamicmapsync.config.DynmapConfigSection;
import me.fabrimat.dynamicmapsync.dynmap.DynmapJson;
import me.fabrimat.dynamicmapsync.dynmap.DynmapManager;
import me.fabrimat.dynamicmapsync.dynmap.utils.DynmapUtils;
import me.fabrimat.dynamicmapsync.dynmap.SourceMap;
import me.fabrimat.dynamicmapsync.dynmap.command.sub.DynmapSubCommand;
import me.fabrimat.dynamicmapsync.dynmap.json.DynmapMarkerFile;
import me.fabrimat.dynamicmapsync.dynmap.json.marker.DynmapMarkerSet;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.step.Step;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MarkerSyncSubCommand implements DynmapSubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        DynmapManager dynmapManager = DynamicMapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getMarkersFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock marker file");
        }

        DynmapConfigSection config = DynamicMapSync.getInstance().getMainConfig().getDynmapConfig();
        for (String worldName : dynmapManager.getWorlds().keySet()) {
            DynmapJson destinationJson = new DynmapJson(config.getDestinationPath(),
                    DynmapJson.FileType.MARKERS,
                    DynmapUtils.rewriteWorldName(worldName, config.getWorldRewrites()),
                    true);

            Map<String, SourceMap> sourceMaps = config.getSourceMaps();
            sourceMaps = sourceMaps.entrySet().stream()
                    .filter(entry -> entry.getValue().syncMarkers())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

            List<String> priority = config.getPriority();

            if (!priority.isEmpty()) {
                for (String map : priority) {
                    if (sourceMaps.containsKey(map)) {
                        SourceMap sourceMap = sourceMaps.get(map);
                        sourceMaps.remove(map);
                        attemptCopy(destinationJson, sourceMap, map, worldName);
                    }
                }
            }

            for (Map.Entry<String, SourceMap> entry : sourceMaps.entrySet()) {
                SourceMap sourceMap = entry.getValue();
                attemptCopy(destinationJson, sourceMap, entry.getKey(), worldName);
            }

            destinationJson.writeFile();
        }
        dynmapManager.setTimestamp("markersSync", Timestamp.from(Instant.now()));
        dynmapManager.getWorldFileLock().unlock();

        return true;
    }

    private void attemptCopy(DynmapJson destinationJson, SourceMap sourceMap, String sourceName, String worldName) {
        try {
            DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.MARKERS, worldName, false);
            if (sourceJson.getFile().exists()) {
                copyValues(sourceJson, destinationJson);
            }
        } catch (IOException | IllegalStateException e) {
            AppServer.getInstance().getLogger().warning("Could not copy values from %1 - ".replace("%1", sourceName) + e.getMessage());
        }
    }

    private void copyValues(DynmapJson source, DynmapJson destination) {
        Preconditions.checkArgument(source.getFileType().equals(DynmapJson.FileType.MARKERS), "Source must be a marker file");
        Preconditions.checkArgument(destination.getFileType().equals(DynmapJson.FileType.MARKERS), "Destination must be a marker file");

        DynmapConfigSection config = DynamicMapSync.getInstance().getMainConfig().getDynmapConfig();
        DynmapMarkerFile sConfig = (DynmapMarkerFile) source.getDynmapFile();
        DynmapMarkerFile dConfig = (DynmapMarkerFile) destination.getDynmapFile();

        sConfig.initSets();
        dConfig.initSets();

        sConfig.getSets().forEach((setKey, setValue) -> {
            setValue.getAreas().forEach((key, area) -> area.setWorld(DynmapUtils.rewriteWorldName(area.getWorld(), config.getWorldRewrites())));
            setValue.getLines().forEach((key, line) -> line.setWorld(DynmapUtils.rewriteWorldName(line.getWorld(), config.getWorldRewrites())));
            setValue.getCircles().forEach((key, circle) -> circle.setWorld(DynmapUtils.rewriteWorldName(circle.getWorld(), config.getWorldRewrites())));
            setValue.getMarkers().forEach((key, marker) -> marker.setWorld(DynmapUtils.rewriteWorldName(marker.getWorld(), config.getWorldRewrites())));
        });
        sConfig.getSets().forEach((key, value) -> {
            if (!dConfig.getSets().containsKey(key)) {
                dConfig.getSets().put(key, value);
            } else {
                DynmapMarkerSet dSet = dConfig.getSets().get(key);
                value.getAreas().forEach((areaKey, area) -> {
                    if (!dSet.getAreas().containsKey(areaKey)) {
                        dSet.getAreas().put(areaKey, area);
                    }
                });
                value.getLines().forEach((lineKey, line) -> {
                    if (!dSet.getLines().containsKey(lineKey)) {
                        dSet.getLines().put(lineKey, line);
                    }
                });
                value.getCircles().forEach((circleKey, circle) -> {
                    if (!dSet.getCircles().containsKey(circleKey)) {
                        dSet.getCircles().put(circleKey, circle);
                    }
                });
                value.getMarkers().forEach((markerKey, marker) -> {
                    if (!dSet.getMarkers().containsKey(markerKey)) {
                        dSet.getMarkers().put(markerKey, marker);
                    }
                });
            }
        });
    }
}
