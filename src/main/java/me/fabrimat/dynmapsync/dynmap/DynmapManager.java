package me.fabrimat.dynmapsync.dynmap;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.command.PlayerSyncCommand;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.JobManager;
import me.fabrimat.dynmapsync.job.JobScheduleInfo;
import me.fabrimat.dynmapsync.job.command.Command;
import me.fabrimat.dynmapsync.job.command.CommandManager;
import me.fabrimat.dynmapsync.job.step.Step;
import me.fabrimat.dynmapsync.scheduler.ScheduledTask;
import me.fabrimat.dynmapsync.scheduler.Scheduler;

import java.util.concurrent.TimeUnit;

public class DynmapManager {

    public DynmapManager() {

    }

    public void initialize() {
        DynmapSync dynmapSync = DynmapSync.getInstance();
        CommandManager commandManager = dynmapSync.getCommandManager();

        commandManager.registerCommand(new PlayerSyncCommand());
    }
}
