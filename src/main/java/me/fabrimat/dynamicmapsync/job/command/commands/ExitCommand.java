package me.fabrimat.dynamicmapsync.job.command.commands;

import me.fabrimat.dynamicmapsync.AppServer;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.command.exceptions.CommandException;
import me.fabrimat.dynamicmapsync.job.step.Step;

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
