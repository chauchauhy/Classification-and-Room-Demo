package com.example.a413project.Database;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.a413project.Database.model.Classification;

// the agent of room database
public interface RoomDAO {
    String tableName = "ClassificationTable";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(Classification classification);
}

