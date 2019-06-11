package com.example.di_1_hexentanz.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.Startscreen;

/**
Placeholder class
 */

public class Winnerpop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView winner;
        Intent intetn = getIntent();
        String USERNAME = "username";



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winnerpop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.7),(int)(0.7*height));

        winner = findViewById(R.id.winner);
        winner.setText(intetn.getStringExtra(USERNAME));


    }

    public void onClick(View view){
        Intent i = new Intent(getApplicationContext(), Startscreen.class);
        startActivity(i);

    }




}
