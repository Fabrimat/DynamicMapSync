package me.fabrimat.dynmapsync.config;

import me.fabrimat.dynmapsync.DynmapSync;

import java.io.IOException;
import java.util.logging.Level;

public final class MainConfig extends ConfigManager {

    private int threadPoolSize;
    private int recursiveJobsProtection;
    private int urlTimeout;
    private DynmapConfigSection dynmapConfig;
    private boolean debug;

    public void loadConfiguration() {
        try {
            loadConfig("config.yml", ConfigurationProvider.getProvider(YamlConfiguration.class));
        } catch (IOException e) {
            DynmapSync.getInstance().getLogger().log(Level.SEVERE, "Error loading config.yml", e);
        }
    }

    @Override
    protected void loadConfig(String fileName, ConfigurationProvider configurationProvider) throws IOException {
        super.loadConfig(fileName, configurationProvider);
        this.setThreadPoolSize(getConfiguration().getInt("thread-pool-size", 10));
        this.setRecursiveJobsProtection(getConfiguration().getInt("recursive-jobs-protection", 10));
        this.setUrlTimeout(getConfiguration().getInt("url-timeout", 5000));
        this.setDynmapConfig(new DynmapConfigSection(getConfiguration().getSection("dynmap")));
        this.setDebug(getConfiguration().getBoolean("debug", false));
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    private void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public int getRecursiveJobsProtection() {
        return recursiveJobsProtection;
    }

    private void setRecursiveJobsProtection(int recursiveJobsProtection) {
        this.recursiveJobsProtection = recursiveJobsProtection;
    }

    public int getUrlTimeout() {
        return urlTimeout;
    }

    private void setUrlTimeout(int urlTimeout) {
        this.urlTimeout = urlTimeout;
    }

    public DynmapConfigSection getDynmapConfig() {
        return dynmapConfig;
    }


    public void setDynmapConfig(DynmapConfigSection dynmapConfig) {
        this.dynmapConfig = dynmapConfig;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
