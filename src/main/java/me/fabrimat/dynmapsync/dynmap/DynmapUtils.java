package me.fabrimat.dynmapsync.dynmap;

import java.util.Map;

public class DynmapUtils {

    public static String rewriteWorldName(String world, Map<String, String> rewrites) {
        if (rewrites.containsKey(world)) {
            return rewrites.get(world);
        }
        return world;
    }
}
