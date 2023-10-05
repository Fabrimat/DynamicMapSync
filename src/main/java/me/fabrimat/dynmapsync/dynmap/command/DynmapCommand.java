package me.fabrimat.dynmapsync.dynmap.command;

import me.fabrimat.dynmapsync.dynmap.command.sub.SyncSubCommand;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.util.Arrays;
import java.util.Locale;

public class DynmapCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) throws Exception {
        if (args != null && args.length > 0) {
            switch (args[0].toUpperCase(Locale.ROOT)) {
                case "SYNC":
                    return new SyncSubCommand().execute(job, step, this, Arrays.copyOfRange(args, 1, args.length-1));
                case "CLEAR":
                    // TODO
            }
        }
        return false;
    }

    @Override
    public String getCommand() {
        return "DYNMAP";
    }
}
