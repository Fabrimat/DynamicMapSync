package me.fabrimat.dynamicmapsync.job.command;

import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.step.Step;

public interface CommandExecutor {
    boolean execute(Job job, Step step, String[] args) throws Exception;

    String getCommand();
}
