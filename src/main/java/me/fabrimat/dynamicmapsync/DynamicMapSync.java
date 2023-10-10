package me.fabrimat.dynamicmapsync;

import jline.console.ConsoleReader;
import me.fabrimat.dynamicmapsync.config.JobConfig;
import me.fabrimat.dynamicmapsync.config.MainConfig;
import me.fabrimat.dynamicmapsync.dynmap.DynmapManager;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.JobManager;
import me.fabrimat.dynamicmapsync.job.command.CommandManager;
import me.fabrimat.dynamicmapsync.job.command.commands.ExitCommand;
import me.fabrimat.dynamicmapsync.job.command.commands.JobCommand;
import me.fabrimat.dynamicmapsync.job.command.commands.LogCommand;
import me.fabrimat.dynamicmapsync.job.command.commands.UrlCommand;
import me.fabrimat.dynamicmapsync.log.DynamicMapSyncLogger;
import me.fabrimat.dynamicmapsync.log.LoggingOutputStream;
import me.fabrimat.dynamicmapsync.scheduler.Scheduler;
import me.fabrimat.dynamicmapsync.scheduler.DynamicMapSyncScheduler;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicMapSync extends AppServer {

    private final CommandManager commandManager;
    private final JobManager jobManager;
    private final DynmapManager dynmapManager;
    private final Logger logger;
    private final Scheduler scheduler;
    private final JobConfig jobConfig;
    private final MainConfig mainConfig;
    private final ReentrantLock shutdownLock = new ReentrantLock();
    private final ConsoleReader consoleReader;
    private volatile boolean isRunning = false;

    public DynamicMapSync() throws IOException {
        this.consoleReader = new ConsoleReader();
        this.consoleReader.setExpandEvents(false);
        this.logger = new DynamicMapSyncLogger("DynamicMapSync", "dynamicmapsync.log", this.consoleReader);

        System.setErr(new PrintStream(new LoggingOutputStream(logger, Level.SEVERE), true));
        System.setOut(new PrintStream(new LoggingOutputStream(logger, Level.INFO), true));

        this.mainConfig = new MainConfig();
        this.jobConfig = new JobConfig();
        this.commandManager = new CommandManager();
        this.jobManager = new JobManager();
        this.dynmapManager = new DynmapManager();
        this.scheduler = new DynamicMapSyncScheduler(getMainConfig().getThreadPoolSize());
    }

    public static DynamicMapSync getInstance() {
        return (DynamicMapSync) AppServer.getInstance();
    }

    @Override
    public String getName() {
        return "DynamicMapSync";
    }

    @Override
    public String getVersion() {
        return (DynamicMapSync.class.getPackage().getImplementationVersion() == null) ?
                "1.0-SNAPSHOT" : DynamicMapSync.class.getPackage().getImplementationVersion();
    }

    @Override
    public void start() {
        getMainConfig().loadConfiguration();
        getJobConfig().loadConfiguration();
        getCommandManager().registerCommand(new ExitCommand());
        getCommandManager().registerCommand(new LogCommand());
        getCommandManager().registerCommand(new JobCommand());
        getCommandManager().registerCommand(new UrlCommand());

        getDynmapManager().initialize();

        if(getMainConfig().isDebug()) {
            getLogger().setLevel(Level.ALL);
            getLogger().info("Debug mode enabled");
        }

        setRunning(true);

        for (Job job : getJobConfig().getLoadedJobs()) {
            getJobManager().registerJob(job);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> independentThreadStop(false)));
    }

    @Override
    public void stop() {
        new Thread("Shutdown Thread") {
            @Override
            public void run() {
                independentThreadStop(true);
            }
        }.start();
    }

    private void independentThreadStop(boolean callSystemExit) {
        getShutdownLock().lock();

        if (!isRunning()) {
            getShutdownLock().unlock();
            return;
        }
        setRunning(false);
        getLogger().info("Shutting down");

        getLogger().info("Unregistering jobs");
        getJobManager().unregisterAllJobs();

        getLogger().info("Closing scheduler");
        if (!getScheduler().cancelAll(5, TimeUnit.SECONDS)) {
            getLogger().info("Scheduler not responding. Terminating forcefully");
            getScheduler().cancelAllNow();
        }

        getLogger().info("Thank you and goodbye");

        if (getLogger() instanceof DynamicMapSyncLogger dynamicMapSyncLogger) {
            dynamicMapSyncLogger.getDispatcher().interrupt();
            try {
                dynamicMapSyncLogger.getDispatcher().join(1000);
            } catch (InterruptedException ignored) {
            }
        }

        for (Handler handler : getLogger().getHandlers()) {
            handler.close();
        }

        getShutdownLock().unlock();

        if (callSystemExit) {
            System.exit(0);
        }
    }


    public synchronized boolean isRunning() {
        return isRunning;
    }

    private synchronized void setRunning(boolean running) {
        isRunning = running;
    }

    public ReentrantLock getShutdownLock() {
        return shutdownLock;
    }

    @Override
    public JobManager getJobManager() {
        return jobManager;
    }

    @Override
    public DynmapManager getDynmapManager() {
        return dynmapManager;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public JobConfig getJobConfig() {
        return jobConfig;
    }

    @Override
    public MainConfig getMainConfig() {
        return this.mainConfig;
    }

    public ConsoleReader getConsoleReader() {
        return consoleReader;
    }

}
