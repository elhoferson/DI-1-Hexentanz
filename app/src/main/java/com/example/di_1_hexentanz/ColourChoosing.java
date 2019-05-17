package com.example.di_1_hexentanz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.di_1_hexentanz.GameBoard.Gamescreen;
import com.example.di_1_hexentanz.Player.PlayerColor;

public class ColourChoosing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colourchoosing);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        Button btnBlue = findViewById(R.id.btnBlue);
        btnBlue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Gamescreen.setColor(PlayerColor.BLUE);
                Intent intent = new Intent(getApplicationContext(), Gamescreen.class);
                startActivity(intent);
            }
        });

        Button btnGreen = findViewById(R.id.btnGreen);
        btnGreen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Gamescreen.setColor(PlayerColor.GREEN);
                Intent intent = new Intent(getApplicationContext(), Gamescreen.class);
                startActivity(intent);
            }
        });

        Button btnYellow = findViewById(R.id.btnYellow);
        btnYellow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Gamescreen.setColor(PlayerColor.YELLOW);
                Intent intent = new Intent(getApplicationContext(), Gamescreen.class);
                startActivity(intent);
            }
        });

        Button btnRed = findViewById(R.id.btnRed);
        btnRed.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Gamescreen.setColor(PlayerColor.RED);
                Intent intent = new Intent(getApplicationContext(), Gamescreen.class);
                startActivity(intent);
            }
        });
    }




}
