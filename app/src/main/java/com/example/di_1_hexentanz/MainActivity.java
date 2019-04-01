package com.example.di_1_hexentanz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btn_dice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_dice = findViewById(R.id.btn_dice);
        btn_dice.setOnClickListener(new View.OnClickListener() {
            @
                    Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DiceActivity.class);
                startActivity(i);
            }
        });



    }


}