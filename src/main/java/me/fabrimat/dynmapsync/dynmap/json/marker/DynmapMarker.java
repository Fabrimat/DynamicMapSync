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
}
