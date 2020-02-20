package com.example.kingdee.something.widget.meteor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

public class Spark {

    private int color;
    private float pX,pY;
    private int radius;
    private int f;
    private int F_MAX=20;
    private Paint paint=new Paint();
    private Random random=new Random();

    public Spark(float pX, float pY,int radius,int color) {
        this.pX=pX;
        this.pY=pY;
        this.radius=radius;
        F_MAX+=(random.nextInt(9)-4);
        f=F_MAX;
        this.color=color;
    }

    public boolean isDie(){
        return f<=0;
    }

    public void draw(Canvas canvas) {
        int a= (int) (0xff*(f/(float)F_MAX)+0.5);
        int colorWithAlphaByF= Color.argb(a,Color.red(color),Color.green(color),Color.blue(color));
        paint.setColor(colorWithAlphaByF);
        canvas.drawCircle(pX,pY,radius,paint);
        f--;
    }
}
