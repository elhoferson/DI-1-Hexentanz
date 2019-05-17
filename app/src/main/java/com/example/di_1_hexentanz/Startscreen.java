package com.example.di_1_hexentanz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Startscreen extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        ImageView BtnJoinGame = findViewById(R.id.Btn_JoinGame);
        BtnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Join Game IButton", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView BtnCreateGame = findViewById(R.id.Btn_CreateGame);
        BtnCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                Intent intent = new Intent(getApplicationContext(), ColourChoosing.class);
                startActivity(intent);
            }
        });


        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bgsound);


        final ImageView BtnSound = findViewById(R.id.sound);
        BtnSound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    BtnSound.setImageResource(R.drawable.sound_on);
                }
                else {
                    mediaPlayer.pause();
                    BtnSound.setImageResource(R.drawable.sound_off);
                }

            }
        });


        mediaPlayer.start();
    }



    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
