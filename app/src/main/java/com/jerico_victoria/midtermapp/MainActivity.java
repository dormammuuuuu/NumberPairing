package com.jerico_victoria.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    EditText username;
    ImageButton next;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText)findViewById(R.id.UserName);
        next = (ImageButton)findViewById(R.id.NextButton);
        getSupportActionBar().hide();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                user = username.getText().toString();
                Intent i = new Intent(MainActivity.this, Game.class);
                i.putExtra("user", user);
                startActivity(i);
                finish();
            }
        });
    }
}