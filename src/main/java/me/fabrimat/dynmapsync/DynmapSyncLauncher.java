package me.fabrimat.dynmapsync;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import me.fabrimat.dynmapsync.job.command.Command;

import java.util.Collections;

public class DynmapSyncLauncher {

    public static void main(String[] args) throws Exception {
        DynmapSync dynmapSync = new DynmapSync();
        AppServer.setInstance(dynmapSync);
        dynmapSync.getLogger().info("Enabling " + dynmapSync.getName() + " version " + dynmapSync.getVersion());

        dynmapSync.start();

        OptionParser parser = new OptionParser();
        parser.acceptsAll(Collections.singletonList("noconsole"), "Disable console input");
        OptionSet options = parser.parse(args);

        if (!options.has("noconsole")) {
            String line;
            while (dynmapSync.isRunning() && (line = dynmapSync.getConsoleReader().readLine(">")) != null) {
                if (!dynmapSync.getCommandManager().dispatchCommand(new Command(line))) {
                    dynmapSync.getLogger().warning("Command not found");
                }
            }
        }
    }

}
