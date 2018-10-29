package com.example.anroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.anroid.DataBase.DAO;
import com.example.anroid.DataBase.entities.Note;

public class AddNoteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        String userName = getIntent().getExtras().getString("Name");

        findViewById(R.id.button_decline).setOnClickListener((listener)-> this.finish());

        findViewById(R.id.button_accept).setOnClickListener((listener)-> {
            String text = ((TextView)findViewById(R.id.add_note_text)).getText().toString();
            DAO.insertNote(new Note(text, userName), AddNoteActivity.this);
            this.finish();
        });
    }
}
