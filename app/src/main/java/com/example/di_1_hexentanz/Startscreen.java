package com.example.di_1_hexentanz;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Startscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //ImageView BtnJoinGame = findViewById(R.id.Btn_JoinGame);
        //BtnJoinGame.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Toast.makeText(getApplicationContext(), "Join Game Button", Toast.LENGTH_SHORT).show();
        //    }
        //});

        //ImageView BtnCreateGame = findViewById(R.id.Btn_CreateGame);
        //BtnCreateGame.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(getApplicationContext(), ColourChoosing.class);
        //        startActivity(intent);
        //    }
        //});
    }

    public void onClick(View v) {

        if (!activeWifi()) {
            Toast.makeText(this, "Wifi not active!", Toast.LENGTH_SHORT).show();
            return;
        }

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

}
