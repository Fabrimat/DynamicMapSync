package me.fabrimat.dynmapsync.dynmap;

import com.google.gson.JsonObject;

import java.nio.file.Path;

public class DynmapUtils {

    public static void createWorldFileIfNotExists(Path dynmapPath) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "world");
    }

}
