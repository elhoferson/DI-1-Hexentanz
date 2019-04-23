package com.example.di_1_hexentanz;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class WitchView extends View {
    int x,y;
    Witch witchInstance;
    Paint paint;

    public WitchView(Context context, int x, int y, Witch witchInstance) {
        super(context);
        this.x = x;
        this.y = y;
        this.witchInstance = witchInstance;
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int radius = 40;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GRAY);
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
    }
}
