package com.example.di_1_hexentanz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WalkingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
    }

    public static int walkFields(int numberOfFields) {

        System.out.printf("gehe %d felder", numberOfFields);
        return numberOfFields;

    }
}
