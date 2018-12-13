package com.example.anroid;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.example.anroid.database.AppDatabase;
import com.example.anroid.database.DAO;
import com.example.anroid.database.entities.Note;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class androidTestMyFlavor {
    private static String dataBaseName = "testDataBase";
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.anroid", appContext.getPackageName());
    }
    @Test
    public void testBdInsert(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        Note newNote = new Note("testtest", "user2");
        newNote.setId(999);
        AppDatabase.dataBaseName = dataBaseName;
        DAO.insertNote(newNote, appContext);
        DAO.getAllUserNotes(appContext,"user2", output -> {
            boolean flag = false;
            for(Note note : output) {
                if (note.getId() == newNote.getId()){
                    flag = true;
                    break;
                }
            }
            assertEquals(true, flag);
            DAO.dropNoteById(999, appContext);
        });
    }

    @Test
    public void testBdDelete(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        Note newNote = new Note("testtest", "user2");
        newNote.setId(999);

        AppDatabase.dataBaseName = dataBaseName;

        DAO.insertNote(newNote, appContext);

        DAO.dropNoteById(999, appContext);

        DAO.getAllUserNotes(appContext,"user2", output -> {
            boolean flag = false;
            for(Note note : output) {
                if (note.getId() == newNote.getId()){
                    flag = true;
                    break;
                }
            }
            assertEquals(false, flag);
        });
    }

    @Test
    public void authotizationTest(){
        String login = "user2";
        String password = "12345";

        GetPost g = new GetPost(output -> {
            assertEquals("1", output);
        });
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("IsNew", "0");
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("Login", login);
        HashMap<String, String> map3 = new HashMap<>();
        map3.put("Password", password);

        g.execute(map1, map2, map3);
    }
}