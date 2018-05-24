package com.shaffer.ad340assignments.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Settings {

    @PrimaryKey
    @NonNull
    private int id;

    @ColumnInfo(name = "daily_reminder_time")
    private String dailyMatchReminderTime = "00:00";

    // in miles - default set to 10 miles
    @ColumnInfo(name = "max_distance")
    private String maxDistance = "10";

    @ColumnInfo
    private String gender;

    @ColumnInfo(name = "is_public")
    private boolean isPublic = false;

    // min age set to 18
    @ColumnInfo(name = "min_age")
    private String minAge = "18";

    @ColumnInfo(name = "max_age")
    private String maxAge = "120";

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getDailyMatchReminderTime() {
        return dailyMatchReminderTime;
    }

    public void setDailyMatchReminderTime(String dailyMatchReminderTime) {
        this.dailyMatchReminderTime = dailyMatchReminderTime;
    }

    public String getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(String maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
