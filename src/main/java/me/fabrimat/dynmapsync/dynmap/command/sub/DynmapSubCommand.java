package me.fabrimat.dynmapsync.dynmap.command.sub;

import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

public interface DynmapSubCommand {
    boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception;
}
