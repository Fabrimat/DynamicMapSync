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
}
