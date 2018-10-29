package com.example.anroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.anroid.database.DAO;
import com.example.anroid.database.entities.Note;
import com.example.anroid.gui.NoteItem;

import java.io.ByteArrayOutputStream;

public class NotesActivity extends AppCompatActivity {
    private String userName;

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        userName = getIntent().getExtras().getString("Name");
    }

    @Override
    protected void onStart() {
        super.onStart();

        findViewById(R.id.buttonAddNote).setOnClickListener((listener)->{
            Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
            intent.putExtra("Name", userName);
            startActivity(intent);
        });

        LinearLayout notesLayout = findViewById(R.id.notesList);
        notesLayout.removeAllViews();

        Context context = this;

        DAO.getAllUserNotes(this, userName, output -> {
            for(Note note : output){
                NoteItem ni = new NoteItem(context, note.getText(), note.getId());
                notesLayout.addView(ni);
            }
        });
    }
}
