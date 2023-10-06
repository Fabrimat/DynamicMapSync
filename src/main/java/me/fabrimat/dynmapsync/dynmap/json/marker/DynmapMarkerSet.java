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

    public DynmapMarkerSet(boolean hide, String label, int layerPriority, String minZoom, String maxZoom, String[] allowedIcons, String defaultIcon, boolean showLabels, Map<String, DynmapMarker> markers, Map<String, DynmapMarkerPolyLine> lines, Map<String, DynmapMarkerCircle> circles, Map<String, DynmapMarkerArea> areas) {
        this.hide = hide;
        this.label = label;
        this.layerPriority = layerPriority;
        this.minZoom = minZoom;
        this.maxZoom = maxZoom;
        this.allowedIcons = allowedIcons;
        this.defaultIcon = defaultIcon;
        this.showLabels = showLabels;
        this.markers = markers;
        this.lines = lines;
        this.circles = circles;
        this.areas = areas;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
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

    public String[] getAllowedIcons() {
        return allowedIcons;
    }

    public void setAllowedIcons(String[] allowedIcons) {
        this.allowedIcons = allowedIcons;
    }

    public String getDefaultIcon() {
        return defaultIcon;
    }

    public void setDefaultIcon(String defaultIcon) {
        this.defaultIcon = defaultIcon;
    }

    public boolean isShowLabels() {
        return showLabels;
    }

    public void setShowLabels(boolean showLabels) {
        this.showLabels = showLabels;
    }

    public Map<String, DynmapMarker> getMarkers() {
        return markers;
    }

    public void setMarkers(Map<String, DynmapMarker> markers) {
        this.markers = markers;
    }

    public Map<String, DynmapMarkerPolyLine> getLines() {
        return lines;
    }

    public void setLines(Map<String, DynmapMarkerPolyLine> lines) {
        this.lines = lines;
    }

    public Map<String, DynmapMarkerCircle> getCircles() {
        return circles;
    }

    public void setCircles(Map<String, DynmapMarkerCircle> circles) {
        this.circles = circles;
    }

    public Map<String, DynmapMarkerArea> getAreas() {
        return areas;
    }

    public void setAreas(Map<String, DynmapMarkerArea> areas) {
        this.areas = areas;
    }
}
