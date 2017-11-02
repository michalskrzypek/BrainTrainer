package com.michalskrzypek.braintrainer;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    static Button playAgainButton, goBackButton;
    static TextView mathTask, secondsLeft, resultText, outcomePopUp;
    static GridLayout gridLayout;
   static CountDownTimer countDownTimer;
    static long seconds = 30000;
    static int correctAnswers;
    static int wrongAnswers;
    static int totalAnswers;
    static int result , numb1, numb2;
    static double operation;
    static int resultInTag;
    static boolean finished;
static long timeLeft = seconds +500;
    

public void goBack(View view){

    Intent i = new Intent();
    i.putExtra("KeyFromGameActivity", String.valueOf(timeLeft/1000));
    setResult(RESULT_OK, i);
    finish();
}
    public static void startAGame(){
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                secondsLeft.setText(millisUntilFinished/1000 + "s");
            }
            @Override
            public void onFinish() {
                timeLeft-=500;
                secondsLeft.setText(timeLeft/1000 + "s");
                outcomePopUp.setTextColor(Color.DKGRAY);
                outcomePopUp.setText("Your score: "+String.valueOf(correctAnswers)+"/"+String.valueOf(totalAnswers));
                outcomePopUp.animate().alpha(1f).setDuration(1);
                finished = true;
                playAgainButton.setVisibility(View.VISIBLE);
            }
        };
        generateEquality();
        countDownTimer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resultText.setText(String.valueOf(correctAnswers) + "/" + String.valueOf(totalAnswers));
        countDownTimer.start();

    }

    public static void playAgain(View view){
        correctAnswers = 0;
        totalAnswers = 0;
        wrongAnswers = 0;
        resultText.setText(String.valueOf(correctAnswers) + "/" + String.valueOf(totalAnswers));
        outcomePopUp.animate().alpha(0f).setDuration(1);
        finished = false;
        playAgainButton.setVisibility(View.INVISIBLE);
        timeLeft = seconds + 500;
        startAGame();
    }

    public static void showOutcome(final String outcome){
        if(outcome.equalsIgnoreCase("correct!")){
            outcomePopUp.setTextColor(Color.rgb(30,255,30));
        }else if(outcome.equalsIgnoreCase("wrong!")){
            outcomePopUp.setTextColor(Color.rgb(255,10,10));
        }
        outcomePopUp.setText(outcome);
        outcomePopUp.animate().alpha(1f).setDuration(500);
        outcomePopUp.postDelayed(new Runnable() {
            public void run() {
                outcomePopUp.animate().alpha(0f).setDuration(500);
            }
        }, 1000);
    }

    public static void chooseAnswer(View view){
        if(!finished) {
            TextView chosenView = (TextView) view;
            if (Integer.parseInt(chosenView.getText().toString()) == result) {
                correctAnswers++;
                showOutcome("CORRECT!");
            } else {
                wrongAnswers++;
                showOutcome("WRONG!");
            }
            totalAnswers++;
            resultText.setText(String.valueOf(correctAnswers) + "/" + String.valueOf(totalAnswers));
            generateEquality();
        }

    }

    public static void generateEquality(){
        numb1 = (int) (Math.random() * 50);
        numb2 = (int) (Math.random() * 50);

        operation =  Math.random();
        if(operation <0.5) {
            result = numb1 + numb2;
            mathTask.setText(String.valueOf(numb1) + "+" + String.valueOf(numb2));
        }else{
            result = numb1 - numb2;
            mathTask.setText(String.valueOf(numb1) + "-" + String.valueOf(numb2));
        }

        generateNumbers();
    }

    public static void generateNumbers (){

        int incorrectAnswer = 0;
        for(int i =0; i<gridLayout.getChildCount();i++){
            incorrectAnswer = (int) (Math.random()*100);
            TextView num = (TextView) gridLayout.getChildAt(i);

            while(incorrectAnswer == result){
                incorrectAnswer = (int) (Math.random()*100);
            }
            num.setText(String.valueOf(incorrectAnswer));
        }

        Random rand = new Random();
        int resultInTag = rand.nextInt(4);
        TextView correctView = (TextView) gridLayout.getChildAt(resultInTag);
        correctView.setText(String.valueOf(result));

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        mathTask = (TextView) findViewById(R.id.mathTaskView);
        resultText = (TextView) findViewById(R.id.resultText);
        secondsLeft = (TextView) findViewById(R.id.secondsText);
        outcomePopUp = (TextView) findViewById(R.id.outcomeView);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        goBackButton = (Button) findViewById(R.id.goBackButton);
        totalAnswers = correctAnswers + wrongAnswers;

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String dataReceived = extras.getString("KeyFromMainActivity");
            if(dataReceived !=null){
                Toast.makeText(this, dataReceived, Toast.LENGTH_SHORT).show();
            }
        }
        startAGame();
    }


}
