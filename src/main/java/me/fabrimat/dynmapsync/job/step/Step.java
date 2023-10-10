package me.fabrimat.dynmapsync.job.step;

import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.Command;
import me.fabrimat.dynmapsync.job.command.CommandManager;

import java.util.logging.Level;

public record Step(Job job, Command command, Command fallbackCommand, boolean conditional) {

    public boolean execute() {
        CommandManager commandManager = AppServer.getInstance().getCommandManager();
        boolean debug = AppServer.getInstance().getMainConfig().isDebug();
        boolean success;

        try {
            success = commandManager.executeCommand(job(), this, command());
        } catch (Exception ex) {
            if (debug) {
                DynmapSync.getInstance().getLogger().log(Level.SEVERE, "Error executing command " + command().getCommandName(), ex);
            }
            success = false;
        }

        if (!success) {
            try {
                commandManager.executeCommand(job(), this, fallbackCommand());
            } catch (Exception ex) {
                if (debug) {
                    DynmapSync.getInstance().getLogger().log(Level.SEVERE, "Error executing command " + fallbackCommand().getCommandName(), ex);
                }
            }
        }

        return success;
    }

}
