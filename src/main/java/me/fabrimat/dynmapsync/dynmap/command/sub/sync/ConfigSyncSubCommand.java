package me.fabrimat.dynmapsync.dynmap.command.sub.sync;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.AppServer;
import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapJson;
import me.fabrimat.dynmapsync.dynmap.DynmapManager;
import me.fabrimat.dynmapsync.dynmap.utils.DynmapUtils;
import me.fabrimat.dynmapsync.dynmap.SourceMap;
import me.fabrimat.dynmapsync.dynmap.command.sub.DynmapSubCommand;
import me.fabrimat.dynmapsync.dynmap.json.DynmapConfigFile;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigMap;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigWorld;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class ConfigSyncSubCommand implements DynmapSubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        DynmapManager dynmapManager = DynmapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getConfigFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock config file");
        }
        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        DynmapJson destinationJson = new DynmapJson(config.getDestinationPath(), DynmapJson.FileType.CONFIG, null, true);

        ((DynmapConfigFile)destinationJson.getDynmapFile()).setWorlds(new ConfigWorld[]{});
        dynmapManager.getWorlds().clear();
        Map<String, SourceMap> sourceMaps = config.getSourceMaps();
        List<String> priority = config.getPriority();
        if (!priority.isEmpty()) {
            for (String map : priority) {
                if (sourceMaps.containsKey(map)) {
                    SourceMap sourceMap = sourceMaps.get(map);
                    sourceMaps.remove(map);
                    attemptCopy(destinationJson, sourceMap, map);
                }
            }
        }

        for (Map.Entry<String, SourceMap> entry : sourceMaps.entrySet()) {
            SourceMap sourceMap = entry.getValue();
            attemptCopy(destinationJson, sourceMap, entry.getKey());
        }

        for (ConfigWorld world : ((DynmapConfigFile) destinationJson.getDynmapFile()).getWorlds()) {
            Set<ConfigMap> maps = ConcurrentHashMap.newKeySet();
            if (world.getMaps() != null) {
                maps.addAll(List.of(world.getMaps()));
            }
            dynmapManager.getWorlds().put(world.getName(), maps);
        }

        destinationJson.writeFile();
        dynmapManager.setTimestamp("configSync", Timestamp.from(Instant.now()));
        dynmapManager.getConfigFileLock().unlock();

        return true;
    }

    private void attemptCopy(DynmapJson destinationJson, SourceMap sourceMap, String sourceName) {
        try {
            DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.CONFIG, null, false);
            if (sourceJson.getFile().exists()) {
                copyValues(sourceJson, destinationJson);
            }
        } catch (IOException | IllegalStateException e) {
            AppServer.getInstance().getLogger().warning("Could not copy values from %1 - ".replace("%1", sourceName) + e.getMessage());
        }
    }

    private void copyValues(DynmapJson source, DynmapJson destination) {
        Preconditions.checkArgument(source.getFileType().equals(DynmapJson.FileType.CONFIG), "Source must be a config file");
        Preconditions.checkArgument(destination.getFileType().equals(DynmapJson.FileType.CONFIG), "Destination must be a config file");

        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        DynmapConfigFile sConfig = (DynmapConfigFile) source.getDynmapFile();
        DynmapConfigFile dConfig = (DynmapConfigFile) destination.getDynmapFile();

        List<ConfigWorld> sWorlds = List.of(sConfig.getWorlds());
        List<ConfigWorld> dWorlds = new ArrayList<>(List.of(dConfig.getWorlds()));

        for (ConfigWorld sWorld : sWorlds) {
            sWorld.setName(DynmapUtils.rewriteWorldName(sWorld.getName(), config.getWorldRewrites()));
            if (dWorlds.stream().noneMatch(dWorld -> dWorld.getName().equals(sWorld.getName()))) {
                dWorlds.add(sWorld);
            } else {
                for (ConfigWorld dWorld : dWorlds) {
                    if (dWorld.getName().equals(sWorld.getName())) {
                        List<ConfigMap> sMaps = List.of(sWorld.getMaps());
                        List<ConfigMap> dMaps = new ArrayList<>();
                        if (dWorld.getMaps() != null) {
                            dMaps.addAll(List.of(dWorld.getMaps()));
                        }

                        for (ConfigMap sMap : sMaps) {
                            if (dMaps.stream().noneMatch(dMap -> dMap.getName().equals(sMap.getName()))) {
                                dMaps.add(sMap);
                            }
                        }
                        dWorld.setMaps(dMaps.toArray(new ConfigMap[0]));
                    }
                }
            }
        }
        dConfig.setWorlds(dWorlds.toArray(new ConfigWorld[0]));
        dConfig.setComponents(sConfig.getComponents());

        dConfig.setUpdateRate(sConfig.getUpdateRate());
        dConfig.setChatLengthLimit(sConfig.getChatLengthLimit());
        dConfig.setConfigHash(sConfig.getConfigHash());
        dConfig.setSpamMessage(sConfig.getSpamMessage());
        dConfig.setDefaultMap(sConfig.getDefaultMap());
        dConfig.setChatRequiresLoginMessage(sConfig.getChatRequiresLoginMessage());
        dConfig.setHiddenNameJoinMessage(sConfig.getHiddenNameJoinMessage());
        dConfig.setTitle(sConfig.getTitle());
        dConfig.setGrayPlayersWhenHidden(sConfig.isGrayPlayersWhenHidden());
        dConfig.setQuitMessage(sConfig.getQuitMessage());
        dConfig.setDefaultZoom(sConfig.getDefaultZoom());
        dConfig.setAllowChat(sConfig.isAllowChat());
        dConfig.setAllowWebChat(sConfig.isAllowWebChat());
        dConfig.setSidebarOpened(sConfig.isSidebarOpened());
        dConfig.setWebChatInterval(sConfig.getWebChatInterval());
        dConfig.setChatNotAllowedMessage(sConfig.getChatNotAllowedMessage());
        dConfig.setCoreVersion(sConfig.getCoreVersion());
        dConfig.setJoinMessage(sConfig.getJoinMessage());
        dConfig.setWebChatRequiresLogin(sConfig.isWebChatRequiresLogin());
        dConfig.setShowLayerControl(sConfig.getShowLayerControl());
        dConfig.setLoginEnabled(sConfig.isLoginEnabled());
        dConfig.setLoginRequired(sConfig.isLoginRequired());
        dConfig.setMaxCount(sConfig.getMaxCount());
        dConfig.setDynmapVersion(sConfig.getDynmapVersion());
        dConfig.setMapTypesMessage(sConfig.getMapTypesMessage());
        dConfig.setCyrillic(sConfig.isCyrillic());
        dConfig.setHiddenNameQuitMessage(sConfig.getHiddenNameQuitMessage());
        dConfig.setJsonFile(sConfig.isJsonFile());
        dConfig.setPlayersMessage(sConfig.getPlayersMessage());
        dConfig.setWebPrefix(sConfig.getWebPrefix());
        dConfig.setShowPlayerFacesInMenu(sConfig.isShowPlayerFacesInMenu());
        dConfig.setDefaultWorld(sConfig.getDefaultWorld());
    }
}
