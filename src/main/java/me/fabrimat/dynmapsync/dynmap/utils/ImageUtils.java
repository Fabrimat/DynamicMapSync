package me.fabrimat.dynmapsync.dynmap.utils;

import com.google.common.io.Files;
import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.ImageEncoding;
import me.fabrimat.dynmapsync.dynmap.ImageFormat;
import me.fabrimat.dynmapsync.utils.Utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageUtils {
    private static final Object imageioLock = new Object();

    public static BufferedImage loadImage(Path path) {
        ImageFormat format = ImageFormat.getFormat(path);
        if (format != null) {
            try {
                return imageIODecode(path, format);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void writeImage(Path file, BufferedImage image, ImageFormat fmt) {
        try (BufferOutputStream fos = imageIOEncode(image, fmt)) {
            Files.write(fos.buf, file.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage imageIODecode(Path path, ImageFormat fmt) throws IOException {
        if(Utils.isRequiredJDKVersion(17,-1,-1)){
            return imageIODecodeUnsafe(path, fmt); //we can skip Thread safety for more performance
        }
        synchronized(imageioLock) {
            return imageIODecodeUnsafe(path, fmt);
        }
    }

    private static BufferedImage imageIODecodeUnsafe(Path path, ImageFormat fmt) throws IOException {
        ImageIO.setUseCache(false);
        if (fmt.getEncoding() == ImageEncoding.WEBP) {
            try (FileInputStream fis = new FileInputStream(path.toFile());
            BufferInputStream bis = new BufferInputStream(fis.readAllBytes()))
            {
                return doWEBPDecode(bis);
            } catch (FileNotFoundException ex) {
                //Log.info("Error decoding image - " + iox.getMessage());
                return null;
            }
        }
        return ImageIO.read(path.toFile());
    }

    public static BufferedImage mergeImages(BufferedImage topImage, BufferedImage bottomImage) {

        int width = Math.max(topImage.getWidth(), bottomImage.getWidth());
        int height = Math.max(topImage.getHeight(), bottomImage.getHeight());

        BufferedImage mergedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        mergedImage.getGraphics().drawImage(bottomImage, 0, 0, null);
        mergedImage.getGraphics().drawImage(topImage, 0, 0, null);

        return mergedImage;
    }

    public static BufferOutputStream imageIOEncode(BufferedImage img, ImageFormat fmt) {
        if(Utils.isRequiredJDKVersion(17,-1,-1)){
            return imageIOEncodeUnsafe(img, fmt); //we can skip Thread safety for more performance
        }
        synchronized(imageioLock) {
            return imageIOEncodeUnsafe(img, fmt);
        }
    }

    private static BufferOutputStream imageIOEncodeUnsafe(BufferedImage img, ImageFormat fmt) {
        BufferOutputStream bos = new BufferOutputStream();
        try {
            ImageIO.setUseCache(false); /* Don't use file cache - too small to be worth it */

            //fmt = validateFormat(fmt);

            if(fmt.getEncoding() == ImageEncoding.JPG) {
                WritableRaster raster = img.getRaster();
                WritableRaster newRaster = raster.createWritableChild(0, 0, img.getWidth(),
                        img.getHeight(), 0, 0, new int[] {0, 1, 2});
                DirectColorModel cm = (DirectColorModel)img.getColorModel();
                DirectColorModel newCM = new DirectColorModel(cm.getPixelSize(),
                        cm.getRedMask(), cm.getGreenMask(), cm.getBlueMask());
                // now create the new buffer that is used ot write the image:
                BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);

                // Find a jpeg writer
                ImageWriter writer = null;
                Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
                if (iter.hasNext()) {
                    writer = iter.next();
                }
                if(writer == null) {
                    //Log.severe("No JPEG ENCODER - Java VM does not support JPEG encoding");
                    return null;
                }
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                iwp.setCompressionQuality(fmt.getQuality());

                ImageOutputStream ios;
                ios = ImageIO.createImageOutputStream(bos);
                writer.setOutput(ios);

                writer.write(null, new IIOImage(rgbBuffer, null, null), iwp);
                writer.dispose();

                rgbBuffer.flush();
            }
            else if (fmt.getEncoding() == ImageEncoding.WEBP) {
                doWEBPEncode(img, bos, fmt);
            }
            else {
                ImageIO.write(img, fmt.getFileExt(), bos); /* Write to byte array stream - prevent bogus I/O errors */
            }
        } catch (IOException iox) {
            //Log.info("Error encoding image - " + iox.getMessage());
            return null;
        }
        return bos;
    }

    private static void doWEBPEncode(BufferedImage img, OutputStream out, ImageFormat fmt) throws IOException {
        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        BufferOutputStream bos = new BufferOutputStream();

        ImageIO.write(img, "png", bos); // Encode as PNG in buffere output stream
        // Write to a tmp file
        File tempFile = File.createTempFile("pngToWebp", "png");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(bos.buf, 0, bos.len);
        fos.close();
        // Run encoder to new temp file
        File tempFile2 = File.createTempFile("pngToWebp", "webp");
        List<String> args = new ArrayList<>();
        args.add(config.getCwebpPath());
        if (fmt.isLossless()) {
            args.add("-lossless");
        }
        args.add("-q");
        args.add(Integer.toString((int)fmt.getQuality()));
        args.add(tempFile.getAbsolutePath());
        args.add("-o");
        args.add(tempFile2.getAbsolutePath());
        Process pr = Runtime.getRuntime().exec(args.toArray(new String[0]));
        try {
            pr.waitFor();
        } catch (InterruptedException ix) {
            throw new IOException("Error waiting for encoder");
        }
        // Read output file into output stream
        Files.copy(tempFile2, out);
        out.flush();
        // Clean up temp files
        tempFile.delete();
        tempFile2.delete();
    }

    private static BufferedImage doWEBPDecode(BufferInputStream buf) throws IOException {
        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        // Write to a tmp file
        File tempFile = File.createTempFile("webpToPng", "webp");
        Files.write(buf.buffer(), tempFile);
        // Run encoder to new new temp file
        File tempFile2 = File.createTempFile("webpToPng", "png");
        String[] args = {config.getDwebpPath(), tempFile.getAbsolutePath(), "-o", tempFile2.getAbsolutePath() };
        Process pr = Runtime.getRuntime().exec(args);
        try {
            pr.waitFor();
        } catch (InterruptedException ix) {
            throw new IOException("Error waiting for encoder");
        }
        // Read file
        BufferedImage obuf = ImageIO.read(tempFile2);
        // Clean up temp files
        tempFile.delete();
        tempFile2.delete();

        return obuf;
    }
}
