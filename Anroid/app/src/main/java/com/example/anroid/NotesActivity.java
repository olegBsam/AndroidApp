package com.example.anroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anroid.DataBase.DAO;
import com.example.anroid.DataBase.NoteSelector;
import com.example.anroid.DataBase.entities.Note;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NotesActivity extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        userName = getIntent().getExtras().getString("Name");
    }

    @Override
    protected void onStart() {
        super.onStart();

        LinearLayout notesLayout = findViewById(R.id.notesList);
        notesLayout.removeAllViews();

        Context context = this;

        NoteSelector selector = new NoteSelector(new NoteSelector.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<Note> output) throws ExecutionException, InterruptedException {
                for(Note note : output){
                    TextView textView = new TextView(context);
                    textView.setText(note.getText());

                    notesLayout.addView(textView);
                }
            }
        }, DAO.getDAO(context));

        selector.execute(userName);
    }
}
