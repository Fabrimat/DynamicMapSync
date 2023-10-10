package me.fabrimat.dynamicmapsync.job.command.exceptions;

public class CommandNotRegisteredException extends RuntimeException {
    public CommandNotRegisteredException(String message) {
        super(message);
    }
}
