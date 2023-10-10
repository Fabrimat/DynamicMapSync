package me.fabrimat.dynamicmapsync.dynmap.command.sub;

import com.google.common.base.Preconditions;
import me.fabrimat.dynamicmapsync.dynmap.command.sub.sync.ConfigSyncSubCommand;
import me.fabrimat.dynamicmapsync.dynmap.command.sub.sync.MarkerSyncSubCommand;
import me.fabrimat.dynamicmapsync.dynmap.command.sub.sync.PlayerSyncSubCommand;
import me.fabrimat.dynamicmapsync.dynmap.command.sub.sync.TileSyncSubCommand;
import me.fabrimat.dynamicmapsync.job.Job;
import me.fabrimat.dynamicmapsync.job.command.CommandExecutor;
import me.fabrimat.dynamicmapsync.job.step.Step;

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
