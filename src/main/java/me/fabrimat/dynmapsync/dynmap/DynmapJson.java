package me.fabrimat.dynmapsync.dynmap;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.fabrimat.dynmapsync.dynmap.json.DynmapConfigFile;
import me.fabrimat.dynmapsync.dynmap.json.DynmapFile;
import me.fabrimat.dynmapsync.dynmap.json.DynmapMarkerFile;
import me.fabrimat.dynmapsync.dynmap.json.DynmapWorldFile;
import me.fabrimat.dynmapsync.dynmap.json.world.update.ComponentMessage;
import me.fabrimat.dynmapsync.dynmap.json.world.update.ComponentMessageAdapter;
import me.fabrimat.dynmapsync.dynmap.json.world.update.Update;
import me.fabrimat.dynmapsync.dynmap.json.world.update.UpdateTypeAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Objects;

public final class DynmapJson {
    private final Path dynmapPath;
    private final FileType fileType;
    private final String world;

    private DynmapFile dynmapFile;

    public DynmapJson(Path dynmapPath, FileType fileType, String world) throws IOException {
        this.dynmapPath = dynmapPath;
        this.fileType = fileType;

        Preconditions.checkArgument(!(world == null && fileType != FileType.CONFIG), "World cannot be null for non config files");
        if (world == null) {
            world = "";
        }
        this.world = world;
        createFileIfNotExists();
        loadJsonFile();
    }

    public File getFile() {
        Path path = dynmapPath.resolve(fileType.getFilePath().replace("%1", world));
        return path.toFile();
    }

    private void loadJsonFile() throws IOException {
        File file = getFile();
        Preconditions.checkState(file.exists(), "File %1 does not exists".replace("%1", file.getAbsolutePath()));

        Class<? extends DynmapFile> clazz = switch (fileType) {
            case MARKERS -> DynmapMarkerFile.class;
            case CONFIG -> DynmapConfigFile.class;
            case WORLD -> DynmapWorldFile.class;
        };

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Update.class, new UpdateTypeAdapter<>())
                .registerTypeAdapter(ComponentMessage.class, new ComponentMessageAdapter<>())
                .create();
        this.dynmapFile = gson.fromJson(Files.newBufferedReader(file.toPath(), Charset.defaultCharset()), clazz);
    }

    private void createFileIfNotExists() throws IOException {
        File file = getFile();
        if (!file.exists()) {
            DynmapFile dynmapFile = switch (fileType) {
                case MARKERS -> new DynmapMarkerFile();
                case CONFIG -> new DynmapConfigFile();
                case WORLD -> new DynmapWorldFile();
            };

            Gson gson = new Gson();
            gson.toJson(dynmapFile, new FileWriter(file));
        }
    }

    public void writeFile() throws IOException {
        dynmapFile.setTimestamp(Instant.now().toEpochMilli());
        File file = getFile();
        Gson gson = new GsonBuilder().create();
        gson.toJson(dynmapFile, new FileWriter(file));
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
        WORLD("standalone/dynmap_%1.json"),
        ;

        private final String filePath;

        FileType(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }
}
