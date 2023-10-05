package me.fabrimat.dynmapsync.dynmap.json.marker;

import com.google.gson.annotations.SerializedName;

public class DynmapMarkerPolyLine {
    private String label;
    private String desc;
    private String markup;
    private double[] x;
    private double[] y;
    private double[] z;
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
    private String world;
}
