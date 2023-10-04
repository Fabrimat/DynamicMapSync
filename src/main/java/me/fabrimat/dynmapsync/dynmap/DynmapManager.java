package me.fabrimat.dynmapsync.dynmap;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.scheduler.ScheduledTask;
import me.fabrimat.dynmapsync.scheduler.Scheduler;

public class DynmapManager {

    ScheduledTask playerSyncTask;
    ScheduledTask markerSyncTask;
    ScheduledTask tileSyncTask;

    public DynmapManager() {

    }

    private void start() {
        DynmapSync dynmapSync = DynmapSync.getInstance();
        Scheduler scheduler = dynmapSync.getScheduler();
    }
}
