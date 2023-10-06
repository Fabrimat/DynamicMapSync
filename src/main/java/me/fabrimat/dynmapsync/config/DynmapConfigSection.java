package me.fabrimat.dynmapsync.config;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.dynmap.SourceMap;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynmapConfigSection {
    private final Configuration config;

    private SyncMode syncMode;
    private Map<String, String> worldRewrites;
    private final Map<String, SourceMap> sourceMaps = new HashMap<>();
    private List<String> priority = new ArrayList<>();
    private Path destinationPath;
    private String cwebpPath;
    private String dwebpPath;
    private int webpQuality;
    private boolean webpLossless;
    private int jpgQuality;

    DynmapConfigSection(Configuration config) {
        this.config = config;
        loadConfig();
    }

    private void loadConfig() {
        try {
            syncMode = SyncMode.valueOf(config.getString("sync-mode", "MOVE").toUpperCase());
        } catch (IllegalArgumentException e) {
            syncMode = SyncMode.MOVE;
        }
        worldRewrites = config.getSection("world-name-rewrite").getKeys().stream()
                .collect(HashMap::new, (map, key) -> map.put(key, config.getSection("world-name-rewrite").getString(key)), HashMap::putAll);
        for (String key : config.getSection("source-maps").getKeys()) {
            Configuration section = config.getSection("source-maps").getSection(key);
            SourceMap map = new SourceMap(Paths.get(section.getString("path")), section.getBoolean("sync-players", true),
                    section.getBoolean("sync-markers", true), section.getBoolean("sync-tiles", true), section.getBoolean("sync-daytime", true));
            sourceMaps.put(key, map);
        }
        priority = config.getStringList("priority");
        destinationPath = Paths.get(config.getString("destination-path"));
        Preconditions.checkNotNull(destinationPath, "Destination path cannot be null");
        this.setCwebpPath(config.getString("webp.cwebp-path", "/usr/bin/cwebp"));
        this.setDwebpPath(config.getString("webp.dwebp-path", "/usr/bin/dwebp"));
        this.setWebpLossless(config.getBoolean("webp.lossless", false));
        this.setWebpQuality(config.getInt("webp.quality", 75));
        this.setJpgQuality(config.getInt("jpg.quality", 75));
    }

    public SyncMode getSyncMode() {
        return syncMode;
    }

    public Map<String, String> getWorldRewrites() {
        return worldRewrites;
    }

    public Map<String, SourceMap> getSourceMaps() {
        return sourceMaps;
    }

    public List<String> getPriority() {
        return priority;
    }

    public Path getDestinationPath() {
        return destinationPath;
    }

    public String getCwebpPath() {
        return cwebpPath;
    }

    public void setCwebpPath(String cwebpPath) {
        this.cwebpPath = cwebpPath;
    }

    public String getDwebpPath() {
        return dwebpPath;
    }

    public void setDwebpPath(String dwebpPath) {
        this.dwebpPath = dwebpPath;
    }

    public int getWebpQuality() {
        return webpQuality;
    }

    public void setWebpQuality(int webpQuality) {
        this.webpQuality = webpQuality;
    }

    public boolean isWebpLossless() {
        return webpLossless;
    }

    public void setWebpLossless(boolean webpLossless) {
        this.webpLossless = webpLossless;
    }

    public int getJpgQuality() {
        return jpgQuality;
    }

    public void setJpgQuality(int jpgQuality) {
        this.jpgQuality = jpgQuality;
    }

    public enum SyncMode {
        COPY, MOVE
    }
}
