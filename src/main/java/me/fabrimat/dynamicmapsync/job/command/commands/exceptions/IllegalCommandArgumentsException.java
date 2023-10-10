package me.fabrimat.dynamicmapsync.job.command.commands.exceptions;

import me.fabrimat.dynamicmapsync.job.command.exceptions.CommandException;

public class IllegalCommandArgumentsException extends CommandException {
    public IllegalCommandArgumentsException(String message) {
        super(message);
    }
}
