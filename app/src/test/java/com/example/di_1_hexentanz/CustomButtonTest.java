package com.example.di_1_hexentanz;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import com.example.di_1_hexentanz.gameboard.buttons.CustomButton;
import com.example.di_1_hexentanz.gameboard.buttons.IButton;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomButtonTest {

    private CustomButton testButton;
    private Context context;
    private DisplayMetrics metrics;
    private int width;
    private int height;
    private int leftPosition;
    private int topPosition;
    private IButton.BtnType typeBtn;

    @Before
    public void setTestButton() {
        testButton = new CustomButton(context, metrics, typeBtn);
    }

    @Test
    public void testBitMapWidth() {
        assertEquals(testButton.getBitmapWidth(), width);
    }

    @Test
    public void testBitMapHeight() {
        assertEquals(testButton.getBitMapHeight(), height);
    }

    @Test
    public void testLeftPos() {
        assertEquals(testButton.getLeftPosition(), leftPosition);
    }

    @Test
    public void testTopPos() {
        assertEquals(testButton.getTopPosition(), topPosition);
    }

    @Test
    public void testTypeBtn() {
        testButton.setTypeBtn(typeBtn);
        assertEquals(testButton.getTypeBtn(), typeBtn);
    }

    /*
    @Test
    public void testgetBitmap(IButton.BtnType typeBtn) {
        testButton.setTypeBtn(typeBtn);
        CustomButton mockButton = mock(CustomButton.class);
        when(mockButton.getTypeBtn() == IButton.BtnType.YOUR_TURN_BTN);
    }
    */

}
