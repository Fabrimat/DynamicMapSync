package me.fabrimat.dynamicmapsync.dynmap;

public enum ImageEncoding {
    PNG("png", "image/png", true), JPG("jpg", "image/jpeg", false), WEBP("webp", "image/webp", true);
    public final String ext;
    public final String mimetype;
    public final boolean hasAlpha;

    ImageEncoding(String ext, String mime, boolean has_alpha) {
        this.ext = ext;
        this.mimetype = mime;
        this.hasAlpha = has_alpha;
    }
    public String getFileExt() { return ext; }
    public String getContentType() { return mimetype; }

    public static ImageEncoding fromOrd(int ix) {
        ImageEncoding[] v = values();
        if ((ix >= 0) && (ix < v.length))
            return v[ix];
        return null;
    }
    public static ImageEncoding fromContentType(String ct) {
        ImageEncoding[] v = values();
        for (int i = 0; i < v.length; i++) {
            if (v[i].mimetype.equalsIgnoreCase(ct)) {
                return v[i];
            }
        }
        return null;
    }
    public static ImageEncoding fromExt(String x) {
        ImageEncoding[] v = values();
        for (int i = 0; i < v.length; i++) {
            if (v[i].ext.equalsIgnoreCase(x)) {
                return v[i];
            }
        }
        return null;
    }
}
