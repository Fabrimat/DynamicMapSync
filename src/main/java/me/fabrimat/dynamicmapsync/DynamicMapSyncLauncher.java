package me.fabrimat.dynamicmapsync;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import me.fabrimat.dynamicmapsync.job.command.Command;

import java.util.Collections;

public class DynamicMapSyncLauncher {

    public static void main(String[] args) throws Exception {
        DynamicMapSync dynamicMapSync = new DynamicMapSync();
        AppServer.setInstance(dynamicMapSync);
        dynamicMapSync.getLogger().info("Enabling " + dynamicMapSync.getName() + " version " + dynamicMapSync.getVersion());

        dynamicMapSync.start();

        OptionParser parser = new OptionParser();
        parser.acceptsAll(Collections.singletonList("noconsole"), "Disable console input");
        OptionSet options = parser.parse(args);

        if (!options.has("noconsole")) {
            String line;
            while (dynamicMapSync.isRunning() && (line = dynamicMapSync.getConsoleReader().readLine(">")) != null) {
                if (!dynamicMapSync.getCommandManager().dispatchCommand(new Command(line))) {
                    dynamicMapSync.getLogger().warning("Command not found");
                }
            }
        }
    }

}
