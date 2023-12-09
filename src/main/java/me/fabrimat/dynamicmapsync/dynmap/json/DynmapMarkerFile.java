package me.fabrimat.dynamicmapsync.dynmap.json;

import me.fabrimat.dynamicmapsync.dynmap.json.marker.DynmapMarkerSet;

import java.util.HashMap;
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

    public void initSets() {
        if (sets == null) {
            sets = new HashMap<>();
        }
    }
}
