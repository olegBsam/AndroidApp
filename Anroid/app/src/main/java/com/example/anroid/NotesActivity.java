package com.example.anroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anroid.DataBase.DAO;
import com.example.anroid.DataBase.entities.Note;

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
                LinearLayout layout = new LinearLayout(context);
                TextView textView = new TextView(context);
                textView.setText(note.getText());
                layout.addView(textView);

                GradientDrawable border = new GradientDrawable();
                border.setColor(0xFFFFFFFF);
                border.setStroke(1, 0xFF000000);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.setBackground(border);
                }

                notesLayout.addView(layout);
            }
        });
    }
}
