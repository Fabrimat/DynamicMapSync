package me.fabrimat.dynmapsync.config;

import me.fabrimat.dynmapsync.DynmapSync;

import java.io.IOException;
import java.util.logging.Level;

public final class MainConfig extends ConfigManager {

    private int threadPoolSize;
    private DynmapConfigSection dynmapConfig;

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
        this.dynmapConfig = new DynmapConfigSection(getConfiguration().getSection("dynmap"));
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    private void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public DynmapConfigSection getDynmapConfig() {
        return dynmapConfig;
    }


}
