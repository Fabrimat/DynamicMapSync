package me.fabrimat.dynamicmapsync.dynmap;

import me.fabrimat.dynamicmapsync.DynamicMapSync;
import me.fabrimat.dynamicmapsync.config.DynmapConfigSection;
import me.fabrimat.dynamicmapsync.utils.Utils;

import java.nio.file.Path;
import java.util.Optional;

public enum ImageFormat {
    FORMAT_PNG("png", 0.0f, ImageEncoding.PNG),
    FORMAT_JPG75("jpg-q75", 0.75f, ImageEncoding.JPG),
    FORMAT_JPG80("jpg-q80", 0.80f, ImageEncoding.JPG),
    FORMAT_JPG85("jpg-q85", 0.85f, ImageEncoding.JPG),
    FORMAT_JPG("jpg", 0.85f, ImageEncoding.JPG),
    FORMAT_JPG90("jpg-q90", 0.90f, ImageEncoding.JPG),
    FORMAT_JPG95("jpg-q95", 0.95f, ImageEncoding.JPG),
    FORMAT_JPG100("jpg-q100", 1.00f, ImageEncoding.JPG),
    FORMAT_WEBP75("webp-q75", 75, ImageEncoding.WEBP),
    FORMAT_WEBP80("webp-q80", 80, ImageEncoding.WEBP),
    FORMAT_WEBP85("webp-q85", 85, ImageEncoding.WEBP),
    FORMAT_WEBP("webp", 85, ImageEncoding.WEBP),
    FORMAT_WEBP90("webp-q90", 90, ImageEncoding.WEBP),
    FORMAT_WEBP95("webp-q95", 95, ImageEncoding.WEBP),
    FORMAT_WEBP100("webp-q100", 100, ImageEncoding.WEBP),
    FORMAT_WEBPL("webp-l", 85, ImageEncoding.WEBP);
    final String id;
    final float qual;
    final ImageEncoding enc;

    ImageFormat(String id, float quality, ImageEncoding enc) {
        this.id = id;
        this.qual = quality;
        this.enc = enc;
    }
    public String getID() { return id; }
    public String getFileExt() { return enc.getFileExt(); }
    public float getQuality() { return qual; }
    public ImageEncoding getEncoding() { return enc; }

    public boolean isLossless() {
        return this == FORMAT_WEBPL;
    }

    public static ImageFormat getFormat(Path path) {
        DynmapConfigSection config = DynamicMapSync.getInstance().getMainConfig().getDynmapConfig();
        Optional<String> ext = Utils.getFileExtension(path.toFile().getName());
        ImageFormat format = null;
        if (ext.isPresent()) {
            String extValue = ext.get();
            switch (extValue) {
                case "jpg":
                case "jpeg":
                    format = switch (config.getJpgQuality()) {
                        case 75 -> ImageFormat.FORMAT_JPG75;
                        case 80 -> ImageFormat.FORMAT_JPG80;
                        case 85 -> ImageFormat.FORMAT_JPG85;
                        case 90 -> ImageFormat.FORMAT_JPG90;
                        case 95 -> ImageFormat.FORMAT_JPG95;
                        case 100 -> ImageFormat.FORMAT_JPG100;
                        default -> ImageFormat.FORMAT_JPG;
                    };
                    break;
                case "webp":
                    if (config.isWebpLossless()) {
                        format = ImageFormat.FORMAT_WEBPL;
                    } else {
                        format = switch (config.getWebpQuality()) {
                            case 75 -> ImageFormat.FORMAT_WEBP75;
                            case 80 -> ImageFormat.FORMAT_WEBP80;
                            case 85 -> ImageFormat.FORMAT_WEBP85;
                            case 90 -> ImageFormat.FORMAT_WEBP90;
                            case 95 -> ImageFormat.FORMAT_WEBP95;
                            case 100 -> ImageFormat.FORMAT_WEBP100;
                            default -> ImageFormat.FORMAT_WEBP;
                        };
                    }
                    break;
                case "png":
                    format = ImageFormat.FORMAT_PNG;
                    break;
                default:
                    break;
            }
        }
        return format;
    }
};