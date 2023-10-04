package me.fabrimat.dynmapsync.dynmap.json.update.marker;

import com.google.gson.annotations.SerializedName;

public class CircleMarkerUpdated extends MarkerComponentMessage {
    private String msg;
    private double x;
    private double y;
    private double z;
    private double xr;
    private double zr;
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
}
