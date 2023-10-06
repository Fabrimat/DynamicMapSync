package me.fabrimat.dynmapsync.dynmap.json.marker;

import com.google.gson.annotations.SerializedName;

public class DynmapMarker {
    private String label;
    private String markup;
    private String desc;
    private String icon;
    @SerializedName("minzoom")
    private String minZoom;
    @SerializedName("maxzoom")
    private String maxZoom;
    private double x;
    private double y;
    private double z;
    private String world;
    private int weight;

    public DynmapMarker(String label, String markup, String desc, String icon, String minZoom, String maxZoom, double x, double y, double z, String world, int weight) {
        this.label = label;
        this.markup = markup;
        this.desc = desc;
        this.icon = icon;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.weight = weight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(String minZoom) {
        this.minZoom = minZoom;
    }

    public String getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(String maxZoom) {
        this.maxZoom = maxZoom;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
