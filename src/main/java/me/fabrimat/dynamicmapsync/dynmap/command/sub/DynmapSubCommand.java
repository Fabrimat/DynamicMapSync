package me.fabrimat.dynamicmapsync.dynmap.command.sub;

import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.step.Step;

public interface DynmapSubCommand {
    boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception;
}
