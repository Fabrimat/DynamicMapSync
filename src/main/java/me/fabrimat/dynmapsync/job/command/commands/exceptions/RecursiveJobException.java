package me.fabrimat.dynmapsync.job.command.commands.exceptions;

import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;

public class RecursiveJobException extends CommandException {
    public RecursiveJobException(String message) {
        super(message);
    }
}
