package com.example.di_1_hexentanz.player;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.di_1_hexentanz.R;
import com.example.di_1_hexentanz.Startscreen;
import com.example.di_1_hexentanz.network.logic.std.NetworkLogic;
import com.example.di_1_hexentanz.network.logic.std.WifiP2pLogic;

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

        PlayerColor color = (PlayerColor) getIntent().getSerializableExtra(USERNAME);
        winner = findViewById(R.id.winner);
        winner.setText(color.name());


    }

    public void onClick(View view){
        WifiP2pLogic.instance().disconnect();
        NetworkLogic.getInstance().close();

        Intent i = new Intent(getApplicationContext(), Startscreen.class);
        startActivity(i);

    }




}
