package com.jerico_victoria.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class GameOver extends AppCompatActivity {
    Button restart, exit;
    TextView scorelabel;
    int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        restart = (Button)findViewById(R.id.Restart);
        exit = (Button)findViewById(R.id.Exit);
        scorelabel = (TextView) findViewById(R.id.scorelabel);
        getSupportActionBar().hide();

        //Get The Passed Data (Username) from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            score = extras.getInt("score"); //Get score
            if (score == 0) { //Set the value to "Player" kung walang username na nilagay
                score = 0;
            }
        }

        scorelabel.setText("Final Score: " + String.valueOf(score));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
                System.exit(0);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });
    }
}