package com.jerico_victoria.midtermapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.widget.Toast;
import android.os.Handler;

public class Game extends AppCompatActivity {
    TextView name, timer, index;
    String value;
    Toast toast;
    ProgressBar timerbar;
    private String[] id;
    private TextView[] box = new TextView[49];
    int j, temp;
    int num1 = -1, num2 = 1;
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

        name.setText(value);
        AttachNumbersToBox();
        StartTimer();
    }

    //100s timer function
    private void StartTimer() {
        new CountDownTimer(100000, 1000) {

            public void onTick(long millisUntilFinished) {
                timerbar.setProgress((int) (millisUntilFinished / 1000)); //Progressbar
                timer.setText("Time Remaining: " + millisUntilFinished / 1000); //Text timer
            }

            public void onFinish() {
                timer.setText("Time's Up!");
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

        //Shuff 1st set and 2nd set (para by pair sila)
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
            disabledBoxes.add(num1);
            disabledBoxes.add(num2);
        } else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    box[num1].setTextColor(Color.TRANSPARENT);
                    box[num2].setTextColor(Color.TRANSPARENT);
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