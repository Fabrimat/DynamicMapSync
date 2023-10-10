package me.fabrimat.dynamicmapsync.job.command.commands;

import me.fabrimat.dynamicmapsync.AppServer;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.JobManager;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.command.commands.exceptions.IllegalCommandArgumentsException;
import me.fabrimat.dynamicmapsync.job.command.commands.exceptions.JobNotFoundException;
import me.fabrimat.dynamicmapsync.job.command.commands.exceptions.RecursiveJobException;
import me.fabrimat.dynamicmapsync.job.step.Step;

import java.util.Arrays;

public class JobCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) throws IllegalCommandArgumentsException, JobNotFoundException, RecursiveJobException {
        if (args != null && args.length > 0) {
            String arg = String.join(" ", args);
            JobManager jobManager = AppServer.getInstance().getJobManager();
            Job callJob = jobManager.getJob(arg);
            if (callJob != null) {
                int count = 0;
                for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                    if (element.getClassName().equals(getClass().getName())) {
                        count++;
                    }
                    if (count > AppServer.getInstance().getMainConfig().getRecursiveJobsProtection()) {
                        throw new RecursiveJobException("Too many recursive jobs: " + count);
                    }
                }
                return callJob.runSteps();
            } else {
                throw new JobNotFoundException("Job " + arg + " not found");
            }
        } else {
            throw new IllegalCommandArgumentsException("Illegal command argument for command JOB: " + Arrays.toString(args));
        }
    }
    
    @Override
    public String getCommand() {
        return "JOB";
    }
}
