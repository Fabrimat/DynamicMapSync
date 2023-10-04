package me.fabrimat.dynmapsync.job.step;

import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.Command;
import me.fabrimat.dynmapsync.job.command.CommandManager;
import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;

public class Step {
    private final Job job;
    private final Command command;
    private final Command fallbackCommand;
    private final boolean conditional;
    
    public Step(Job job, Command command, Command fallbackCommand, boolean conditional) {
        this.job = job;
        this.command = command;
        this.fallbackCommand = fallbackCommand;
        this.conditional = conditional;
    }
    
    public boolean execute() {
        CommandManager commandManager = AppServer.getInstance().getCommandManager();
        boolean success;
        
        try {
            success = commandManager.executeCommand(getJob(), this, getCommand());
        } catch (Exception ex) {
            success = false;
        }
        
        if (!success) {
            try {
                commandManager.executeCommand(getJob(), this, getFallbackCommand());
            } catch (Exception ignored) {
            }
        }
        
        return success;
    }
    
    public Job getJob() {
        return job;
    }
    
    public Command getCommand() {
        return command;
    }
    
    public Command getFallbackCommand() {
        return fallbackCommand;
    }
    
    public boolean isConditional() {
        return conditional;
    }
    
}
