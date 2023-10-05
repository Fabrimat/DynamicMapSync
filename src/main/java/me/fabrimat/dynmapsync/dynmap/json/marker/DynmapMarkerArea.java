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
}
