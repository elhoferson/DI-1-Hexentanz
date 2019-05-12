package com.example.di_1_hexentanz;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class WitchView extends View {
    int x;
    int y;
    int radius;
    Witch witchInstance;
    Paint paint;
    PlayerColor color;

    public WitchView(Context context, int x, int y, int radius, Witch witchInstance) {
        super(context);
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.witchInstance = witchInstance;
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getColorFromPlayerColor(this.color));
        canvas.drawCircle(x, y, radius, paint);
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
