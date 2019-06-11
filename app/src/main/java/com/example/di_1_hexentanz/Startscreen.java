package com.example.di_1_hexentanz;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Startscreen extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bgsound);


        final ImageView BtnSound = findViewById(R.id.btn_sound);
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

    public void onClick(View v) {

        if (!activeWifi()) {
            Toast.makeText(this, "Wifi not active!", Toast.LENGTH_SHORT).show();
            return;
        }
        mediaPlayer.stop();

        switch (v.getId()) {
            case R.id.Btn_CreateGame:

                startChild(CreateGameActivity.class);
                break;
            case R.id.Btn_JoinGame:
                startChild(JoinGameActivity.class);
                break;
            default:
                // nothing to do
        }
    }

    private Boolean activeWifi() {
        Boolean active = false;
        WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            //wifi is enabled
            active = true;
        }
        return active;
    }



    private void startChild(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        mediaPlayer.start();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        mediaPlayer.start();
    }
}
