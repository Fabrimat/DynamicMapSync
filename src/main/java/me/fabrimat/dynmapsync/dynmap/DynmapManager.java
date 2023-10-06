package me.fabrimat.dynmapsync.dynmap;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.dynmap.command.DynmapCommand;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigMap;
import me.fabrimat.dynmapsync.job.command.CommandManager;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class DynmapManager {

    ReentrantLock worldFileLock = new ReentrantLock();
    ReentrantLock markersFileLock = new ReentrantLock();
    ReentrantLock configFileLock = new ReentrantLock();
    ReentrantLock tileFilesLock = new ReentrantLock();

    final Set<String> worlds = ConcurrentHashMap.newKeySet();
    final Set<ConfigMap> maps = ConcurrentHashMap.newKeySet();

    final Map<String, Timestamp> timestamps = new ConcurrentHashMap<>();

    public void initialize() {
        DynmapSync dynmapSync = DynmapSync.getInstance();
        CommandManager commandManager = dynmapSync.getCommandManager();

        commandManager.registerCommand(new DynmapCommand());
    }

    public ReentrantLock getWorldFileLock() {
        return worldFileLock;
    }

    public ReentrantLock getMarkersFileLock() {
        return markersFileLock;
    }

    public ReentrantLock getConfigFileLock() {
        return configFileLock;
    }

    public Set<String> getWorlds() {
        return worlds;
    }

    public void setTimestamp(String key, Timestamp timestamp) {
        timestamps.put(key, timestamp);
    }

    public Timestamp getTimestamp(String key) {
        return timestamps.get(key);
    }

    public Set<ConfigMap> getMaps() {
        return maps;
    }

    public ReentrantLock getTileFilesLock() {
        return tileFilesLock;
    }
}
