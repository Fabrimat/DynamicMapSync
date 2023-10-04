package me.fabrimat.dynmapsync.dynmap.json.update.marker;

import com.google.gson.annotations.SerializedName;

public class MarkerSetUpdated extends MarkerComponentMessage {
    public String msg;
    public String id;
    public String label;
    @SerializedName("layerprio")
    public int layerPriority;
    @SerializedName("minzoom")
    private int minZoom;
    @SerializedName("maxzoom")
    private int maxZoom;
    @SerializedName("showlabels")
    public Boolean showLabels;
}
