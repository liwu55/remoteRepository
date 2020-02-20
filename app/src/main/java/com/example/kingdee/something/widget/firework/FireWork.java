package com.example.kingdee.something.widget.firework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by liwu on 2017/11/27.
 * 主体
 */

public class FireWork {

    int STATE_FLYING = 0;
    int STATE_SHOW = 1;
    int STATE_END = 2;

    public static int radius;
    public static int countFragment;
    //飞行方向 度
    private int direction;
    //飞行速度 px/s
    private double speed;
    //阻力 %/s
    private int speedReducePerS;
    //
    private double x = -1;
    private double lastX = -1;
    private double y = -1;
    private double lastY = -1;
    //画笔
    Paint paint;
    private int state = STATE_FLYING;
    private List<FireWorkFragment> fragmentList;

    //尾巴
    private List<TailSmaller> tail = new ArrayList();

    Random random;

    public FireWork() {
        random = new Random();
        speed = 1000 + random.nextInt(401);
        speedReducePerS = 500 + random.nextInt(201);
        direction = 85 + random.nextInt(11);
//        radius =3+random.nextInt(3);
        radius = 9;
        countFragment = 20 + random.nextInt(10);
    }

    public void drawSelf(Canvas c, float rate) {
        calc(c, rate);
        if (state == STATE_FLYING) {
            c.drawCircle((float) x, (float) y, radius, paint);
        } else if (state == STATE_SHOW) {
            boolean someFly = false;
            if (fragmentList != null) {
                for (FireWorkFragment f : fragmentList) {
                    if (f.isFlying()) {
                        f.draw(c);
                        someFly = true;
                    }
                }
                if (!someFly) {
                    state = STATE_END;
                }
            }
        }
        for (TailSmaller t : tail) {
            t.draw(c);
        }
    }

    public void calc(Canvas c, float rate) {
        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.WHITE);
        }
        if (x == -1 && y == -1) {
            initPosition(c);
        }
        cutTail();
        changeDirection();
        if (state == STATE_FLYING) {
            double flyingDistance = rate * speed;
            double cosValue = Math.cos(Math.toRadians(direction));
            double sinValue = Math.sin(Math.toRadians(direction));
            double xPlus = cosValue * flyingDistance;
            double yReduce = sinValue * flyingDistance;
            x += xPlus;
            y -= yReduce;

            Path path = new Path();
            path.moveTo((float) (lastX + radius * sinValue), (float) (lastY + radius * cosValue));
            path.lineTo((float) (x + radius * sinValue), (float) (y + radius * cosValue));
            path.lineTo((float) (x - radius * sinValue), (float) (y - radius * cosValue));
            path.lineTo((float) (lastX - radius * sinValue), (float) (lastY - radius * cosValue));
            tail.add(new TailSmaller(radius,lastX,x,lastY,y,sinValue,cosValue));

            speed -= speedReducePerS * rate;
            lastX = x;
            lastY = y;
        }
        if (state == STATE_SHOW) {
            if (fragmentList == null) {
                fragmentList = new ArrayList<>();
                for (int i = 0; i < countFragment; i++) {
                    fragmentList.add(new FireWorkFragment(x, y,c));
                }
            }
            for (int i = 0; i < fragmentList.size(); i++) {
                fragmentList.get(i).calc(rate);
            }
        }
        if (speed <= 0) {
            state = STATE_SHOW;
        }


    }

    private void changeDirection() {
        int dC = random.nextInt(7) - 3;
        direction += dC;
    }

    private void cutTail() {
        Iterator<TailSmaller> iterator = tail.iterator();
        while (iterator.hasNext()) {
            TailSmaller next = iterator.next();
            if (next.isEnd()) {
                iterator.remove();
            }
        }
    }

    private void initPosition(Canvas c) {
        int width = c.getWidth();
        int height = c.getHeight();
        int width13 = width / 3;
        x = width13 + random.nextInt(width13);
        lastX = x;
        y = height;
        lastY = height;
    }

    public boolean isEnd() {
        return state == STATE_END;
    }
}
