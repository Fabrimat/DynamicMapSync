package me.fabrimat.dynmapsync.dynmap.json.update;

public class Tile extends Update {
    public final String type = "tile";
    private String name;

    public Tile(long timestamp, String name) {
        super(timestamp);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
