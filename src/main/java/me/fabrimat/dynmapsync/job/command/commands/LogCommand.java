package me.fabrimat.dynmapsync.job.command.commands;

import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

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
