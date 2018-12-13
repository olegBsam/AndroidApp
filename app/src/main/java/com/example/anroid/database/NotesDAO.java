package com.example.anroid.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.anroid.database.entities.Note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NotesDAO {
    String TABLE_NOTES = "Notes";

    String TABLE_NOTES_USER_NAME = "userName";
    String TABLE_NOTES_PK = "Id";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<Note> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM " + TABLE_NOTES + " WHERE " + TABLE_NOTES_USER_NAME + " LIKE :name")
    List<Note> getAllNotesByUserName(String name);

    @Query("DELETE FROM " + TABLE_NOTES + " WHERE " + TABLE_NOTES_PK + " = :id")
    void deleteNoteById(Integer id);

    @Query("DELETE FROM " + TABLE_NOTES + " WHERE " + TABLE_NOTES_USER_NAME + " LIKE :name")
    void deleteAllNotesByUserName(String name);
}