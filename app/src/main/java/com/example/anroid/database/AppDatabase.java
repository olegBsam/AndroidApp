package com.example.anroid.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.anroid.database.entities.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract NotesDAO notesDAO();

    public static String dataBaseName = "user-database";

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, dataBaseName).build();
        }
        return INSTANCE;
    }
}
