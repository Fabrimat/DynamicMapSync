package me.fabrimat.dynmapsync.dynmap;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.fabrimat.dynmapsync.dynmap.json.DynmapWorld;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class DynmapUtils {

    public static void createWorldFileIfNotExists(Path dynmapPath) throws IOException {
        File file = getWorldFile(dynmapPath);
        if (!file.exists()) {
            Gson gson = new Gson();
            gson.toJson(new DynmapWorld(), new FileWriter(file));
        }
    }

    public static DynmapWorld loadWorldFile(Path dynmapPath) throws IOException {
        File file = getWorldFile(dynmapPath);
        if (!file.exists()) {
            return null;
        }
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(Files.newBufferedReader(file.toPath(), Charset.defaultCharset()), DynmapWorld.class);
    }

    public static File getWorldFile(Path dynmapPath) {
        Preconditions.checkArgument(Files.exists(dynmapPath), "Dynmap path does not exist");
        Path standalone = dynmapPath.resolve("standalone");
        Preconditions.checkState(Files.exists(standalone), "Dynmap standalone path does not exist");
        return standalone.resolve("dynmap_world.json").toFile();
    }
}
