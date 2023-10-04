package me.fabrimat.dynmapsync.config;

import java.io.IOException;

public final class MainConfig extends ConfigManager {
    
    private int threadPoolSize;
    private int nestedJobsProtection;
    private int urlTimeout;
    private DynmapConfigSection dynmapConfig;
    
    public void loadConfiguration() {
        try {
            loadConfig("config.yml", ConfigurationProvider.getProvider(YamlConfiguration.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void loadConfig(String fileName, ConfigurationProvider configurationProvider) throws IOException {
        super.loadConfig(fileName, configurationProvider);
        this.setThreadPoolSize(getConfiguration().getInt("thread-pool-size", 10));
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
