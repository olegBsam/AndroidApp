package com.example.anroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        final Intent intent = new Intent(this, NoteList.class);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = ((TextView)findViewById(R.id.editTextLogin)).getText().toString();
                if(login.length() == 0){
                    return;
                }

                if(!validateLogin(login)){
                    return;
                }

                String password = ((TextView)findViewById(R.id.editTextPassword)).getText().toString();
                if(password.length() == 0){
                    return;
                }

                if(!sendAuthenticationData(login, password)){
                    return;
                }
               // startActivity(intent);

            }
        };
        findViewById(R.id.buttonLogIn).setOnClickListener(onClick);
    }

    protected void onClickButtonInput(){

    }

    private boolean sendAuthenticationData(String login, String password){
        try{
            GetPost.doGet(login + " " + password);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
    private boolean validateLogin(String login){
        return login.matches("\\w+");
    }
}
