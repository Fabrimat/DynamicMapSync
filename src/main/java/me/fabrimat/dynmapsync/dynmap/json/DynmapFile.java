package me.fabrimat.dynmapsync.dynmap.json;

public abstract class DynmapFile {
    private long timestamp;

    public DynmapFile(long timestamp) {
        this.timestamp = timestamp;
    }

    public DynmapFile() {}

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
