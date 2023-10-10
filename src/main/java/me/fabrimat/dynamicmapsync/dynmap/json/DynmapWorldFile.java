package me.fabrimat.dynamicmapsync.dynmap.json;

import com.google.gson.annotations.SerializedName;
import me.fabrimat.dynamicmapsync.dynmap.json.world.DynmapPlayer;
import me.fabrimat.dynamicmapsync.dynmap.json.world.update.Update;

public class DynmapWorldFile extends DynmapFile {
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

    public DynmapWorldFile(long timestamp, int currentCount, boolean hasStorm, DynmapPlayer[] players, boolean isThundering, long serverTime, long configHash, Update[] updates) {
        super(timestamp);
        this.currentCount = currentCount;
        this.hasStorm = hasStorm;
        this.players = players;
        this.isThundering = isThundering;
        this.serverTime = serverTime;
        this.configHash = configHash;
        this.updates = updates;
    }

    public DynmapWorldFile() {

    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public boolean isHasStorm() {
        return hasStorm;
    }

    public void setHasStorm(boolean hasStorm) {
        this.hasStorm = hasStorm;
    }

    public DynmapPlayer[] getPlayers() {
        return players;
    }

    public void setPlayers(DynmapPlayer[] players) {
        this.players = players;
    }

    public boolean isThundering() {
        return isThundering;
    }

    public void setThundering(boolean thundering) {
        isThundering = thundering;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public long getConfigHash() {
        return configHash;
    }

    public void setConfigHash(long configHash) {
        this.configHash = configHash;
    }

    public Update[] getUpdates() {
        return updates;
    }

    public void setUpdates(Update[] updates) {
        this.updates = updates;
    }
}
