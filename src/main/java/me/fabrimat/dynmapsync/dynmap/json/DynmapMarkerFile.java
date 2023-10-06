package me.fabrimat.dynmapsync.dynmap.json;

import me.fabrimat.dynmapsync.dynmap.json.marker.DynmapMarkerSet;

import java.util.Map;

public class DynmapMarkerFile extends DynmapFile {
    private Map<String, DynmapMarkerSet> sets;

    public DynmapMarkerFile(long timestamp, Map<String, DynmapMarkerSet> sets) {
        super(timestamp);
        this.sets = sets;
    }

    public DynmapMarkerFile() {
    }

    public Map<String, DynmapMarkerSet> getSets() {
        return sets;
    }

    public void setSets(Map<String, DynmapMarkerSet> sets) {
        this.sets = sets;
    }
}
