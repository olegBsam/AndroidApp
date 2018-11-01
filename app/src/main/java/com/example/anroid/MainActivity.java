package com.example.anroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = new Intent(MainActivity.this, NotesActivity.class);
        intent.putExtra("Name", "Oleg");
        startActivity(intent);*/

        findViewById(R.id.buttonLogIn).setOnClickListener((listener) -> {
            /*Intent intent1 = new Intent(MainActivity.this, NotesActivity.class);
            intent1.putExtra("Name", "Oleg");
            startActivity(intent1);*/

            String login = ((TextView) findViewById(R.id.editTextLogin)).getText().toString();
            if (login.length() == 0) {
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!MainActivity.validateLogin(login)) {
                Toast.makeText(this, "Неверный формат логина", Toast.LENGTH_SHORT).show();
                return;
            }

            String password = ((TextView) findViewById(R.id.editTextPassword)).getText().toString();
            if (password.length() == 0) {
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            sendAuthenticationData(login, password);

        });

        findViewById(R.id.buttonRegistration).setOnClickListener((listener) -> {
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
        });
    }

    private void sendAuthenticationData(String login, String password) {
        try {
            GetPost g = new GetPost(output -> {
                switch (output) {
                    case "1": {
                        Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                        intent.putExtra("Name", login);
                        startActivity(intent);
                        break;
                    }
                    case "3": {
                        Toast.makeText(this, "Ошибка логина или пароля", Toast.LENGTH_SHORT).show();
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
            e.printStackTrace();
        }
    }

    public static boolean validateLogin(String login) {
        return login.matches("\\w+");
    }
}
