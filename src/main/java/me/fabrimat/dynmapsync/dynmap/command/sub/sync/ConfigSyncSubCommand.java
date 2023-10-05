package me.fabrimat.dynmapsync.dynmap.command.sub.sync;

import com.google.common.base.Preconditions;
import me.fabrimat.dynmapsync.DynmapSync;
import me.fabrimat.dynmapsync.config.DynmapConfigSection;
import me.fabrimat.dynmapsync.dynmap.DynmapJson;
import me.fabrimat.dynmapsync.dynmap.DynmapManager;
import me.fabrimat.dynmapsync.dynmap.DynmapUtils;
import me.fabrimat.dynmapsync.dynmap.SourceMap;
import me.fabrimat.dynmapsync.dynmap.command.sub.SubCommand;
import me.fabrimat.dynmapsync.dynmap.json.DynmapConfig;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigMap;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigWorld;
import me.fabrimat.dynmapsync.job.Job;
import me.fabrimat.dynmapsync.job.command.CommandExecutor;
import me.fabrimat.dynmapsync.job.step.Step;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ConfigSyncSubCommand implements SubCommand {
    @Override
    public boolean execute(Job job, Step step, CommandExecutor command, String[] args) throws Exception {
        DynmapManager dynmapManager = DynmapSync.getInstance().getDynmapManager();
        boolean locked = dynmapManager.getConfigFileLock().tryLock(5, TimeUnit.SECONDS);
        if (!locked) {
            throw new IOException("Could not lock config file");
        }
        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        DynmapJson destinationJson = new DynmapJson(config.getDestinationPath(), DynmapJson.FileType.CONFIG, null);

        Map<String, SourceMap> sourceMaps = config.getSourceMaps();
        sourceMaps = sourceMaps.entrySet().stream()
                .filter(entry -> entry.getValue().syncPlayers())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<String> priority = config.getPriority();

        if (!priority.isEmpty()) {
            for (String map : priority) {
                if (sourceMaps.containsKey(map)) {
                    SourceMap sourceMap = sourceMaps.get(map);
                    sourceMaps.remove(map);
                    DynmapJson sourceJson = new DynmapJson(sourceMap.path(), DynmapJson.FileType.CONFIG, null);
                    if (sourceJson.getFile().exists()) {
                        copyValues(sourceJson, destinationJson);
                    }
                }
            }
        }
        return true;
    }

    private void copyValues(DynmapJson source, DynmapJson destination) {
        Preconditions.checkArgument(source.getFileType().equals(DynmapJson.FileType.CONFIG), "Source must be a config file");
        Preconditions.checkArgument(destination.getFileType().equals(DynmapJson.FileType.CONFIG), "Destination must be a config file");

        DynmapConfigSection config = DynmapSync.getInstance().getMainConfig().getDynmapConfig();
        DynmapConfig sConfig = (DynmapConfig) source.getDynmapFile();
        DynmapConfig dConfig = (DynmapConfig) destination.getDynmapFile();

        List<ConfigWorld> sWorlds = List.of(sConfig.getWorlds());
        List<ConfigWorld> dWorlds = new ArrayList<>(List.of(dConfig.getWorlds()));

        for (ConfigWorld sWorld : sWorlds) {
            sWorld.setName(DynmapUtils.rewriteWorldName(sWorld.getName(), config.getWorldRewrites()));
            if (dWorlds.stream().noneMatch(dWorld -> dWorld.getName().equals(sWorld.getName()))) {
                dWorlds.add(sWorld);
            }
        }
        dConfig.setWorlds(dWorlds.toArray(new ConfigWorld[0]));

        List<ConfigMap> sMaps = List.of(sConfig.getMaps());
        List<ConfigMap> dMaps = new ArrayList<>(List.of(dConfig.getMaps()));

        for (ConfigMap sMap : sMaps) {
            if (dMaps.stream().noneMatch(dMap -> dMap.getName().equals(sMap.getName()))) {
                dMaps.add(sMap);
            }
        }
        dConfig.setMaps(dMaps.toArray(new ConfigMap[0]));

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
