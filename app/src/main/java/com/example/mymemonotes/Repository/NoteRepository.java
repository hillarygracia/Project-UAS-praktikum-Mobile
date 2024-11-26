package com.example.mymemonotes.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mymemonotes.Data.Note;
import com.example.mymemonotes.Data.NoteDao;
import com.example.mymemonotes.Data.NoteDatabase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes; // LiveData to observe all notes
    private ExecutorService executorService; // Executor for background tasks

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Note>> getAllNotes() {
        // Return LiveData of all notes
        return allNotes;
    }

    public void insert(Note note) {
        // Insert note asynchronously
        executorService.execute(() -> noteDao.insert(note));
    }

    public void update(Note note) {
        // Update note asynchronously
        executorService.execute(() -> noteDao.update(note));
    }

    public void delete(Note note) {
        // Delete note asynchronously
        executorService.execute(() -> noteDao.delete(note));
    }

}