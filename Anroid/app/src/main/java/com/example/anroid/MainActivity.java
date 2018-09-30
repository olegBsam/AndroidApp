package com.example.anroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
        final Intent intent = new Intent(this, FullscreenActivity.class);

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);

            }
        };
        findViewById(R.id.buttonLogIn).setOnClickListener(onClick);
    }

    protected void onClickButtonInput(){

    }

}
