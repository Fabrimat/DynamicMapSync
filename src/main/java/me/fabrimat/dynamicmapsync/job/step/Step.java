package me.fabrimat.dynamicmapsync.job.step;

import me.fabrimat.dynamicmapsync.AppServer;
import me.fabrimat.dynamicmapsync.DynamicMapSync;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.Command;
import me.fabrimat.dynamicmapsync.job.command.CommandManager;

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
                DynamicMapSync.getInstance().getLogger().log(Level.SEVERE, "Error executing command " + command().getCommandName(), ex);
            }
            success = false;
        }

        if (!success) {
            try {
                commandManager.executeCommand(job(), this, fallbackCommand());
            } catch (Exception ex) {
                if (debug) {
                    DynamicMapSync.getInstance().getLogger().log(Level.SEVERE, "Error executing command " + fallbackCommand().getCommandName(), ex);
                }
            }
        }

        return success;
    }

}
