package me.fabrimat.dynmapsync.dynmap.command.sub.sync;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapManager;
import me.fabrimat.dynmapsync.dynmap.ImageFormat;
import me.fabrimat.dynmapsync.dynmap.SourceMap;
import me.fabrimat.dynmapsync.dynmap.command.sub.DynmapSubCommand;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigMap;
import me.fabrimat.dynmapsync.dynmap.utils.ImageUtils;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TileSyncSubCommand implements DynmapSubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        DynmapManager dynmapManager = DynmapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getTileFilesLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock tile files");
        }

        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        Map<String, SourceMap> sourceMaps = config.getSourceMaps();

        for (Map.Entry<String, Set<ConfigMap>> entry : dynmapManager.getWorlds().entrySet()) {
            for (String priority : config.getPriority()) {
                SourceMap sMap = sourceMaps.get(priority);
                if (sMap.syncTiles()) {
                    for (ConfigMap map : entry.getValue()) {
                        copyTiles(entry.getKey(), priority, sMap, map, config.getDestinationPath());
                    }
                }
            }
            for (Map.Entry<String, SourceMap> sourceMapEntry : sourceMaps.entrySet()) {
                SourceMap sMap = sourceMapEntry.getValue();
                if (sMap.syncTiles()) {
                    for (ConfigMap map : entry.getValue()) {
                        copyTiles(entry.getKey(), sourceMapEntry.getKey(), sMap, map, config.getDestinationPath());
                    }
                }
            }
        }

        dynmapManager.getTileFilesLock().unlock();
        return true;
    }
    private void copyTiles(String world, String sourceName, SourceMap source, ConfigMap map, Path destination) throws IOException {
        Path sourcePath = source.path().resolve("tiles");
        Path destinationPath = destination.resolve("tiles");

        Path worldPath = sourcePath.resolve(world);
        if (!Files.exists(worldPath) || !Files.isDirectory(worldPath)) {
            return;
        }

        Path mapPath = worldPath.resolve(map.getPrefix());
        if (!Files.exists(mapPath) || !Files.isDirectory(mapPath)) {
            return;
        }

        Files.walkFileTree(mapPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) throws IOException {
                Path relativePath = mapPath.relativize(filePath);
                Path destinationFilePath = destinationPath.resolve(world).resolve(map.getPrefix()).resolve(relativePath);

                if (filePath.toFile().isDirectory()) {
                    if (!Files.exists(destinationFilePath)) {
                        Files.createDirectories(destinationFilePath);
                    }
                } else {
                    processImages(filePath, destinationFilePath, sourceName);
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void processImages(Path source, Path destination, String sourceName) throws IOException {
        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();

        BufferedImage topImg = ImageUtils.loadImage(source);
        if (destination.toFile().exists()) {
            BufferedImage bottomImg = ImageUtils.loadImage(destination);

            List<String> priority = config.getPriority();

            Object destOrigin = Files.getAttribute(destination, "user.dynmapsync.source");
            if (destOrigin != null) {
                String destOriginValue = destOrigin.toString();
                if (priority.indexOf(sourceName) > priority.indexOf(destOriginValue)) {
                    BufferedImage tmp = topImg;
                    topImg = bottomImg;
                    bottomImg = tmp;
                    sourceName = destOriginValue;
                }
            }
            if (topImg != null && bottomImg != null) topImg = ImageUtils.mergeImages(topImg, bottomImg);
            if (topImg != null && ImageFormat.getFormat(destination) != null) {
                ImageUtils.writeImage(destination, topImg, ImageFormat.getFormat(destination));
            }
            if (config.getSyncMode().equals(DynmapConfigSection.SyncMode.MOVE)) {
                Files.delete(source);
            }
        } else {
            if (config.getSyncMode().equals(DynmapConfigSection.SyncMode.COPY)) {
                Files.copy(source, destination);
            } else {
                Files.move(source, destination);
            }
        }
        Files.setAttribute(destination, "user.dynmapsync.source", sourceName);
    }
}
