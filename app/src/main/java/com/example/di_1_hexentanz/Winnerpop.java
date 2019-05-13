package com.example.di_1_hexentanz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

public class Winnerpop extends AppCompatActivity {

    DetermineWinner2 won;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winnerpop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.7),(int)(0.7*height));
    }

    public void onClick(View view){
        Intent i = new Intent(getApplicationContext(),Startscreen.class);
        startActivity(i);

    }




}
