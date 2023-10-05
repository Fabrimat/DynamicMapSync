package me.fabrimat.dynmapsync.job.step;

import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.Command;
import me.fabrimat.dynmapsync.job.command.CommandManager;

public record Step(Job job, Command command, Command fallbackCommand, boolean conditional) {

    public boolean execute() {
        CommandManager commandManager = AppServer.getInstance().getCommandManager();
        boolean success;

        try {
            success = commandManager.executeCommand(job(), this, command());
        } catch (Exception ex) {
            success = false;
        }

        if (!success) {
            try {
                commandManager.executeCommand(job(), this, fallbackCommand());
            } catch (Exception ignored) {
            }
        }

        return success;
    }

}
