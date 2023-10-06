package me.fabrimat.dynmapsync.dynmap.command.sub.sync;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapJson;
import me.fabrimat.dynmapsync.dynmap.DynmapManager;
import me.fabrimat.dynmapsync.dynmap.DynmapUtils;
import me.fabrimat.dynmapsync.dynmap.SourceMap;
import me.fabrimat.dynmapsync.dynmap.command.sub.DynmapSubCommand;
import me.fabrimat.dynmapsync.dynmap.json.DynmapMarkerFile;
import me.fabrimat.dynmapsync.dynmap.json.marker.DynmapMarkerSet;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

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
        DynmapManager dynmapManager = DynmapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getMarkersFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock marker file");
        }

        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        for (String worldName : dynmapManager.getWorlds()) {
            DynmapJson destinationJson = new DynmapJson(config.getDestinationPath(), DynmapJson.FileType.MARKERS, DynmapUtils.rewriteWorldName(worldName, config.getWorldRewrites()));

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
                        DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.MARKERS, worldName);
                        if (sourceJson.getFile().exists()) {
                            copyValues(sourceJson, destinationJson);
                        }
                    }
                }
            }

            destinationJson.writeFile();
        }
        dynmapManager.setTimestamp("markersSync", Timestamp.from(Instant.now()));
        dynmapManager.getWorldFileLock().unlock();

        return true;
    }

    private void copyValues(DynmapJson source, DynmapJson destination) {
        Preconditions.checkArgument(source.getFileType().equals(DynmapJson.FileType.MARKERS), "Source must be a marker file");
        Preconditions.checkArgument(destination.getFileType().equals(DynmapJson.FileType.MARKERS), "Destination must be a marker file");

        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        DynmapMarkerFile sConfig = (DynmapMarkerFile) source.getDynmapFile();
        DynmapMarkerFile dConfig = (DynmapMarkerFile) destination.getDynmapFile();

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
