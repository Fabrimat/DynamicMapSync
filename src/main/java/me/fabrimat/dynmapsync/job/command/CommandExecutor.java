package me.fabrimat.dynmapsync.job.command;

import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;
import me.fabrimat.dynmapsync.job.step.Step;

public interface CommandExecutor {
    boolean execute(Job job, Step step, String[] args) throws CommandException;
    
    String getCommand();
}
