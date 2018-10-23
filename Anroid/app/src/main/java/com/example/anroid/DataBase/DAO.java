package com.example.anroid.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.TextView;

import com.example.anroid.DataBase.entities.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DAO extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notesDB";

    private static DAO instance = null;

    private DAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DAO getDAO(Context context){
        if(instance == null){
            instance = new DAO(context);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableUsers = "CREATE TABLE Users(Name TEXT PRIMARY KEY)";
        db.execSQL(tableUsers);

        String tableNotes = "CREATE TABLE Notes(Id INTEGER PRIMARY KEY autoincrement, User TEXT REFERENCES Users(Name), Note TEXT, Image BLOB)";
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

    private void getAllUsers() {
        UserSelector userSelector = new UserSelector(output -> {

        }, this);

        userSelector.execute("User");
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void addNote(String userName, String note, Bitmap image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("User", userName);
        values.put("Note", note);

        if(image != null) {
            byte[] data = getBitmapAsByteArray(image);

            values.put("Image", data);
        }

        db.insert("Notes", null, values);
        db.close();
    }

    public void getAllUserNotes(String userName){
        NoteSelector noteSelector = new NoteSelector(output -> {

        }, this);

        noteSelector.execute(userName);
    }

    public void dropNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Notes", "Id" + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }
}
