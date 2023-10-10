package me.fabrimat.dynamicmapsync.job.command.commands.exceptions;

import me.fabrimat.dynamicmapsync.job.command.exceptions.CommandException;

public class JobNotFoundException extends CommandException {
    public JobNotFoundException(String message) {
        super(message);
    }
}
