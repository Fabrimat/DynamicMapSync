package me.fabrimat.dynmapsync.dynmap.json.update;

import com.google.gson.annotations.SerializedName;

public class DayNight extends Update{
    public final String type = "daynight";

    @SerializedName("isday")
    private boolean isDay;

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }
}
