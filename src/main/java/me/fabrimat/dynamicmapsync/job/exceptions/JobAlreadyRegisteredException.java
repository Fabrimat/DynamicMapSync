package me.fabrimat.dynamicmapsync.job.exceptions;

public class JobAlreadyRegisteredException extends RuntimeException {
    public JobAlreadyRegisteredException(String s) {
        super(s);
    }
}
