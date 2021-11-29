package com.example.a413project.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.a413project.Database.model.Classification;

@androidx.room.Database(entities = {Classification.class}, version = 1, exportSchema = true)
public abstract class Database extends RoomDatabase {
    // define base attribute of the database
    public static final String dbName = "ResultData.db";
    // the instance of this class
    private static volatile Database instance;

    public static synchronized Database getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static Database create(final Context context){
        return Room.databaseBuilder(context, Database.class, dbName).build();
    }
    public abstract RoomDAO getRoomDAO();

}
