package me.fabrimat.dynamicmapsync.dynmap.json.world.update;

public class ComponentMessage extends Update {
    public final String type = "component";

    public ComponentMessage(long timestamp) {
        super(timestamp);
    }
}
