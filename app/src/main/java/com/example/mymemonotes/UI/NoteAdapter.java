package com.example.mymemonotes.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymemonotes.Data.Note;
import com.example.mymemonotes.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private final OnItemClickListener listener;
    private List<Note> notes = new ArrayList<>();

    public NoteAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        // Get the note at the current position
        Note currentNote = notes.get(position);
        holder.tvNoteTitle.setText(currentNote.getTitle());
        holder.tvNoteDesc.setText(currentNote.getDescription());
        // Handle edit button click
        holder.ivEdit.setOnClickListener(v -> listener.onEditClick(currentNote));
        // Handle delete button click
        holder.ivDelete.setOnClickListener(v -> listener.onDeleteClick(currentNote));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    // Updates the list of notes and notifies the adapter that the data set has changed.
    public void setNotes(List<Note> notes) {
        this.notes = notes;
        // Refresh the RecyclerView
        notifyDataSetChanged();
    }

    static class NoteHolder extends RecyclerView.ViewHolder {
        private final TextView tvNoteTitle;
        private final TextView tvNoteDesc;
        private final ImageView ivEdit;
        private final ImageView ivDelete;

        public NoteHolder(View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById( R.id.tvNoteTitle);
            tvNoteDesc = itemView.findViewById(R.id.tvNoteDesc);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

    // Interface for handling click events on note items
    public interface OnItemClickListener {
        void onEditClick(Note note);

        void onDeleteClick(Note note);
    }
}