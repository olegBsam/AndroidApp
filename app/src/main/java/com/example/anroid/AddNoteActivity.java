package com.example.anroid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anroid.database.DAO;
import com.example.anroid.database.entities.Note;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class AddNoteActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 0;

    private static NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        int id = viewModel.id;

        setContentView(R.layout.activity_add_note);

        EditText tv = findViewById(R.id.add_note_text);
        tv.setText(viewModel.text);

        String userName = viewModel.name;

        findViewById(R.id.button_decline).setOnClickListener((listener)-> this.finish());

        findViewById(R.id.button_accept).setOnClickListener((listener)-> {
            String newText = ((TextView)findViewById(R.id.add_note_text)).getText().toString();
            Note note = new Note(newText, userName);

            if(viewModel.image != null) {
                note.setImage(viewModel.image);
            }

            if (id != -1){
                note.setId(id);
                DAO.dropNoteById(id, AddNoteActivity.this);
            }
            DAO.insertNote(note, AddNoteActivity.this);
            this.finish();
        });

        findViewById(R.id.imageButtonAddImage).setOnClickListener((listener)->{
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        });
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
            Bitmap thumbnailBitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            if (thumbnailBitmap != null) {
                viewModel.image = getBitmapAsByteArray(thumbnailBitmap);
            }
        }
    }
}