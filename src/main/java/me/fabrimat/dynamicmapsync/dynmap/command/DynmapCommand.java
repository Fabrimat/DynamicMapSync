package me.fabrimat.dynamicmapsync.dynmap.command;

import com.google.common.base.Preconditions;
import me.fabrimat.dynamicmapsync.dynmap.command.sub.SyncSubCommand;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.step.Step;

import java.util.Arrays;
import java.util.Locale;

public class DynmapCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) throws Exception {
        Preconditions.checkArgument(args != null && args.length > 0, "Not enough arguments");
        String subCommand = args[0].toUpperCase(Locale.ROOT);
        if (args.length > 1) {
            args = Arrays.copyOfRange(args, 1, args.length);
        }
        switch (subCommand) {
            case "SYNC":
                return new SyncSubCommand().execute(job, step, this, args);
            case "CLEAR":
                // TODO
        }
        return false;
    }

    @Override
    public String getCommand() {
        return "DYNMAP";
    }
}
