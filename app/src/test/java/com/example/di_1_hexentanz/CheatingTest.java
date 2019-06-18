package com.example.di_1_hexentanz;

import android.content.Context;

import com.example.di_1_hexentanz.gameboard.Gamescreen;
import com.example.di_1_hexentanz.gameboard.LumiSensor;


import org.junit.Before;
import org.junit.Test;

public class CheatingTest {
    private Gamescreen gamescreen;
    private LumiSensor lumiSensor;
    Context context;

    @Before
    public void setUp() {
        gamescreen = new Gamescreen();
        context = gamescreen.getApplicationContext();
        lumiSensor = new LumiSensor(context, gamescreen);
    }

}
