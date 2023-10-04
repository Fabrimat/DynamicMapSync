package me.fabrimat.dynmapsync.job.command.commands;

import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;
import me.fabrimat.dynmapsync.job.step.Step;

public class ExitCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) throws CommandException {
        AppServer.getInstance().stop();
        return true;
    }

    @Override
    public String getCommand() {
        return "EXIT";
    }
}
