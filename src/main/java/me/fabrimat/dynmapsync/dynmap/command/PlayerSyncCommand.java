package me.fabrimat.dynmapsync.dynmap.command;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapUtils;
import me.fabrimat.dynmapsync.dynmap.SourceMap;
import me.fabrimat.dynmapsync.dynmap.json.DynmapPlayer;
import me.fabrimat.dynmapsync.dynmap.json.DynmapWorld;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.command.exceptions.CommandException;
import me.fabrimat.dynmapsync.job.step.Step;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerSyncCommand implements CommandExecutor {
    @Override
    public boolean execute(Job job, Step step, String[] args) throws IOException {
        if (args != null && args.length > 0) {
            switch (args[0].toUpperCase(Locale.ROOT)) {
                case "SYNC":
                    switch (args[1].toUpperCase(Locale.ROOT)) {
                        case "PLAYERS":
                            syncPlayers();
                            break;
                        case "TILES":
                            // TODO
                        case "MARKERS":
                            // TODO
                        default:
                            return false;
                    }
                    break;
                case "CLEAR":
                    // TODO
                default:
                    return false;
            }
        }
            return true;
    }

    private void syncPlayers() throws IOException {
        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        DynmapUtils.createWorldFileIfNotExists(config.getDestinationPath());
        DynmapWorld dynmapWorld = DynmapUtils.loadWorldFile(config.getDestinationPath());
        Preconditions.checkNotNull(dynmapWorld, "Dynmap destination world file is null");

        Map<String, SourceMap> sourceMaps = config.getSourceMaps();
        sourceMaps = sourceMaps.entrySet().stream()
                .filter(entry -> entry.getValue().syncPlayers())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<String> priority = config.getPriority();

        List<DynmapPlayer> players = new ArrayList<>();
        if (!priority.isEmpty()) {
            for (String map : priority) {
                if (sourceMaps.containsKey(map)) {
                    SourceMap sourceMap = sourceMaps.get(map);
                    sourceMaps.remove(map);
                    if (DynmapUtils.getWorldFile(sourceMap.path()).exists()) {
                        DynmapWorld dynWorld = DynmapUtils.loadWorldFile(sourceMap.path());
                        if (dynWorld != null) {
                            players.addAll(Arrays.stream(dynWorld.getPlayers()).toList());
                        }
                    }
                }
            }
        }

        for (Map.Entry<String, SourceMap> entry : sourceMaps.entrySet()) {
            SourceMap sourceMap = entry.getValue();
            if (DynmapUtils.getWorldFile(sourceMap.path()).exists()) {
                DynmapWorld world = DynmapUtils.loadWorldFile(sourceMap.path());
                if (world != null) {
                    players.addAll(Arrays.stream(world.getPlayers()).toList());
                }
            }
        }

        players.forEach(player ->
                player.setWorld(DynmapUtils.rewriteWorldName(player.getWorld(), config.getWorldRewrites())));

        dynmapWorld.setPlayers(players.toArray(new DynmapPlayer[0]));
        DynmapUtils.writeWorldFile(config.getDestinationPath(), dynmapWorld);
    }

    @Override
    public String getCommand() {
        return "DYNMAP";
    }
}
