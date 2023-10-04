package me.fabrimat.dynmapsync.dynmap.command;

import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapUtils;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;
import me.fabrimat.dynmapsync.job.step.Step;

public class PlayerSyncCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) throws CommandException {
        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();

        //DynmapUtils.createWorldFileIfNotExists();


        return true;
    }

    @Override
    public String getCommand() {
        return "PLAYERSYNC";
    }
}
