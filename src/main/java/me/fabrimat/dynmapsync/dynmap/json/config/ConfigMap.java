package me.fabrimat.dynmapsync.dynmap.json.config;

import com.google.gson.annotations.SerializedName;

public class ConfigMap {
    @SerializedName("nightandday")
    private boolean nightAndDay;
    private String shader;
    @SerializedName("compassview")
    private String compassView;
    private String prefix;
    @SerializedName("tilescale")
    private int tileScale;
    private String icon;
    private int scale;
    private int azimuth;
    private String type;
    private String title;
    @SerializedName("backgroundday")
    private String backgroundDay;
    @SerializedName("protected")
    private boolean protectedMap;
    @SerializedName("mapzoomout")
    private int mapZoomOut;
    private String perspective;
    @SerializedName("worldtomap")
    private double[] worldToMap;
    private float inclination;
    @SerializedName("image-format")
    private String imageFormat;
    private String lighting;
    @SerializedName("bigmap")
    private boolean bigMap;
    @SerializedName("maptoworld")
    private double[] mapToWorld;
    private String background;
    @SerializedName("boostzoom")
    private int boostZoom;
    private String name;
    @SerializedName("backgroundnight")
    private String backgroundNight;
    @SerializedName("mapzoomin")
    private int mapZoomIn;

    public ConfigMap(boolean nightAndDay, String shader, String compassView, String prefix, int tileScale, String icon, int scale, int azimuth, String type, String title, String backgroundDay, boolean protectedMap, int mapZoomOut, String perspective, double[] worldToMap, int inclination, String imageFormat, String lighting, boolean bigMap, double[] mapToWorld, String background, int boostZoom, String name, String backgroundNight, int mapZoomIn) {
        this.nightAndDay = nightAndDay;
        this.shader = shader;
        this.compassView = compassView;
        this.prefix = prefix;
        this.tileScale = tileScale;
        this.icon = icon;
        this.scale = scale;
        this.azimuth = azimuth;
        this.type = type;
        this.title = title;
        this.backgroundDay = backgroundDay;
        this.protectedMap = protectedMap;
        this.mapZoomOut = mapZoomOut;
        this.perspective = perspective;
        this.worldToMap = worldToMap;
        this.inclination = inclination;
        this.imageFormat = imageFormat;
        this.lighting = lighting;
        this.bigMap = bigMap;
        this.mapToWorld = mapToWorld;
        this.background = background;
        this.boostZoom = boostZoom;
        this.name = name;
        this.backgroundNight = backgroundNight;
        this.mapZoomIn = mapZoomIn;
    }

    public boolean isNightAndDay() {
        return nightAndDay;
    }

    public void setNightAndDay(boolean nightAndDay) {
        this.nightAndDay = nightAndDay;
    }

    public String getShader() {
        return shader;
    }

    public void setShader(String shader) {
        this.shader = shader;
    }

    public String getCompassView() {
        return compassView;
    }

    public void setCompassView(String compassView) {
        this.compassView = compassView;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getTileScale() {
        return tileScale;
    }

    public void setTileScale(int tileScale) {
        this.tileScale = tileScale;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(int azimuth) {
        this.azimuth = azimuth;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundDay() {
        return backgroundDay;
    }

    public void setBackgroundDay(String backgroundDay) {
        this.backgroundDay = backgroundDay;
    }

    public boolean isProtectedMap() {
        return protectedMap;
    }

    public void setProtectedMap(boolean protectedMap) {
        this.protectedMap = protectedMap;
    }

    public int getMapZoomOut() {
        return mapZoomOut;
    }

    public void setMapZoomOut(int mapZoomOut) {
        this.mapZoomOut = mapZoomOut;
    }

    public String getPerspective() {
        return perspective;
    }

    public void setPerspective(String perspective) {
        this.perspective = perspective;
    }

    public double[] getWorldToMap() {
        return worldToMap;
    }

    public void setWorldToMap(double[] worldToMap) {
        this.worldToMap = worldToMap;
    }

    public float getInclination() {
        return inclination;
    }

    public void setInclination(float inclination) {
        this.inclination = inclination;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    public String getLighting() {
        return lighting;
    }

    public void setLighting(String lighting) {
        this.lighting = lighting;
    }

    public boolean isBigMap() {
        return bigMap;
    }

    public void setBigMap(boolean bigMap) {
        this.bigMap = bigMap;
    }

    public double[] getMapToWorld() {
        return mapToWorld;
    }

    public void setMapToWorld(double[] mapToWorld) {
        this.mapToWorld = mapToWorld;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getBoostZoom() {
        return boostZoom;
    }

    public void setBoostZoom(int boostZoom) {
        this.boostZoom = boostZoom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundNight() {
        return backgroundNight;
    }

    public void setBackgroundNight(String backgroundNight) {
        this.backgroundNight = backgroundNight;
    }

    public int getMapZoomIn() {
        return mapZoomIn;
    }

    public void setMapZoomIn(int mapZoomIn) {
        this.mapZoomIn = mapZoomIn;
    }
}