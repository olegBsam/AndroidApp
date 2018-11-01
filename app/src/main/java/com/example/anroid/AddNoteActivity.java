package com.example.anroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.anroid.database.DAO;
import com.example.anroid.database.entities.Note;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class AddNoteActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 0;

    private byte[] image;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = null;

        findViewById(R.id.add_note_text).getBackground().clearColorFilter();

        setContentView(R.layout.activity_add_note);

        String userName = getIntent().getExtras().getString("Name");

        findViewById(R.id.button_decline).setOnClickListener((listener)-> this.finish());

        findViewById(R.id.button_accept).setOnClickListener((listener)-> {
            String text = ((TextView)findViewById(R.id.add_note_text)).getText().toString();
            Note note = new Note(text, userName);

            if(image != null) {
                note.setImage(image);
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
                image = getBitmapAsByteArray(thumbnailBitmap);
            }
        }
    }
}
