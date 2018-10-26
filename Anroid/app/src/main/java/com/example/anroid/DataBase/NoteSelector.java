package com.example.anroid.DataBase;
import android.os.AsyncTask;

import com.example.anroid.DataBase.entities.Note;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NoteSelector extends AsyncTask<String, Void, ArrayList<Note>> {
    public interface AsyncResponse {
        void processFinish(ArrayList<Note> output) throws ExecutionException, InterruptedException;
    }

    private final NotesDAO notesDAO;
    private NoteSelector.AsyncResponse delegate;

    public NoteSelector(AsyncResponse delegate, AppDatabase database) {
        notesDAO = database.notesDAO();
        this.delegate = delegate;
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
        return new ArrayList<>(notesDAO.getAllNotesByUserName(strings[0]));
    }
}
