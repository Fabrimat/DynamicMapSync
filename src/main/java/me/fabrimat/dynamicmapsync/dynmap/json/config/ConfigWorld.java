package me.fabrimat.dynamicmapsync.dynmap.json.config;

import com.google.gson.annotations.SerializedName;

public class ConfigWorld {
    @SerializedName("sealevel")
    private int seaLevel;
    @SerializedName("protected")
    private boolean protectedWorld;
    @SerializedName("extrazoomout")
    private int extraZoomOut;
    private String name;
    private String title;
    private Center center;

    private ConfigMap[] maps;
    @SerializedName("worldheight")
    private int worldHeight;

    public ConfigWorld(int seaLevel, boolean protectedWorld, int extraZoomOut, String name, String title, Center center, ConfigMap[] maps, int worldHeight) {
        this.seaLevel = seaLevel;
        this.protectedWorld = protectedWorld;
        this.extraZoomOut = extraZoomOut;
        this.name = name;
        this.title = title;
        this.center = center;
        this.maps = maps;
        this.worldHeight = worldHeight;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public boolean isProtectedWorld() {
        return protectedWorld;
    }

    public void setProtectedWorld(boolean protectedWorld) {
        this.protectedWorld = protectedWorld;
    }

    public int getExtraZoomOut() {
        return extraZoomOut;
    }

    public void setExtraZoomOut(int extraZoomOut) {
        this.extraZoomOut = extraZoomOut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConfigMap[] getMaps() {
        return maps;
    }

    public void setMaps(ConfigMap[] maps) {
        this.maps = maps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
    }

    public static class Center {
        private long x;
        private long y;
        private long z;

        public Center(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getX() {
            return x;
        }

        public void setX(long x) {
            this.x = x;
        }

        public long getY() {
            return y;
        }

        public void setY(long y) {
            this.y = y;
        }

        public long getZ() {
            return z;
        }

        public void setZ(long z) {
            this.z = z;
        }
    }
}
