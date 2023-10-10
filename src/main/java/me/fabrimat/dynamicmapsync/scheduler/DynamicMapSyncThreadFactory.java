package me.fabrimat.dynamicmapsync.scheduler;

import java.util.concurrent.ThreadFactory;

public class DynamicMapSyncThreadFactory implements ThreadFactory {
    private int counter;

    public DynamicMapSyncThreadFactory() {
        counter = 1;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable, "DynamicMapSync Pool Thread #" + counter);
        counter++;
        return t;
    }
}
