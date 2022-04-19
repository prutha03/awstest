package com.example.flappygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    ImageButton imagebutton;
    ImageButton imagebutton1;
    TextView tScore,tBest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        imagebutton=findViewById(R.id.restartbtn);
        imagebutton1=findViewById(R.id.quitbtn);
        int scoreCount = getIntent().getExtras().getInt("score");
        SharedPreferences pref =getSharedPreferences("myStoragePreference",0);
        int scoreBest = pref.getInt("scoreBest",0);
        SharedPreferences.Editor edit = pref.edit();
        if(scoreCount>scoreBest){
            scoreBest = scoreCount;
            edit.putInt("scoreBest",scoreBest);
            edit.apply();
        }



        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(GameOver.this,firstpage.class);
                startActivity(intent);
                finish();
            }
        });
        imagebutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            });
        tScore = findViewById((R.id.score_Display));
        tBest= findViewById((R.id.Best_Display));
        tScore.setText(""+scoreCount);
        tBest.setText(""+scoreBest);
    }
}