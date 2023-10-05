package me.fabrimat.dynmapsync.dynmap.command.sub;

import me.fabrimat.dynmapsync.dynmap.command.sub.sync.ConfigSyncSubCommand;
import me.fabrimat.dynmapsync.dynmap.command.sub.sync.PlayerSyncSubCommand;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.util.Arrays;
import java.util.Locale;

public class SyncSubCommand implements SubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        switch (args[1].toUpperCase(Locale.ROOT)) {
            case "PLAYERS":
                return new PlayerSyncSubCommand().execute(job, step, command, Arrays.copyOfRange(args, 1, args.length-1));
            case "CONFIG":
                return new ConfigSyncSubCommand().execute(job, step, command, Arrays.copyOfRange(args, 1, args.length-1));
            case "TILES":
                // TODO
            case "MARKERS":
                // TODO
        }
        return false;
    }
}
