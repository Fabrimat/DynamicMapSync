package me.fabrimat.dynmapsync.dynmap.json.update;

import com.google.gson.*;

import java.lang.reflect.Type;

public class Update{
    public final String generatedBy = "DynmapSync";
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
