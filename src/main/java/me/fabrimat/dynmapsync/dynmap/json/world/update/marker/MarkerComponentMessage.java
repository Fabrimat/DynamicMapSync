package me.fabrimat.dynmapsync.dynmap.json.world.update.marker;

import com.google.gson.annotations.SerializedName;
import me.fabrimat.dynmapsync.dynmap.json.world.update.ComponentMessage;

public class MarkerComponentMessage extends ComponentMessage {
    @SerializedName("ctype")
    public final String cType = "markers";

    public MarkerComponentMessage(long timestamp) {
        super(timestamp);
    }
}
