package me.fabrimat.dynmapsync.dynmap.command;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.dynmap.command.sub.SyncSubCommand;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

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
