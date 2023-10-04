package me.fabrimat.dynmapsync.dynmap.json.update;

public class Update {
    public final String generatedBy = "DynmapSync";
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
