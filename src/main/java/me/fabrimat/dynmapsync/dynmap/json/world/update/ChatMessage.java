package me.fabrimat.dynmapsync.dynmap.json.world.update;

public class ChatMessage extends Update {
    public final String type = "chat";
    private String source;
    private String playerName;
    private String message;
    private String account;
    private String channel;

    public ChatMessage(long timestamp, String source, String playerName, String message, String account, String channel) {
        super(timestamp);
        this.source = source;
        this.playerName = playerName;
        this.message = message;
        this.account = account;
        this.channel = channel;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
