package com.example.mymemonotes.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymemonotes.R;


public class AddEditNoteActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDescription;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_add_edit_note);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        btnSave = findViewById(R.id.btnSave);

        // Check if an existing note is being edited
        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_ID")) {
            setTitle("Edit Note");
            // Set the existing title
            etTitle.setText(intent.getStringExtra("EXTRA_TITLE"));
            // Set the existing description
            etDescription.setText(intent.getStringExtra("EXTRA_DESCRIPTION"));
        } else {
            setTitle("Add Note");
        }

        btnSave.setOnClickListener(v -> {
            saveNote();
        });

    }

    private void saveNote() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Description is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra("EXTRA_TITLE", title);
        data.putExtra("EXTRA_DESCRIPTION", description);

        int id = getIntent().getIntExtra("EXTRA_ID", -1);
        if (id != -1) {
            // Include the note ID if editing an existing note
            data.putExtra("EXTRA_ID", id);
        }

        // Set the result to OK and attach the data
        setResult(RESULT_OK, data);
        finish();
    }
}