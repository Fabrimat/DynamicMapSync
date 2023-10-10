package me.fabrimat.dynamicmapsync.job.command.commands.exceptions;

import me.fabrimat.dynamicmapsync.job.command.exceptions.CommandException;

public class RecursiveJobException extends CommandException {
    public RecursiveJobException(String message) {
        super(message);
    }
}
