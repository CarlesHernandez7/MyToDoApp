package com.tecnocampus.apps2324p4carleshernandez.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.tecnocampus.apps2324p4carleshernandez.domain.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    public abstract TaskDao taskDao();
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile RoomDatabase INSTANCE;

    static RoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RoomDatabase.class, "MyToDoAppDatabase")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
