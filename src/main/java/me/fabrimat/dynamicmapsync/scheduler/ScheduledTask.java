package me.fabrimat.dynamicmapsync.scheduler;

import java.util.concurrent.Future;

public interface ScheduledTask {
    int getId();

    void cancel();

    Future<?> getFuture();

    boolean isActive();
}
