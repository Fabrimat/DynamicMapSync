package me.fabrimat.dynmapsync.dynmap.json.update.marker;

import com.google.gson.annotations.SerializedName;

public class AreaMarkerUpdated extends MarkerComponentMessage {
    private String msg;
    @SerializedName("ytop")
    private double yTop;
    @SerializedName("ybottom")
    private double yBottom;
    private double[] x;
    private double[] z;
    private int weight;
    private double opacity;
    private String color;
    @SerializedName("fillopacity")
    private double fillOpacity;
    @SerializedName("fillcolor")
    private String fillColor;
    private String id;
    private String label;
    private String set;
    private String desc;
    @SerializedName("minzoom")
    private int minZoom;
    @SerializedName("maxzoom")
    private int maxZoom;
    private boolean markup;

    public AreaMarkerUpdated(long timestamp, String msg, double yTop, double yBottom, double[] x, double[] z, int weight, double opacity, String color, double fillOpacity, String fillColor, String id, String label, String set, String desc, int minZoom, int maxZoom, boolean markup) {
        super(timestamp);
        this.msg = msg;
        this.yTop = yTop;
        this.yBottom = yBottom;
        this.x = x;
        this.z = z;
        this.weight = weight;
        this.opacity = opacity;
        this.color = color;
        this.fillOpacity = fillOpacity;
        this.fillColor = fillColor;
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

    public double getyTop() {
        return yTop;
    }

    public void setyTop(double yTop) {
        this.yTop = yTop;
    }

    public double getyBottom() {
        return yBottom;
    }

    public void setyBottom(double yBottom) {
        this.yBottom = yBottom;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
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

    public double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
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
