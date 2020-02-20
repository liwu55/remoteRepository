package com.example.kingdee.something.widget.firework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.style.ScaleXSpan;

import com.example.kingdee.something.util.ToggleLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by liwu on 2017/11/27.
 * 烟花碎片
 */

public class FireWorkFragment {

    public static final int TYPE_CIRCLE=0;
    public static final int TYPE_GROUND=1;


    private static Random random = new Random();
    private static Paint paint = new Paint();
    private final int xMax;
    private final int yMax;
    private final float touchX;
    private final float touchY;
    private double directionOri=-1;
    private boolean hasTail;
    private int ySpeedReduce;
    private int color;
    private int red;
    private int green;
    private int blue;
    private double x;
    private double y;
    private double speed;
    private double direction;
    private float speedReducePerS;
    private double cosValue;
    private double sinValue;
    private int radius;
    private int type;

    //尾巴
//    private List<FireWorkFragmentTail> tail=new ArrayList();
    private List<FireWorkFragmentTailSmaller> tail=new ArrayList();
    private double lastX;
    private double lastY;

    public FireWorkFragment(double x, double y,Canvas c) {
        this(x,y,-1,-1,c,TYPE_CIRCLE);
    }

    public FireWorkFragment(double x, double y,Canvas c,int type) {
        this(x,y,-1,-1,c,type);
    }

    public FireWorkFragment(double x, double y,float touchX,float touchY,Canvas c,int type) {
        this.x = x;
        this.y = y;
        this.type=type;
        this.xMax=c.getWidth();
        this.yMax=c.getHeight();
        this.touchX=touchX;
        this.touchY=touchY;
        if(type==TYPE_GROUND){
            speed = 4000 + random.nextInt(1000);
            if(touchX==-1&&touchY==-1) {
                direction = 80 + random.nextInt(20);
            }else{
                direction = Math.toDegrees(Math.atan((y-touchY)/(touchX-x)));
                adjustAngle();
            }
            directionOri=direction;
            ySpeedReduce=7000;
            radius=5;
            hasTail=true;

        }else if(type==TYPE_CIRCLE){
            speed = 250 + random.nextInt(250);
            speedReducePerS = 300;
            direction = random.nextInt(360);
            radius=6;
            hasTail=true;
            cosValue = Math.cos(Math.toRadians(direction));
            sinValue = Math.sin(Math.toRadians(direction));
        }
        int alpha = 0xff;
        red = random.nextInt(255);
        green = random.nextInt(255);
        blue = random.nextInt(255);
        color = Color.argb(alpha, red, green, blue);
    }

    public void draw(Canvas c) {
        paint.setColor(color);
        c.drawCircle((float)x,(float)y,radius,paint);
        if(hasTail){
            drawTail(c);
        }
    }

    private void drawTail(Canvas c) {
        for (FireWorkFragmentTailSmaller t:tail){
            t.draw(c);
        }
    }

    public void calc(float rate) {
        if(hasTail) {
            lastX = x;
            lastY = y;
        }
        if(type==TYPE_CIRCLE) {
            double flyingDistance = rate * speed;
            double xPlus = cosValue * flyingDistance;
            double yReduce = sinValue * flyingDistance;
            x += xPlus;
            y -= yReduce;
            speed -= speedReducePerS*rate;
        }else{
            cosValue = Math.cos(Math.toRadians(direction));
            sinValue = Math.sin(Math.toRadians(direction));
            double flyingDistance = rate * speed;
            double xPlus = cosValue * flyingDistance;
            double yReduce = sinValue * flyingDistance;
            x += xPlus;
            y -= yReduce;

            double xSpeed=speed*cosValue;
            double ySpeed=speed*sinValue;
            ySpeed-=ySpeedReduce*rate;
            direction=Math.toDegrees(Math.atan(ySpeed/xSpeed));
            adjustAngle();
            speed=Math.sqrt(xSpeed* xSpeed+ySpeed*ySpeed);
        }
        if(hasTail) {
            cutTail();
            //尾巴
            /*Path path = new Path();
            path.moveTo((float) (lastX + radius * sinValue), (float) (lastY + radius * cosValue));
            path.lineTo((float) (x + radius * sinValue), (float) (y + radius * cosValue));
            path.lineTo((float) (x - radius * sinValue), (float) (y - radius * cosValue));
            path.lineTo((float) (lastX - radius * sinValue), (float) (lastY - radius * cosValue));
            tail.add(new FireWorkFragmentTail(path, red, green, blue));*/
            tail.add(new FireWorkFragmentTailSmaller(radius,lastX,x,lastY,y,sinValue,cosValue, red, green, blue));
        }

    }

    private void adjustAngle() {
        if(directionOri==-1){
            if(touchX!=-1&&touchY!=-1){
                if(touchX<x){
                    direction+=180;
                }
            }
            direction+=(random.nextInt(10)-5);
        }else {
            if (directionOri > 90 && direction < 90) {
                direction += 180;
            }
        }
    }

    private void cutTail() {
        Iterator<FireWorkFragmentTailSmaller> iterator = tail.iterator();
        while(iterator.hasNext()){
            FireWorkFragmentTailSmaller next = iterator.next();
            if(next.isEnd()){
                iterator.remove();
            }
        }
    }

    public boolean isFlying() {
        if(type==TYPE_CIRCLE) {
            return speed > 0;
        }else{
            return !(x<-500||y<-2000||x>xMax+500||y>yMax+2000);
        }
    }
}
