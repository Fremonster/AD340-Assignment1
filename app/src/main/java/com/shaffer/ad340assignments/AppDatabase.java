package com.shaffer.ad340assignments;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.shaffer.ad340assignments.dao.SettingsDao;
import com.shaffer.ad340assignments.entity.Settings;

@Database(entities = {Settings.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SettingsDao settingsDao();
}
