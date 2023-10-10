package me.fabrimat.dynamicmapsync.dynmap;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.fabrimat.dynamicmapsync.AppServer;
import me.fabrimat.dynamicmapsync.dynmap.json.DynmapConfigFile;
import me.fabrimat.dynamicmapsync.dynmap.json.DynmapFile;
import me.fabrimat.dynamicmapsync.dynmap.json.DynmapMarkerFile;
import me.fabrimat.dynamicmapsync.dynmap.json.DynmapWorldFile;
import me.fabrimat.dynamicmapsync.dynmap.json.world.update.ComponentMessage;
import me.fabrimat.dynamicmapsync.dynmap.json.world.update.ComponentMessageAdapter;
import me.fabrimat.dynamicmapsync.dynmap.json.world.update.Update;
import me.fabrimat.dynamicmapsync.dynmap.json.world.update.UpdateTypeAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

public final class DynmapJson {
    private final Path dynmapPath;
    private final FileType fileType;
    private final String world;

    private DynmapFile dynmapFile;

    public DynmapJson(Path dynmapPath, FileType fileType, String world, boolean shouldCreate) throws IOException {
        this.dynmapPath = dynmapPath;
        this.fileType = fileType;

        Preconditions.checkArgument(!(world == null && fileType != FileType.CONFIG), "World cannot be null for non config files");
        if (world == null) {
            world = "";
        }
        this.world = world;
        try {
            if (shouldCreate) createFileIfNotExists();
        } catch (IOException e) {
            AppServer.getInstance().getLogger().warning("Could not create file %1 - ".replace("%1", getFile().getAbsolutePath()) + e.getMessage());
        }
        loadJsonFile();
    }

    public File getFile() {
        Path path = dynmapPath.resolve(fileType.getFilePath().replace("%1", world)).toAbsolutePath();
        if (AppServer.getInstance().getMainConfig().isDebug())
            AppServer.getInstance().getLogger().info(String.format("Getting file %s from %s", fileType, path));
        return path.toFile();
    }

    private void loadJsonFile() throws IOException {
        File file = getFile().getAbsoluteFile();
        Preconditions.checkState(file.exists(), "File %1 does not exists".replace("%1", file.getAbsolutePath()));

        Type type = switch (fileType) {
            case MARKERS -> DynmapMarkerFile.class;
            case CONFIG -> DynmapConfigFile.class;
            case WORLD -> DynmapWorldFile.class;
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Update.class, new UpdateTypeAdapter<>())
                .registerTypeAdapter(ComponentMessage.class, new ComponentMessageAdapter<>())
                .create();
        BufferedReader br = new BufferedReader(new FileReader(file));
        this.dynmapFile = gson.fromJson(br, type);
        br.close();
        Preconditions.checkState(dynmapFile != null, "File %1 is empty".replace("%1", file.getAbsolutePath()));
    }

    private void createFileIfNotExists() throws IOException {
        File file = getFile().getAbsoluteFile();
        if (!file.exists() || file.length() == 0) {
            file.getParentFile().getAbsoluteFile().mkdirs();
            boolean debug = AppServer.getInstance().getMainConfig().isDebug();
            if (debug)
                AppServer.getInstance().getLogger().info("Creating file %1".replace("%1", file.getAbsolutePath()));
            DynmapFile dynmapFile = switch (fileType) {
                case MARKERS -> new DynmapMarkerFile();
                case CONFIG -> new DynmapConfigFile();
                case WORLD -> new DynmapWorldFile();
            };

            GsonBuilder gsonBuilder = new GsonBuilder();
            if (debug) {
                gsonBuilder.setPrettyPrinting();
            }
            Gson gson = gsonBuilder.create();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            gson.toJson(dynmapFile, writer);
            writer.close();
            if (debug)
                AppServer.getInstance().getLogger().info("File %1 created".replace("%1", file.getAbsolutePath()));
        }
    }

    public void writeFile() throws IOException {
        dynmapFile.setTimestamp(Instant.now().toEpochMilli());
        File file = getFile();
        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        if (AppServer.getInstance().getMainConfig().isDebug()) {
            gsonBuilder.setPrettyPrinting();
        }
        Gson gson = gsonBuilder.create();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        gson.toJson(dynmapFile, writer);
        writer.close();
    }

    public Path getDynmapPath() {
        return dynmapPath;
    }

    public FileType getFileType() {
        return fileType;
    }

    public String getWorld() {
        return world;
    }

    public DynmapFile getDynmapFile() {
        return dynmapFile;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DynmapJson) obj;
        return Objects.equals(this.dynmapPath, that.dynmapPath) &&
                Objects.equals(this.fileType, that.fileType) &&
                Objects.equals(this.world, that.world) &&
                Objects.equals(this.dynmapFile, that.dynmapFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dynmapPath, fileType, world);
    }

    @Override
    public String toString() {
        return "DynmapJson[" +
                "dynmapPath=" + dynmapPath + ", " +
                "fileType=" + fileType + ", " +
                "world=" + world + ']';
    }

    public enum FileType {
        CONFIG("standalone/dynmap_config.json"),
        MARKERS("tiles/_markers_/marker_%1.json"),
        WORLD("standalone/dynmap_%1.json");

        private final String filePath;

        FileType(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }
}
