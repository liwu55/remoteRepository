package com.example.kingdee.something.widget.reflect_ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.kingdee.something.widget.firework.MyBaseSurfaceView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liwu on 2017/12/18.
 * 反射球
 */

public class ReflectSurface extends MyBaseSurfaceView {

    private long lastTime=-1;
    private List<Ball> balls=new ArrayList<>();
    private int ballsCount=20;

    public ReflectSurface(Context context) {
        super(context);
    }

    public ReflectSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawSelf(Canvas canvas) {
        long curTime=System.currentTimeMillis();
        if(lastTime==-1){
            lastTime=curTime-1;
        }
        double rate=(curTime-lastTime)/1000f;
        if(balls.size()==0){
            for (int i = 0; i < ballsCount; i++) {
                balls.add(new Ball(canvas));
            }
        }
        canvas.drawColor(Color.BLACK);
        for (Ball ball:balls){
            ball.draw(canvas,rate);
        }
        lastTime=curTime;
    }
}
