package com.example.anroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.anroid.DataBase.DAO;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*final EditText loginField = findViewById(R.id.editTextLogin);
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginField.setText("Ура");
            }
        };*/

        final DAO db = DAO.getDAO(this);
        //this.deleteDatabase("notesDB");

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = ((TextView)findViewById(R.id.editTextLogin)).getText().toString();
                //db.addNote("Oleg", text, null);

                //((TextView)findViewById(R.id.editTextLogin)).setText(db.getAllUserNotes(0).get(0));

                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                intent.putExtra("Name", "Oleg");
                startActivity(intent);

                String login = ((TextView) findViewById(R.id.editTextLogin)).getText().toString();
                if (login.length() == 0) {
                    return;
                }

                if (!validateLogin(login)) {
                    return;
                }

                String password = ((TextView) findViewById(R.id.editTextPassword)).getText().toString();
                if (password.length() == 0) {
                    return;
                }

                if (!sendAuthenticationData(login, password)) {
                    return;
                }
                // startActivity(intent);

            }
        };
        findViewById(R.id.buttonLogIn).setOnClickListener(onClick);
    }

    protected void onClickButtonInput() {

    }

    private boolean sendAuthenticationData(String login, String password) {
        try {
            GetPost g = new GetPost(output -> {
                switch (output) {
                    case "1": {
                        //TODO enter
                        break;
                    }
                    case "3": {
                        //TODO INVALID ENTER
                        break;
                    }
                }
            });
            HashMap<String, String> map = new HashMap<>();
            map.put("login", login);
            map.put("password", password);
            map.put("isNew", "1");

            g.execute(map);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean sendRegistrationData(String login, String password){
        try {
            GetPost g = new GetPost(output -> {
                switch (output) {
                    case "2": {
                        //TODO exists
                        break;
                    }
                    case "4": {
                        //TODO OK
                        break;
                    }
                }
            });
            HashMap<String, String> map = new HashMap<>();
            map.put("login", login);
            map.put("password", password);
            map.put("isNew", "0");

            g.execute(map);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean validateLogin(String login) {
        return login.matches("\\w+");
    }
}
