package me.fabrimat.dynamicmapsync.dynmap.json.world.update.marker;

import com.google.gson.annotations.SerializedName;

public class MarkerSetUpdated extends MarkerComponentMessage {
    private String msg;
    private String id;
    private String label;
    @SerializedName("layerprio")
    private int layerPriority;
    @SerializedName("showlabels")
    private Boolean showLabels;
    @SerializedName("minzoom")
    private int minZoom;
    @SerializedName("maxzoom")
    private int maxZoom;

    public MarkerSetUpdated(long timestamp, String msg, String id, String label, int layerPriority, Boolean showLabels, int minZoom, int maxZoom) {
        super(timestamp);
        this.msg = msg;
        this.id = id;
        this.label = label;
        this.layerPriority = layerPriority;
        this.showLabels = showLabels;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public int getLayerPriority() {
        return layerPriority;
    }

    public void setLayerPriority(int layerPriority) {
        this.layerPriority = layerPriority;
    }

    public Boolean getShowLabels() {
        return showLabels;
    }

    public void setShowLabels(Boolean showLabels) {
        this.showLabels = showLabels;
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
}
