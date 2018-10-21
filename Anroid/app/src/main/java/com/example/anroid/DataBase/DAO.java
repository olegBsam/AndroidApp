package com.example.anroid.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DAO extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notesDB";

    public DAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableUsers = "CREATE TABLE Users(Id INTEGER PRIMARY KEY)";
        db.execSQL(tableUsers);

        String tableNotes = "CREATE TABLE Notes(Id INTEGER PRIMARY KEY autoincrement, User INTEGER REFERENCES Users(Id), Note TEXT)";
        db.execSQL(tableNotes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Notes");
        db.execSQL("DROP TABLE IF EXISTS Users");

        onCreate(db);
    }

    public void addUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Id", id);

        db.insert("Users", null, values);
        db.close();
    }

    private ArrayList<Integer> getAllUsers() {
        ArrayList<Integer> allUsers = new ArrayList<Integer>();

        String selectQuery = "SELECT * FROM Users";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                allUsers.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        db.close();

        return allUsers;
    }

    public void addNote(int userId, String note, Object image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User", userId);
        values.put("Note", note);

        db.insert("Notes", null, values);
        db.close();
    }

    public ArrayList<String> getAllUserNotes(int userId){
        ArrayList<String> notes = new ArrayList<>();

        String selectQuery = "SELECT * FROM Notes Where User=" + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                notes.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        db.close();

        return notes;
    }

    public void dropNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Notes", "Id" + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}
