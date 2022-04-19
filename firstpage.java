package com.example.flappygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class firstpage extends AppCompatActivity {
    ImageButton imagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        AppHolder.assign(this.getApplicationContext());
        imagebutton=findViewById(R.id.play);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(firstpage.this,GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}