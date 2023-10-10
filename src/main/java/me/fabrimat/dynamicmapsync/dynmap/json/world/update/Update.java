package me.fabrimat.dynamicmapsync.dynmap.json.world.update;

public class Update{
    public final String generatedBy = "DynamicMapSync";
    private long timestamp;

    public Update(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
