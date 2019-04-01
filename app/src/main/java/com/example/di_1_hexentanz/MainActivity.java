package com.example.di_1_hexentanz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException npe){
            npe.printStackTrace();
        }
        setContentView(R.layout.activity_main);
    }
}
