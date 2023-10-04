package me.fabrimat.dynmapsync;

import me.fabrimat.dynmapsync.config.MainConfig;
import me.fabrimat.dynmapsync.dynmap.DynmapManager;
import me.fabrimat.dynmapsync.job.JobManager;
import me.fabrimat.dynmapsync.job.command.CommandManager;
import me.fabrimat.dynmapsync.scheduler.Scheduler;

import java.io.InputStream;
import java.util.logging.Logger;

public abstract class AppServer {
    
    private static AppServer instance;
    
    public static AppServer getInstance() {
        return AppServer.instance;
    }
    
    public static void setInstance(AppServer instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Instance must be non null");
        }
        if (AppServer.instance != null) {
            throw new IllegalStateException("Instance already set");
        }
        AppServer.instance = instance;
    }
    
    public abstract String getName();
    
    public abstract String getVersion();
    
    public abstract Logger getLogger();
    
    public abstract Scheduler getScheduler();
    
    public abstract JobManager getJobManager();

    public abstract DynmapManager getDynmapManager();

    public abstract MainConfig getMainConfig();
    
    public abstract CommandManager getCommandManager();
    
    public String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }
    
    public abstract void start();
    
    public abstract void stop();
    
    public final InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }
}
