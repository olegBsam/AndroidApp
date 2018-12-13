package com.example.anroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        Button acceptButton = (Button)findViewById(R.id.buttonAcceptRegistration);

        acceptButton.setOnClickListener((listener)->{
            String login = ((TextView)findViewById(R.id.editTextLoginRegistration)).getText().toString();

            if(login.length() == 0){
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!MainActivity.validateLogin(login)) {
                Toast.makeText(this, "Неверный формат логина", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);

            finish();
        });
    }

    private void sendRegistrationData(String login, String password){
        try {
            GetPost g = new GetPost(output -> {
                switch (output.replaceAll("\"", "")) {
                    case "2": {
                        Toast.makeText(this, "Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case "4": {
                        Toast.makeText(this, "Пользователь зарегистрирован", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("login", login);
                        intent.putExtra("password", password);
                        setResult(RESULT_OK, intent);
                        finish();
                        break;
                    }
                }
            });
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("login", login);
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("password", password);
            HashMap<String, String> map3 = new HashMap<>();
            map3.put("isNew", "4");

            g.execute(map1, map2, map3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}