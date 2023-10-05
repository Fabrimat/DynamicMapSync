package me.fabrimat.dynmapsync.dynmap.command.sub.sync;

import me.fabrimat.dynmapsync.dynmap.command.sub.SubCommand;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

public class MarkerSyncSubCommand implements SubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        return true;
    }
}
