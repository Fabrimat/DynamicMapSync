package me.fabrimat.dynmapsync.dynmap.command.sub;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.dynmap.command.sub.sync.ConfigSyncSubCommand;
import me.fabrimat.dynmapsync.dynmap.command.sub.sync.MarkerSyncSubCommand;
import me.fabrimat.dynmapsync.dynmap.command.sub.sync.PlayerSyncSubCommand;
import me.fabrimat.dynmapsync.dynmap.command.sub.sync.TileSyncSubCommand;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.util.Arrays;
import java.util.Locale;

public class SyncSubCommand implements DynmapSubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        Preconditions.checkArgument(args != null && args.length > 0, "Not enough arguments");
        String subCommand = args[0].toUpperCase(Locale.ROOT);
        if (args.length > 1) {
            args = Arrays.copyOfRange(args, 1, args.length);
        }
        return switch (subCommand) {
            case "PLAYERS" ->
                    new PlayerSyncSubCommand().execute(job, step, command, args);
            case "CONFIG" ->
                    new ConfigSyncSubCommand().execute(job, step, command, args);
            case "TILES" ->
                    new TileSyncSubCommand().execute(job, step, command, args);
            case "MARKERS" ->
                    new MarkerSyncSubCommand().execute(job, step, command, args);
            default -> false;
        };
    }
}
