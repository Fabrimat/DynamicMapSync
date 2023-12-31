package me.fabrimat.dynamicmapsync.job.command;

import me.fabrimat.dynamicmapsync.AppServer;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.exceptions.CommandAlreadyRegisteredException;
import me.fabrimat.dynamicmapsync.job.command.exceptions.CommandNotRegisteredException;
import me.fabrimat.dynamicmapsync.job.step.Step;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class CommandManager {

    private final Map<String, CommandExecutor> commands;

    public CommandManager() {
        commands = new HashMap<>();
    }

    public void registerCommand(CommandExecutor commandExecutor) {
        String commandName = commandExecutor.getCommand().toUpperCase(Locale.ROOT);
        if (!getCommands().containsKey(commandName)) {
            getCommands().put(commandName, commandExecutor);
            AppServer.getInstance().getLogger().info("Registered " + commandName + " command");
        } else {
            throw new CommandAlreadyRegisteredException("Command " + commandName + " already registered.");
        }
    }

    public void unregisterCommand(String commandName) {
        commandName = commandName.toUpperCase(Locale.ROOT);
        if (getCommands().containsKey(commandName)) {
            getCommands().remove(commandName);
            AppServer.getInstance().getLogger().info("Unregistered " + commandName + " command");
        } else {
            throw new CommandNotRegisteredException("Command " + commandName + " is not registered.");
        }
    }

    public CommandExecutor getCommandExecutor(String commandName) {
        commandName = commandName.toUpperCase(Locale.ROOT);
        return this.getCommands().get(commandName);
    }

    public boolean executeCommand(Job job, Step step, Command command) throws Exception {
        if (command != null) {
            CommandExecutor executor = this.getCommandExecutor(command.getCommandName());
            if (executor != null) {
                return executor.execute(job, step, command.getArgs());
            } else {
                throw new CommandNotRegisteredException("Command " + command.getCommandName() + " is not registered.");
            }
        }
        return false;
    }

    public boolean dispatchCommand(Command command) {
        if (command != null) {
            if (this.getCommandExecutor(command.getCommandName()) == null) {
                return false;
            }
            AppServer.getInstance().getScheduler().runTask(() -> {
                boolean success;
                try {
                    success = executeCommand(null, null, command);
                } catch (Exception e) {
                    if (AppServer.getInstance().getMainConfig().isDebug()) {
                        AppServer.getInstance().getLogger().log(Level.SEVERE, "Error executing command " + command.getCommandName(), e);
                    }
                    success = false;
                }
                AppServer.getInstance().getLogger().info("Success: " + success);
            });
            return true;
        }
        return false;
    }

    protected Map<String, CommandExecutor> getCommands() {
        return commands;
    }
}
