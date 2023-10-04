package me.fabrimat.dynmapsync.job.command.commands.exceptions;

import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;

public class IllegalCommandArgumentsException extends CommandException {
    public IllegalCommandArgumentsException(String message) {
        super(message);
    }
}
