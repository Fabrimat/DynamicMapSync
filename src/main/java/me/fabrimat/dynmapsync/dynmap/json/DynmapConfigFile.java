package me.fabrimat.dynmapsync.dynmap.json;

import com.google.gson.annotations.SerializedName;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigMap;
import me.fabrimat.dynmapsync.dynmap.json.config.ConfigWorld;

public class DynmapConfigFile implements DynmapFile {
    @SerializedName("updaterate")
    private long updateRate;
    @SerializedName("chatlengthlimit")
    private int chatLengthLimit;
    @SerializedName("confighash")
    private long configHash;
    private ConfigWorld[] worlds = {};
    private ConfigMap[] maps = {};
    @SerializedName("spammessage")
    private String spamMessage;
    @SerializedName("defaultmap")
    private String defaultMap;
    @SerializedName("msg-chatrequireslogin")
    private String chatRequiresLoginMessage;
    @SerializedName("msg-hiddennamejoin")
    private String hiddenNameJoinMessage;
    private String title;
    @SerializedName("grayplayerswhenhidden")
    private boolean grayPlayersWhenHidden;
    @SerializedName("quitmessage")
    private String quitMessage;
    @SerializedName("defaultzoom")
    private int defaultZoom;
    @SerializedName("allowwebchat")
    private boolean allowWebChat;
    @SerializedName("allowchat")
    private boolean allowChat;
    @SerializedName("sidebaropened")
    private boolean sidebarOpened;
    @SerializedName("webchat-interval")
    private int webChatInterval;
    @SerializedName("msg-chatnotallowed")
    private String chatNotAllowedMessage;
    @SerializedName("coreversion")
    private String coreVersion;
    @SerializedName("joinmessage")
    private String joinMessage;
    @SerializedName("webchat-requires-login")
    private boolean webChatRequiresLogin;
    @SerializedName("showlayercontrol")
    private String showLayerControl;
    @SerializedName("login-enabled")
    private boolean loginEnabled;
    @SerializedName("loginrequired")
    private boolean loginRequired;
    @SerializedName("maxcount")
    private int maxCount;
    @SerializedName("dynmapversion")
    private String dynmapVersion;
    @SerializedName("msg-maptypes")
    private String mapTypesMessage;
    private boolean cyrillic;
    @SerializedName("msg-hiddennamequit")
    private String hiddenNameQuitMessage;
    @SerializedName("jsonfile")
    private boolean jsonFile;
    @SerializedName("msg-players")
    private String playersMessage;
    @SerializedName("webprefix")
    private String webPrefix;
    @SerializedName("showplayerfacesinmenu")
    private boolean showPlayerFacesInMenu;
    @SerializedName("defaultworld")
    private String defaultWorld;

    public DynmapConfigFile(long updateRate, int chatLengthLimit, long configHash, ConfigWorld[] worlds, ConfigMap[] maps, String spamMessage, String defaultMap, String chatRequiresLoginMessage, String hiddenNameJoinMessage, String title, boolean grayPlayersWhenHidden, String quitMessage, int defaultZoom, boolean allowWebChat, boolean allowChat, boolean sidebarOpened, int webChatInterval, String chatNotAllowedMessage, String coreVersion, String joinMessage, boolean webChatRequiresLogin, String showLayerControl, boolean loginEnabled, boolean loginRequired, int maxCount, String dynmapVersion, String mapTypesMessage, boolean cyrillic, String hiddenNameQuitMessage, boolean jsonFile, String playersMessage, String webPrefix, boolean showPlayerFacesInMenu, String defaultWorld) {
        this.updateRate = updateRate;
        this.chatLengthLimit = chatLengthLimit;
        this.configHash = configHash;
        this.worlds = worlds;
        this.maps = maps;
        this.spamMessage = spamMessage;
        this.defaultMap = defaultMap;
        this.chatRequiresLoginMessage = chatRequiresLoginMessage;
        this.hiddenNameJoinMessage = hiddenNameJoinMessage;
        this.title = title;
        this.grayPlayersWhenHidden = grayPlayersWhenHidden;
        this.quitMessage = quitMessage;
        this.defaultZoom = defaultZoom;
        this.allowWebChat = allowWebChat;
        this.allowChat = allowChat;
        this.sidebarOpened = sidebarOpened;
        this.webChatInterval = webChatInterval;
        this.chatNotAllowedMessage = chatNotAllowedMessage;
        this.coreVersion = coreVersion;
        this.joinMessage = joinMessage;
        this.webChatRequiresLogin = webChatRequiresLogin;
        this.showLayerControl = showLayerControl;
        this.loginEnabled = loginEnabled;
        this.loginRequired = loginRequired;
        this.maxCount = maxCount;
        this.dynmapVersion = dynmapVersion;
        this.mapTypesMessage = mapTypesMessage;
        this.cyrillic = cyrillic;
        this.hiddenNameQuitMessage = hiddenNameQuitMessage;
        this.jsonFile = jsonFile;
        this.playersMessage = playersMessage;
        this.webPrefix = webPrefix;
        this.showPlayerFacesInMenu = showPlayerFacesInMenu;
        this.defaultWorld = defaultWorld;
    }

    public DynmapConfigFile() {

    }

    public long getUpdateRate() {
        return updateRate;
    }

    public void setUpdateRate(long updateRate) {
        this.updateRate = updateRate;
    }

    public int getChatLengthLimit() {
        return chatLengthLimit;
    }

