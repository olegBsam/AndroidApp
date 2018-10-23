package com.example.anroid.DataBase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.anroid.DataBase.entities.User;
import com.example.anroid.GetPost;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class UserSelector extends AsyncTask<String, Void, ArrayList<User>> {
    public interface AsyncResponse {
        void processFinish(ArrayList<User> output) throws ExecutionException, InterruptedException;
    }

    private DAO dao;
    private AsyncResponse delegate;

    public UserSelector(AsyncResponse asyncResponse, DAO dao) {
        this.delegate = asyncResponse;
        this.dao = dao;
    }

    @Override
    protected void onPostExecute(ArrayList<User> result) {
        try {
            delegate.processFinish(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ArrayList<User> doInBackground(String... strings) {
        ArrayList<User> allUsers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + strings[0];

        SQLiteDatabase db = dao.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                allUsers.add(new User(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        db.close();

        return allUsers;
    }
}