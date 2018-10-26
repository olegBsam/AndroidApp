package com.example.anroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, NotesActivity.class);
        intent.putExtra("Name", "Oleg");
        startActivity(intent);

        View.OnClickListener onClick = v -> {
            Intent intent1 = new Intent(MainActivity.this, NotesActivity.class);
            intent1.putExtra("Name", "Oleg");
            startActivity(intent1);

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

        };
        findViewById(R.id.buttonLogIn).setOnClickListener(onClick);
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
