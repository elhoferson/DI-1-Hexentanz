package com.example.di_1_hexentanz;

import android.content.Context;

import com.example.di_1_hexentanz.gameboard.Feld;
import com.example.di_1_hexentanz.gameboard.FeldView;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FeldTest {

    private int number;
    private int x;
    private int y;
    private int radius;
    private FeldView testFeldView;
    private Context context;
    private Feld testfeld;

    @Before
    public void setTestFeld() {
        testfeld = new Feld(number,x, y, radius, context);
    }

    @Before
    public void setTestFeldView() {
        testFeldView = new FeldView(context, x, y, radius, testfeld, number);
    }

    @Test
    public void testGetX() {
        assertEquals(testfeld.getX(), x);
    }

    @Test
    public void testGetY() {
        assertEquals(testfeld.getY(), y);
    }

    @Test
    public void testGetNumber() {
        assertEquals(testfeld.getNumber(), number);
    }

}
