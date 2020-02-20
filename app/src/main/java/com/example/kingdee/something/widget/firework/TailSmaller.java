package com.example.kingdee.something.widget.firework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by liwu on 2017/11/27.
 *  主体尾巴
 */

public class TailSmaller {

    private Paint paint;
    private Path path;
    private int f;
    private int F_MAX=10;

    private final double lastX;
    private final double x;
    private final double lastY;
    private final double y;
    private final double sinValue;
    private final double cosValue;
    private int radius;
    private final int fullRadius;

    public TailSmaller(int radius, double lastX, double x, double lastY, double y, double sinValue, double cosValue) {
        this.fullRadius=radius;
        this.radius=radius;
        this.lastX=lastX;
        this.x=x;
        this.lastY=lastY;
        this.y=y;
        this.sinValue=sinValue;
        this.cosValue=cosValue;

        f=F_MAX;
        paint=new Paint();
    }

    public void draw(Canvas c){
        int a= (int) (0xff*(f/(float)F_MAX)+0.5);
        int color= Color.argb(a,0xff,0xff,0xff);
        paint.setColor(color);

        radius=(int) (fullRadius*(f/(float)F_MAX)+0.5);
        path=new Path();
        path.moveTo((float) (lastX + radius * sinValue), (float) (lastY + radius * cosValue));
        path.lineTo((float) (x + radius * sinValue), (float) (y + radius * cosValue));
        path.lineTo((float) (x - radius * sinValue), (float) (y - radius * cosValue));
        path.lineTo((float) (lastX - radius * sinValue), (float) (lastY - radius * cosValue));

        c.drawPath(path,paint);
        f--;
    }

    public boolean isEnd(){
        return f<=0;
    }
}
