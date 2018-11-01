package com.example.anroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        findViewById(R.id.buttonAcceptRegistration).setOnClickListener((listener)->{
            String login = ((TextView)findViewById(R.id.editTextLoginRegistration)).getText().toString();

            if(login.length() == 0){
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
                return;
            }

            String password = ((TextView)findViewById(R.id.editTextPasswordRegistration)).getText().toString();

            if(password.length() == 0){
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            String repeat = ((TextView)findViewById(R.id.editTextPasswordRepeat)).getText().toString();

            if(repeat.length() == 0){
                Toast.makeText(this, "Повторите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(repeat)) {
                Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                return;
            }

            sendRegistrationData(login, password);
        });

        findViewById(R.id.buttonDeclineRegistration).setOnClickListener((listener)->{
            finish();
        });
    }

    private void sendRegistrationData(String login, String password){
        try {
            GetPost g = new GetPost(output -> {
                switch (output) {
                    case "2": {
                        Toast.makeText(this, "Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show();
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
            map.put("isNew", "1");

            g.execute(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
