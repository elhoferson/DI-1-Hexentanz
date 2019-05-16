package com.example.di_1_hexentanz.Player;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.di_1_hexentanz.R;

class WitchView extends View {
    int x;
    int y;
    int radius;
    Witch witchInstance;
    Paint paint;
    PlayerColor color;
    Bitmap b;

    public WitchView(Context context, int x, int y, int radius, Witch witchInstance) {
        super(context);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.witchInstance = witchInstance;
        paint = new Paint();
        b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_default);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bResize = Bitmap.createScaledBitmap(b, 2*radius,2*radius, false);
        canvas.drawBitmap(bResize, x-radius, y-radius, paint);
    }

    public void moveView(int destx, int desty) {
        ValueAnimator animatorx = ValueAnimator.ofInt(x, destx);
        ValueAnimator animatory = ValueAnimator.ofInt(y, desty);
        animatorx.setDuration(1000);
        animatory.setDuration(1000);

        animatorx.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                x = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        animatory.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animatorx.start();
        animatory.start();
    }

    public void showColor() {
        switch (color) {
            case RED:
                b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_rot);
                invalidate();
                break;
            case BLUE:
                b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_blau);
                invalidate();
                break;
            case PINK:
                b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_lila);
                invalidate();
                break;
            case GREEN:
                b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_gruen);
                invalidate();
                break;
            case ORANGE:
                b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_orange);
                invalidate();
                break;
            case YELLOW:
                b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_gelb);
                invalidate();
                break;
            default:
                throw new IllegalArgumentException("colour doesn't exist");

        }
    }

    public void hideColor() {
        b= BitmapFactory.decodeResource(getResources(), R.drawable.hexe_default);
        invalidate();
    }

    public void setColor(PlayerColor color){
        this.color = color;
    }


    public int getColorFromPlayerColor(PlayerColor playerColor){

        int color;
        switch (playerColor){
            case BLUE:
                color = Color.BLUE;
                break;
            case GREEN:
                color = Color.GREEN;
                break;
            case YELLOW:
                color = Color.YELLOW;
                break;
            case RED:
                color = Color.RED;
                break;
            default:
                color = Color.BLACK;
                break;
        }
        return color;
    }
}
