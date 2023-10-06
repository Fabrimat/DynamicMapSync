package me.fabrimat.dynmapsync.dynmap.json.marker;

import com.google.gson.annotations.SerializedName;

public class DynmapMarkerCircle {
    private String label;
    private String markup;
    private String desc;
    private double x;
    private double y;
    private double z;
    private double xr;
    private double zr;
    private String world;
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
    @SerializedName("boostflag")
    private boolean boostFlag;
    private String greeting;
    private String farewell;
    @SerializedName("greetingsub")
    private String greetingSub;
    @SerializedName("farewellsub")
    private String farewellSub;
    private int weight;

    public DynmapMarkerCircle(String label, String markup, String desc, double x, double y, double z, double xr, double zr, String world, String fillColor, double fillOpacity, String strokeColor, double strokeOpacity, double strokeWidth, String minZoom, String maxZoom, boolean boostFlag, String greeting, String farewell, String greetingSub, String farewellSub, int weight) {
        this.label = label;
        this.markup = markup;
        this.desc = desc;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xr = xr;
        this.zr = zr;
        this.world = world;
        this.fillColor = fillColor;
        this.fillOpacity = fillOpacity;
        this.strokeColor = strokeColor;
        this.strokeOpacity = strokeOpacity;
        this.strokeWidth = strokeWidth;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.boostFlag = boostFlag;
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

    public double getXr() {
        return xr;
    }

    public void setXr(double xr) {
        this.xr = xr;
    }

    public double getZr() {
        return zr;
    }

    public void setZr(double zr) {
        this.zr = zr;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
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

    public boolean isBoostFlag() {
        return boostFlag;
    }

    public void setBoostFlag(boolean boostFlag) {
        this.boostFlag = boostFlag;
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
