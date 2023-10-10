package me.fabrimat.dynamicmapsync.job.command.exceptions;

public class CommandAlreadyRegisteredException extends RuntimeException {
    public CommandAlreadyRegisteredException(String message) {
        super(message);
    }
}
