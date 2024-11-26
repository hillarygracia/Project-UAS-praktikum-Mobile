package com.example.mymemonotes.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    // Singleton instance of the database
    private static NoteDatabase instance;

    // Get DAO for notes
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            // Build database
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database").fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}