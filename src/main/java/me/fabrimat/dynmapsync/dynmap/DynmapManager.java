package me.fabrimat.dynmapsync.dynmap;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.dynmap.command.DynmapCommand;
import me.fabrimat.dynmapsync.job.command.CommandManager;

public class DynmapManager {

    public DynmapManager() {

    }

    public void initialize() {
        DynmapSync dynmapSync = DynmapSync.getInstance();
        CommandManager commandManager = dynmapSync.getCommandManager();

        commandManager.registerCommand(new DynmapCommand());
    }
}
