package me.fabrimat.dynmapsync.dynmap;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.dynmap.command.DynmapCommand;
import me.fabrimat.dynmapsync.job.command.CommandManager;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class DynmapManager {

    ReentrantLock worldFileLock = new ReentrantLock();
    ReentrantLock markersFileLock = new ReentrantLock();
    ReentrantLock configFileLock = new ReentrantLock();

    Set<String> worlds = new HashSet<>();

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
}
