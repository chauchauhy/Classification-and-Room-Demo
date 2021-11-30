package com.example.a413project.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.a413project.Database.model.Classification;

import java.util.List;

// the Data access object of the room
@Dao
public interface RoomDAO {
    String tableName = "ClassificationTable";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(Classification classification);

    @Query("SELECT * FROM " + tableName)
    List<Classification> selectAll();

    @Update
    void updateData(Classification classification);

    @Delete
    void deleteData(Classification classification);

    @Query("DELETE FROM " + tableName)
    void removeAllData();
}

