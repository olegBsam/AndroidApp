package com.example.anroid.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anroid.DataBase.entities.Note;

import java.util.ArrayList;

public class DAO {
    public static void getAllUserNotes(Context context, String userName, NoteSelector.AsyncResponse response){
        NoteSelector selector = new NoteSelector(response, AppDatabase.getAppDatabase(context));

        selector.execute(userName);
    }

    public static void insertNotes(ArrayList<Note> list, Context context){
        class InsertNotes extends AsyncTask<ArrayList<Note>, Void, Void>{
            @Override
            protected Void doInBackground(ArrayList<Note>... lists) {
                AppDatabase.getAppDatabase(context).notesDAO().insertAll(lists[0]);
                return null;
            }
        }

        new InsertNotes().execute(list);
    }

    public static void insertNote(Note note, Context context){
        class InsertNotes extends AsyncTask<Note, Void, Void>{
            @Override
            protected Void doInBackground(Note... notes) {
                AppDatabase.getAppDatabase(context).notesDAO().insert(notes[0]);
                return null;
            }
        }

        new InsertNotes().execute(note);
    }

    public static void dropAllNotesByUserName(String name, Context context){
        class DropNotes extends AsyncTask<String, Void, Void>{
            @Override
            protected Void doInBackground(String... strings) {
                AppDatabase.getAppDatabase(context).notesDAO().deleteAllNotesByUserName(strings[0]);
                return null;
            }
        }

        new DropNotes().execute(name);
    }

    public static void dropNoteById(int id, Context context){
        class DropNote extends AsyncTask<Integer, Void, Void>{
            @Override
            protected Void doInBackground(Integer... integers) {
                AppDatabase.getAppDatabase(context).notesDAO().deleteNoteById(integers[0]);
                return null;
            }
        }

        new DropNote().execute(id);
    }
}
