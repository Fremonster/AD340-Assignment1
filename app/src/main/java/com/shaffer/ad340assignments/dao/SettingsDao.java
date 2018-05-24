package com.shaffer.ad340assignments.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shaffer.ad340assignments.entity.Settings;

import java.util.List;

@Dao
public interface SettingsDao {

    @Query("SELECT * FROM settings")
    List<Settings> getAll();

    @Query("SELECT * FROM settings WHERE id IN (:ids)")
    List<Settings> loadAllByIds(int[] ids);

    @Update
    void updateSettings(Settings... settings);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Settings... settings);

    @Delete
    void delete(Settings settings);
}
