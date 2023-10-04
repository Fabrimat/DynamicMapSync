package me.fabrimat.dynmapsync.dynmap.json;

import com.google.gson.annotations.SerializedName;
import me.fabrimat.dynmapsync.dynmap.json.update.Update;

public class DynmapWorld {
    @SerializedName("currentcount")
    private int currentCount;
    private boolean hasStorm;
    private DynmapPlayer[] players = {};
    private boolean isThundering;
    @SerializedName("servertime")
    private long serverTime;
    @SerializedName("configHash")
    private long configHash;
    private Update[] updates = {};

}
