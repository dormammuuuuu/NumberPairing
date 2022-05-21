package com.jerico_victoria.midtermapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.Button;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.widget.Toast;
import android.os.Handler;

public class Game extends AppCompatActivity {
    TextView name, timer, score;
    String value;
    Toast toast;
    ProgressBar timerbar;
    Button pause, resume, restart;
    RelativeLayout pause_overlay;
    private String[] id;
    private TextView[] box = new TextView[49];
    int j, temp, num1 = -1, num2 = 1, multiplier = 1, userscore, counter;
    private CountDownTimer time;
    long timeLong = 100000;
    List<Integer> solution = new ArrayList<>();
    List<Integer> disabledBoxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        name = (TextView)findViewById(R.id.PlayerName);
        timerbar = (ProgressBar)findViewById(R.id.timerProgress);
        timer = (TextView)findViewById(R.id.Timer);
        score = (TextView)findViewById(R.id.Score);
        pause = (Button)findViewById(R.id.PauseButton);
        resume = (Button)findViewById(R.id.ResumeButton);
        restart = (Button)findViewById(R.id.RestartButton);
        pause_overlay = (RelativeLayout)findViewById(R.id.pause_overlay);

        timerbar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#6200EE")));

        CreateArraySet(); //Generate 48 numbers (function below)

        //Bind ID's to TextView
        id = new String[]{"box1", "box2", "box3", "box4", "box5", "box6", "box7", "box8", "box9", "box10", "box11", "box12", "box13", "box14", "box15", "box16", "box17","box18", "box19", "box20", "box21", "box22", "box23", "box24", "box25", "box26", "box27", "box28", "box29", "box30", "box31", "box32", "box33", "box34", "box35", "box36", "box37", "box38", "box39", "box40", "box41", "box42", "box43", "box44", "box45", "box46", "box47", "box48", "box49"};
        for(int i=0; i<id.length; i++){
            temp = getResources().getIdentifier(id[i], "id", getPackageName());
            box[i] = (TextView)findViewById(temp); //Binabind na yung textviews sa part na to.
        }

        //Get The Passed Data (Username) from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("user"); //Set
        }

        if (value.equals("")) { //Set the value to "Player" kung walang username na nilagay
            value = "Player";
        }

        //Pause the timer and game
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                time.cancel();
                timer.setText("Game Paused"); //Text timer
                pause.setEnabled(false);
                restart.setEnabled(false);
                pause_overlay.setVisibility(View.VISIBLE);
                disableButton(); //Disable buttons habang nakapause
            }
        });

        //Resume the timer and game
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                StartTimer();
                pause.setEnabled(true);
                restart.setEnabled(true);
                enableButton(); //Enable ALL buttons
                disabledBoxes(); // Disable yung mga TAPOS NA para maiwasan ang bug.
                pause_overlay.setVisibility(View.GONE);
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                time.cancel();
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        name.setText(value);
        AttachNumbersToBox();
        StartTimer();
    }

    //100s timer function
    private void StartTimer() {
        time = new CountDownTimer(timeLong, 1000) {

            public void onTick(long millisUntilFinished) {
                timerbar.setProgress((int) (millisUntilFinished / 1000)); //Progressbar
                timer.setText("Time Remaining: " + millisUntilFinished / 1000); //Text timer
                timeLong = millisUntilFinished;
            }

            public void onFinish() {
                timer.setText("Time's Up!");
                Intent i = new Intent(Game.this, GameOver.class);
                i.putExtra("score", userscore);
                startActivity(i);
                finish();
            }
        }.start();
    }

    //Generate 48 numbers then shuffle it.
    private void CreateArraySet() {
        //1st set 1-24
        for (int i = 1; i <= 24; i++) {
            solution.add(i);
        }
        //2nd set 1-24
        for (int i = 1; i <= 24; i++) {
            solution.add(i);
        }

        //Shuffle 1st set and 2nd set (para by pair sila)
        Collections.shuffle(solution);
    }

    //Attach Numbers To Boxes then set text color to transparent.
    private void AttachNumbersToBox() {
        for (int i = 0; i <= 48; i++){
            if(i == 24){
                box[i].setText("B");
            } else {
                box[i].setText(solution.get(j).toString());
                box[i].setTextColor(Color.TRANSPARENT);
                j++;
                int finalI = i;

                box[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        box[finalI].setTextColor(Color.WHITE);
                        if (num1 == -1){
                            num1 = finalI;
                            box[finalI].setEnabled(false);
                        } else {
                            num2 = finalI;
                            disableButton();    //Disable ALL buttons para di muna makaclick or maiwasan yung double click bug
                            checker(num1, num2);
                            num1 = num2 = -1;
                            enableButton(); //Enable ALL buttons para makaselect ulit.
                            disabledBoxes(); //Disable DONE boxes (yung mga tapos na maselect dahil yung taas na function nito ay inenable lahat kaya need idisable ulit)
                        }
                    }
                });

            }
        }
    }

    //Check number kung same, change bg color pero kung hindi, balik sa transpa textColor
    private void checker(int num1, int num2) {
        String first = box[num1].getText().toString();
        String second = box[num2].getText().toString();
        if (first.equals(second)){
            box[num1].setBackgroundResource(R.color.purple_500);
            box[num2].setBackgroundResource(R.color.purple_500);
            userscore += 2 * multiplier;
            counter++;
            score.setText("Score: " + userscore);
            disabledBoxes.add(num1);
            disabledBoxes.add(num2);
            time.cancel(); //Stop the time
            timeLong += 5000; // Add 5 seconds
            StartTimer(); //Continue timer
            multiplier += 1; //add 1 to multiplier;

            //Checker. If completed na lahat then open next activity.
            if (counter == 24){
                Intent i = new Intent(Game.this, GameOver.class);
                i.putExtra("score", userscore);
                startActivity(i);
                finish();
            }

        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    box[num1].setTextColor(Color.TRANSPARENT);
                    box[num2].setTextColor(Color.TRANSPARENT);
                    multiplier = 1; //set multiplier to 1;
                }
            }, 500);
        }
    }

    //Disable all textview na parang button
    public void disableButton(){
        for (int i = 0; i <= 48; i++){
            if(i == 24){
                continue;
            } else {
                box[i].setEnabled(false);
                j++;
            }
        }
    }
    
    public void disabledBoxes(){
        for (int data: disabledBoxes) {
            box[data].setEnabled(false);
        }
    }

    //Enable all textview na parang button
    public void enableButton(){
        for (int i = 0; i <= 48; i++){
            if(i == 24){
                continue;
            } else {
                box[i].setEnabled(true);
                j++;
            }
        }
    }

}