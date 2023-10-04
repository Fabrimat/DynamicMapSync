package me.fabrimat.dynmapsync.dynmap.json.update;

public class PlayerQuitMessage extends Update {
    public final String type = "playerjoin";
    public String playerName;
    public String account;

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
