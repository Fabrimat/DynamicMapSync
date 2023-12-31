package me.fabrimat.dynamicmapsync.dynmap.json.world.update.marker;

import com.google.gson.annotations.SerializedName;

public class PolyLineMarkerUpdated extends MarkerComponentMessage {
    private String msg;
    private double[] x;
    private double[] y;
    private double[] z;
    private int weight;
    private double opacity;
    private String color;
    private String id;
    private String label;
    private String set;
    private String desc;
    @SerializedName("minzoom")
    private int minZoom;
    @SerializedName("maxzoom")
    private int maxZoom;
    private boolean markup;

    public PolyLineMarkerUpdated(long timestamp, String msg, double[] x, double[] y, double[] z, int weight, double opacity, String color, String id, String label, String set, String desc, int minZoom, int maxZoom, boolean markup) {
        super(timestamp);
        this.msg = msg;
        this.x = x;
        this.y = y;
        this.z = z;
        this.weight = weight;
        this.opacity = opacity;
        this.color = color;
        this.id = id;
        this.label = label;
        this.set = set;
        this.desc = desc;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.markup = markup;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double[] getY() {
        return y;
    }

    public void setY(double[] y) {
        this.y = y;
    }

    public double[] getZ() {
        return z;
    }

    public void setZ(double[] z) {
        this.z = z;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getMinZoom() {
        return minZoom;
    }

    public void setMinZoom(int minZoom) {
        this.minZoom = minZoom;
    }

    public int getMaxZoom() {
        return maxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        this.maxZoom = maxZoom;
    }

    public boolean isMarkup() {
        return markup;
    }

    public void setMarkup(boolean markup) {
        this.markup = markup;
    }
}
