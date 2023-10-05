package me.fabrimat.dynmapsync.dynmap.json.marker;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class DynmapMarkerSet {
    private boolean hide;
    private String label;
    @SerializedName("layerprio")
    private int layerPriority;
    @SerializedName("minzoom")
    private String minZoom;
    @SerializedName("maxzoom")
    private String maxZoom;
    @SerializedName("allowedicons")
    private String[] allowedIcons;
    @SerializedName("deficon")
    private String defaultIcon;
    @SerializedName("showlabels")
    private boolean showLabels;
    private Map<String, DynmapMarker> markers;
    private Map<String, DynmapMarkerPolyLine> lines;
    private Map<String, DynmapMarkerCircle> circles;
    private Map<String, DynmapMarkerArea> areas;
}
