package me.fabrimat.dynmapsync.config;

import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.DynmapSync;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public abstract class ConfigManager {

    private Configuration configuration;

    protected void loadConfig(String fileName, ConfigurationProvider configurationProvider) throws IOException {
        File file = new File(AppServer.getInstance().getWorkingDirectory(), fileName);

        if (!file.exists()) {
            try (InputStream in = AppServer.getInstance().getResourceAsStream(fileName)) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                DynmapSync.getInstance().getLogger().log(Level.SEVERE, "Error loading " + fileName, e);
            }
        }
        setConfiguration(configurationProvider.load(new File(AppServer.getInstance().getWorkingDirectory(), fileName)));
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    protected void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
