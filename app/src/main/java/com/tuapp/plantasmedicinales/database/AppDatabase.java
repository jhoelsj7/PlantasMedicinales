package com.tuapp.plantasmedicinales.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.tuapp.plantasmedicinales.Plant;

@Database(entities = {Plant.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract PlantDao plantDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "plantas_db"
                    ).fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}