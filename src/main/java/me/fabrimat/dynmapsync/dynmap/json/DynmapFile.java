package me.fabrimat.dynmapsync.dynmap.json;

import java.time.Instant;

public abstract class DynmapFile {
    private long timestamp;

    public DynmapFile(long timestamp) {
        this.timestamp = timestamp;
    }

    public DynmapFile() {
        timestamp = Instant.now().toEpochMilli();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
