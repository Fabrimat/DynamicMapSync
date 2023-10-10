package me.fabrimat.dynamicmapsync.job.command.commands;

import me.fabrimat.dynamicmapsync.AppServer;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.step.Step;

public class LogCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) {
        AppServer.getInstance().getLogger().info(String.join(" ", args));
        return true;
    }

    @Override
    public String getCommand() {
        return "LOG";
    }
}
