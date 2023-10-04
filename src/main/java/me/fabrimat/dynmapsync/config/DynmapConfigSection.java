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

    DynmapConfigSection(Configuration config) {
        this.config = config;
        loadConfig();
    }

    private void loadConfig() {
        try {
            syncMode = SyncMode.valueOf(config.getString("sync_mode", "MOVE").toUpperCase());
        } catch (IllegalArgumentException e) {
            syncMode = SyncMode.MOVE;
        }
        worldRewrites = config.getSection("world_name_rewrite").getKeys().stream()
                .collect(HashMap::new, (map, key) -> map.put(key, config.getSection("world_name_rewrite").getString(key)), HashMap::putAll);
        for (String key : config.getSection("source_maps").getKeys()) {
            Configuration section = config.getSection("source_maps").getSection(key);
            SourceMap map = new SourceMap(Paths.get(section.getString("path")), section.getBoolean("sync_players", true),
                    section.getBoolean("sync_markers", true), section.getBoolean("sync_tiles", true), section.getBoolean("sync_daytime", true));
            sourceMaps.put(key, map);
        }
        priority = config.getStringList("priority");
        destinationPath = Paths.get(config.getString("destination_path"));
        Preconditions.checkNotNull(destinationPath, "Destination path cannot be null");
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

    public enum SyncMode {
        COPY, MOVE
    }
}
