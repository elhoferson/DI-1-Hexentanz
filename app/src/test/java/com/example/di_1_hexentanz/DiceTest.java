package com.example.di_1_hexentanz;

import android.app.AlertDialog;
import android.app.Instrumentation;
import android.widget.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowView;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.robolectric.shadows.ShadowInstrumentation.getInstrumentation;


public class DiceTest {

    Dice testDice;
    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6);


    @Before
    public void setTestDice() {
        testDice = new Dice();
    }


    @Test
    public void testCorrectRandomNumberAlgorithm() {
        for (int i = 0; i <= 1000000; i++) {
            if (range.contains(testDice.getRandomNumber())) {
                //System.out.println("OK");
                break;


            }
            throw new IllegalArgumentException("Dice number range is only from 1 to 6");
        }

    }


    @Test
    public void clickingPositiveButtonDismissesDialog() throws Exception {
        AlertDialog alertDialog = new AlertDialog.Builder(testDice)
                .setPositiveButton("Positive", null).create();
        alertDialog.show();

        assertTrue(alertDialog.isShowing());
        ShadowView.clickOn(alertDialog.getButton(AlertDialog.BUTTON_POSITIVE));
        assertFalse(alertDialog.isShowing());

    }

}


