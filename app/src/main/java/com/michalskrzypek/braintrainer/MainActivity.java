package com.michalskrzypek.braintrainer;

import android.app.Instrumentation;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
Button startButton = null;
    TextView titleText = null;
    TextView timeLeftView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleText = (TextView) findViewById(R.id.titleView);
        startButton = (Button) findViewById(R.id.startButton);
        timeLeftView = (TextView) findViewById(R.id.timeLeftView);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), GameActivity.class);
                i.putExtra("KeyFromMainActivity", "Game has started!");
                startActivityForResult(i, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK){
            if (data.hasExtra("KeyFromGameActivity")){
                String s = data.getExtras().getString("KeyFromGameActivity");
                timeLeftView.setText(s+"s");
            }
        }
    }
}