    public void setChatLengthLimit(int chatLengthLimit) {
        this.chatLengthLimit = chatLengthLimit;
    }

    public long getConfigHash() {
        return configHash;
    }

    public void setConfigHash(long configHash) {
        this.configHash = configHash;
    }

    public ConfigWorld[] getWorlds() {
        return worlds;
    }

    public void setWorlds(ConfigWorld[] worlds) {
        this.worlds = worlds;
    }

    public ConfigMap[] getMaps() {
        return maps;
    }

    public void setMaps(ConfigMap[] maps) {
        this.maps = maps;
    }

    public String getSpamMessage() {
        return spamMessage;
    }

    public void setSpamMessage(String spamMessage) {
        this.spamMessage = spamMessage;
    }

    public String getDefaultMap() {
        return defaultMap;
    }

    public void setDefaultMap(String defaultMap) {
        this.defaultMap = defaultMap;
    }

    public String getChatRequiresLoginMessage() {
        return chatRequiresLoginMessage;
    }

    public void setChatRequiresLoginMessage(String chatRequiresLoginMessage) {
        this.chatRequiresLoginMessage = chatRequiresLoginMessage;
    }

    public String getHiddenNameJoinMessage() {
        return hiddenNameJoinMessage;
    }

    public void setHiddenNameJoinMessage(String hiddenNameJoinMessage) {
        this.hiddenNameJoinMessage = hiddenNameJoinMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isGrayPlayersWhenHidden() {
        return grayPlayersWhenHidden;
    }

    public void setGrayPlayersWhenHidden(boolean grayPlayersWhenHidden) {
        this.grayPlayersWhenHidden = grayPlayersWhenHidden;
    }

    public String getQuitMessage() {
        return quitMessage;
    }

    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    public int getDefaultZoom() {
        return defaultZoom;
    }

    public void setDefaultZoom(int defaultZoom) {
        this.defaultZoom = defaultZoom;
    }

    public boolean isAllowWebChat() {
        return allowWebChat;
    }

    public void setAllowWebChat(boolean allowWebChat) {
        this.allowWebChat = allowWebChat;
    }

    public boolean isAllowChat() {
        return allowChat;
    }

    public void setAllowChat(boolean allowChat) {
        this.allowChat = allowChat;
    }

    public boolean isSidebarOpened() {
        return sidebarOpened;
    }

    public void setSidebarOpened(boolean sidebarOpened) {
        this.sidebarOpened = sidebarOpened;
    }

    public int getWebChatInterval() {
        return webChatInterval;
    }

    public void setWebChatInterval(int webChatInterval) {
        this.webChatInterval = webChatInterval;
    }

    public String getChatNotAllowedMessage() {
        return chatNotAllowedMessage;
    }

    public void setChatNotAllowedMessage(String chatNotAllowedMessage) {
        this.chatNotAllowedMessage = chatNotAllowedMessage;
    }

    public String getCoreVersion() {
        return coreVersion;
    }

    public void setCoreVersion(String coreVersion) {
        this.coreVersion = coreVersion;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    public boolean isWebChatRequiresLogin() {
        return webChatRequiresLogin;
    }

    public void setWebChatRequiresLogin(boolean webChatRequiresLogin) {
        this.webChatRequiresLogin = webChatRequiresLogin;
    }

    public String getShowLayerControl() {
        return showLayerControl;
    }

    public void setShowLayerControl(String showLayerControl) {
        this.showLayerControl = showLayerControl;
    }

    public boolean isLoginEnabled() {
        return loginEnabled;
    }

    public void setLoginEnabled(boolean loginEnabled) {
        this.loginEnabled = loginEnabled;
    }

    public boolean isLoginRequired() {
        return loginRequired;
    }

    public void setLoginRequired(boolean loginRequired) {
        this.loginRequired = loginRequired;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public String getDynmapVersion() {
        return dynmapVersion;
    }

    public void setDynmapVersion(String dynmapVersion) {
        this.dynmapVersion = dynmapVersion;
    }

    public String getMapTypesMessage() {
        return mapTypesMessage;
    }

    public void setMapTypesMessage(String mapTypesMessage) {
        this.mapTypesMessage = mapTypesMessage;
    }

    public boolean isCyrillic() {
        return cyrillic;
    }

    public void setCyrillic(boolean cyrillic) {
        this.cyrillic = cyrillic;
    }

    public String getHiddenNameQuitMessage() {
        return hiddenNameQuitMessage;
    }

    public void setHiddenNameQuitMessage(String hiddenNameQuitMessage) {
        this.hiddenNameQuitMessage = hiddenNameQuitMessage;
    }

    public boolean isJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(boolean jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getPlayersMessage() {
        return playersMessage;
    }

    public void setPlayersMessage(String playersMessage) {
        this.playersMessage = playersMessage;
    }

    public String getWebPrefix() {
        return webPrefix;
    }

    public void setWebPrefix(String webPrefix) {
        this.webPrefix = webPrefix;
    }

    public boolean isShowPlayerFacesInMenu() {
        return showPlayerFacesInMenu;
    }

    public void setShowPlayerFacesInMenu(boolean showPlayerFacesInMenu) {
        this.showPlayerFacesInMenu = showPlayerFacesInMenu;
    }

    public String getDefaultWorld() {
        return defaultWorld;
    }

    public void setDefaultWorld(String defaultWorld) {
        this.defaultWorld = defaultWorld;
    }
}
