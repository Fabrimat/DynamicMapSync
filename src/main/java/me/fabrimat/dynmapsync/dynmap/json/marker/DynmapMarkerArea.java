package me.fabrimat.dynmapsync.dynmap.json.marker;

import com.google.gson.annotations.SerializedName;

public class DynmapMarkerArea {
    private String label;
    private String markup;
    private String desc;
    @SerializedName("fillcolor")
    private String fillColor;
    @SerializedName("fillopacity")
    private double fillOpacity;
    @SerializedName("strokecolor")
    private String strokeColor;
    @SerializedName("strokeopacity")
    private double strokeOpacity;
    @SerializedName("strokeweight")
    private double strokeWidth;
    @SerializedName("minzoom")
    private String minZoom;
    @SerializedName("maxzoom")
    private String maxZoom;
    private double[] x;
    @SerializedName("ytop")
    private double yTop;
    @SerializedName("ybottom")
    private double yBottom;
    private double[] z;
    private String world;
    private String greeting;
    private String farewell;
    @SerializedName("greetingsub")
    private String greetingSub;
    @SerializedName("farewellsub")
    private String farewellSub;
    private int weight;

    public DynmapMarkerArea(String label, String markup, String desc, String fillColor, double fillOpacity, String strokeColor, double strokeOpacity, double strokeWidth, String minZoom, String maxZoom, double[] x, double yTop, double yBottom, double[] z, String world, String greeting, String farewell, String greetingSub, String farewellSub, int weight) {
        this.label = label;
        this.markup = markup;
        this.desc = desc;
        this.fillColor = fillColor;
        this.fillOpacity = fillOpacity;
        this.strokeColor = strokeColor;
        this.strokeOpacity = strokeOpacity;
        this.strokeWidth = strokeWidth;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.x = x;
        this.yTop = yTop;
        this.yBottom = yBottom;
        this.z = z;
        this.world = world;
        this.greeting = greeting;
        this.farewell = farewell;
        this.greetingSub = greetingSub;
        this.farewellSub = farewellSub;
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

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public void setFillOpacity(double fillOpacity) {
        this.fillOpacity = fillOpacity;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public double getStrokeOpacity() {
        return strokeOpacity;
    }

    public void setStrokeOpacity(double strokeOpacity) {
        this.strokeOpacity = strokeOpacity;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
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

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
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

    public double[] getZ() {
        return z;
    }

    public void setZ(double[] z) {
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getFarewell() {
        return farewell;
    }

    public void setFarewell(String farewell) {
        this.farewell = farewell;
    }

    public String getGreetingSub() {
        return greetingSub;
    }

    public void setGreetingSub(String greetingSub) {
        this.greetingSub = greetingSub;
    }

    public String getFarewellSub() {
        return farewellSub;
    }

    public void setFarewellSub(String farewellSub) {
        this.farewellSub = farewellSub;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
