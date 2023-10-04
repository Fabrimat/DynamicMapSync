package me.fabrimat.dynmapsync.scheduler;

import java.util.concurrent.ThreadFactory;

public class UptimeThreadFactory implements ThreadFactory {
    private int counter;
    
    public UptimeThreadFactory() {
        counter = 1;
    }
    
    @Override
    public Thread newThread(Runnable runnable) {
        Thread t = new Thread(runnable, "DynmapSync Pool Thread #" + counter);
        counter++;
        return t;
    }
}
