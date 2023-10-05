package me.fabrimat.dynmapsync.dynmap.json;

import me.fabrimat.dynmapsync.dynmap.json.marker.DynmapMarkerSet;

import java.util.Map;

public class DynmapMarkerFile implements DynmapFile {
    private long timestamp;
    private Map<String, DynmapMarkerSet> sets;
}
