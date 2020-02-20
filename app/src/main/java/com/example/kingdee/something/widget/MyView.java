package com.example.kingdee.something.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liwu on 2016/9/13.
 * wdsfa
 */
public class MyView extends View {
    private float x,y;
    private int radius=30;
    private Paint p;
    private Bitmap b;
    private int xP=0,yP=radius;
    private int d=10;
    private boolean xIncrease;

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        p.setColor(Color.RED);
        b = Bitmap.createBitmap( (2 * radius), (2 * radius), Bitmap.Config.ARGB_8888);
    }

    @Override
    public void draw(Canvas canvas) {
        int width = b.getWidth();
        int height = b.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean con1 = (i - radius) * (i - radius) + (j - radius) * (j - radius) < radius * radius;
                boolean con2=(i - radius) * (i - radius) + (j - radius) * (j - radius)>(radius-10)*(radius-10);

                boolean con3 = (i-xP) * (i-xP) + (j - yP) * (j - yP) < radius * radius;
                boolean con4=(i -xP) * (i-xP) + (j - yP) * (j - yP)>(radius-10)*(radius-10);

                boolean circle1= con1&&con2&&con4;
                boolean circle2= con3&&con4&&con1;
                this.b.setPixel(i, j, circle1||circle2 ? Color.BLACK : Color.WHITE);
            }
        }
        canvas.drawBitmap(b, x - radius, y - radius, p);
        super.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(xP==2*radius){
                    xIncrease=false;
                }
                if(xP==0){
                    xIncrease=true;
                }
                if(xIncrease){
                    xP+=d;
                }else{
                    xP-=d;
                }
                calcXCYC();
                x=event.getX();
                y=event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    private void calcXCYC() {
        yP= (int) (Math.sqrt(radius*radius-(xP - radius) * (xP - radius))+radius+0.5);
    }
}
