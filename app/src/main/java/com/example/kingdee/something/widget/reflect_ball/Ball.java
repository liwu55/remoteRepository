package com.example.kingdee.something.widget.reflect_ball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.kingdee.something.widget.firework.FireWorkFragmentTail;
import com.example.kingdee.something.widget.firework.FireWorkFragmentTailSmaller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by liwu on 2017/12/18.
 * 球
 */

public class Ball {
    private final Paint paint;
    private Random random;
    float x, y, direction;
    private int width;
    private int height;
    private double speed;
    private float radius;

    private double lastX = -1;
    private double lastY = -1;
    private List<FireWorkFragmentTailSmaller> tail = new ArrayList<>();
    private final int red;
    private final int green;
    private final int blue;

    public Ball(Canvas canvas) {
        random = new Random();
        width = canvas.getWidth();
        height = canvas.getHeight();
//        x=random.nextInt(width);
        x = width / 2;
        lastX=x;
//        y=random.nextInt(height);
        y = height / 2;
        lastY=y;
        direction = random.nextInt(360);
//        speed=width/4+random.nextInt(width/4);
        speed = width+random.nextInt(width/4);
        radius = 5;
        paint = new Paint();
        red = random.nextInt(255);
        green = random.nextInt(255);
        blue = random.nextInt(255);
        paint.setColor(Color.argb(0xff, red, green, blue));
    }

    public void draw(Canvas canvas, double rate) {
        calc(rate);
        for (FireWorkFragmentTailSmaller t : tail) {
            t.draw(canvas);
        }
        canvas.drawCircle(x, y, radius, paint);
    }

    private void calc(double rate) {
        double cosValue = Math.cos(Math.toRadians(direction));
        double sinValue = Math.sin(Math.toRadians(direction));
        double flyingDistance = rate * speed;
        double xPlus = cosValue * flyingDistance;
        double yReduce = sinValue * flyingDistance;
        x += xPlus;
        y -= yReduce;
        adjustPosition();

        cutTail();

        Path path = new Path();
        path.moveTo((float) (lastX + radius * sinValue), (float) (lastY + radius * cosValue));
        path.lineTo((float) (x + radius * sinValue), (float) (y + radius * cosValue));
        path.lineTo((float) (x - radius * sinValue), (float) (y - radius * cosValue));
        path.lineTo((float) (lastX - radius * sinValue), (float) (lastY - radius * cosValue));
        //tail.add(new FireWorkFragmentTailSmaller(path,red,green,blue));
        tail.add(new FireWorkFragmentTailSmaller((int)radius,lastX,x,lastY,y,sinValue,cosValue, red, green, blue));
        lastX = x;
        lastY = y;

        adjustAngle();

    }

    private void adjustPosition() {
        if (x < radius) {
            x = radius;
        } else if (x > width - radius) {
            x = width - radius;
        }
        if (y < radius) {
            y = radius;
        } else if (y > height - radius) {
            y = height - radius;
        }
    }

    private void cutTail() {
        Iterator<FireWorkFragmentTailSmaller> iterator = tail.iterator();
        while (iterator.hasNext()) {
            FireWorkFragmentTailSmaller next = iterator.next();
            if (next.isEnd()) {
                iterator.remove();
            }
        }
    }

    private void adjustAngle() {
        direction += random.nextInt(61) - 30;
        if (x <= radius || y <= radius || x >= width - radius || y >= height - radius) {
            if (x <= radius) {
                if (direction < 180) {
                    direction = 180 - direction;
                } else {
                    direction = 540 - direction;
                }
            } else if (x >= width - radius) {
                if (direction < 180) {
                    direction = 180 - direction;
                } else {
                    direction = 540 - direction;
                }
            } else if (y <= radius) {
                if (direction < 90) {
                    direction = 360 - direction;
                } else {
                    direction = 360 - direction;
                }
            } else if (y >= height - radius) {
                if (direction > 180) {
                    direction = 360 - direction;
                } else {
                    direction = 360 - direction;
                }
            }
        }
    }

}
