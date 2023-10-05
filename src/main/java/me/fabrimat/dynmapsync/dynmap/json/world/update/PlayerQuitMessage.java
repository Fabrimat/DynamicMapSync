package me.fabrimat.dynmapsync.dynmap.json.world.update;

public class PlayerQuitMessage extends Update {
    public final String type = "playerquit";
    public String playerName;
    public String account;

    public PlayerQuitMessage(long timestamp, String playerName) {
        super(timestamp);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
