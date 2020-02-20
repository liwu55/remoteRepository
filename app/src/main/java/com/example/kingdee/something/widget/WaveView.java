package com.example.kingdee.something.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by kingdee on 2016/4/28.
 */
public class WaveView extends View{
    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();
        int clear=20;
        Log.d("WaveView","width="+width);
        Log.d("WaveView", "height=" + height);
        Paint paint=new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);
        for (int i = 0; i < width; i+=clear) {
            for (int j = 0; j < height; j+=clear) {
                paint.setColor(Color.argb(new Random().nextInt(255),
                        new Random().nextInt(255),
                        new Random().nextInt(255),
                        new Random().nextInt(255)));
                canvas.drawRect(i, j, i + clear, j + clear, paint);
            }
        }
    }
}
