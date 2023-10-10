package me.fabrimat.dynamicmapsync.dynmap.json.world.update;

import com.google.gson.annotations.SerializedName;

public class DayNight extends Update {
    public final String type = "daynight";

    @SerializedName("isday")
    private boolean isDay;

    public DayNight(long timestamp, boolean isDay) {
        super(timestamp);
        this.isDay = isDay;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }
}
