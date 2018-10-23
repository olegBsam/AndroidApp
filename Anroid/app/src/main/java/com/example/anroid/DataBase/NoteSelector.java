package com.example.anroid.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.anroid.DataBase.entities.Note;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoteSelector extends AsyncTask<String, Void, ArrayList<Note>> {
    public interface AsyncResponse {
        void processFinish(ArrayList<Note> output) throws ExecutionException, InterruptedException;
    }

    private DAO dao;
    private NoteSelector.AsyncResponse delegate;

    public NoteSelector(NoteSelector.AsyncResponse asyncResponse, DAO dao) {
        this.delegate = asyncResponse;
        this.dao = dao;
    }

    @Override
    protected void onPostExecute(ArrayList<Note> result) {
        try {
            delegate.processFinish(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayList<Note> doInBackground(String... strings) {
        ArrayList<Note> notes = new ArrayList<>();

        String selectQuery = "SELECT * FROM Notes Where User='" + strings[0] + "'";

        SQLiteDatabase db = dao.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    Note note = new Note(cursor.getInt(0), cursor.getString(2));
                    byte[] object = cursor.getBlob(3);
                    if(object != null){
                        Bitmap bmp = BitmapFactory.decodeByteArray(object, 0, object.length);
                        note.addImage(bmp);
                    }
                    notes.add(note);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }

        return notes;
    }
}
