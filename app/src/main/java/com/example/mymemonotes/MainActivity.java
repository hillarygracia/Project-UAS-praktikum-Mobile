package com.example.mymemonotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mymemonotes.Data.Note;
import com.example.mymemonotes.UI.AddEditNoteActivity;
import com.example.mymemonotes.UI.NoteAdapter;
import com.example.mymemonotes.UI.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.Nullable;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {

    private NoteViewModel noteViewModel;
    private static final int ADD_REQUEST = 1;
    private static final int EDIT_REQUEST = 2;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, AddEditNoteActivity.class), ADD_REQUEST);
        });

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        NoteAdapter noteAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);

        // Setup ViewModel
        noteViewModel = new ViewModelProvider(this).get( NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, notes -> noteAdapter.setNotes(notes));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            // Handle add process
            String title = data.getStringExtra("EXTRA_TITLE");
            String description = data.getStringExtra("EXTRA_DESCRIPTION");

            // Insert to database
            Note note = new Note(title, description);
            noteViewModel.insert(note);

        } else if (requestCode == EDIT_REQUEST && resultCode == RESULT_OK) {
            // Handle edit process
            int id = data.getIntExtra("EXTRA_ID", -1);
            if (id == -1) {
                return;
            }

            String title = data.getStringExtra("EXTRA_TITLE");
            String description = data.getStringExtra("EXTRA_DESCRIPTION");

            // Update existing note
            Note note = new Note(title, description);
            note.setId(id);
            noteViewModel.update(note);
        }
    }

    @Override
    public void onEditClick(Note note) {
        // Redirect to AddEditNoteActivity with data
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra("EXTRA_ID", note.getId());
        intent.putExtra("EXTRA_TITLE", note.getTitle());
        intent.putExtra("EXTRA_DESCRIPTION", note.getDescription());
        startActivityForResult(intent, EDIT_REQUEST);
    }

    @Override
    public void onDeleteClick(Note note) {
        // Show confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure to delete this note?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Delete note
                    noteViewModel.delete(note);
                })
                .setNegativeButton("No", null)
                .show();
    }
}