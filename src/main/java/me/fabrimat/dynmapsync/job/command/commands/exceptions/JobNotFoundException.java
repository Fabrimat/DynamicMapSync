package me.fabrimat.dynmapsync.job.command.commands.exceptions;

import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;

public class JobNotFoundException extends CommandException {
    public JobNotFoundException(String message) {
        super(message);
    }
}
