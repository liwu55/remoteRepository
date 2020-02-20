package com.example.kingdee.something.widget.meteor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.kingdee.something.util.ToggleLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Meteor {

    private final int color;
    private float pX, pY;
    private int radius;
    private List<Spark> sparks = new ArrayList<>();
    private int speed;
    private int direction;
    private long lastDrawTime = -1;
    private long drawTime;
    private Random random = new Random();
    private Paint paint = new Paint();
    private int canvasWidth, canvasHeight;

    public Meteor(float pX, float pY, int speed, int direction, int radius, Canvas canvas) {
        this.pX = pX;
        this.pY = pY;
        this.speed = speed;
        this.direction = direction;
        this.radius = radius;
        this.canvasHeight = canvas.getHeight();
        this.canvasWidth = canvas.getWidth();
        this.color = Color.argb(0xff, random.nextInt(255), random.nextInt(255), random.nextInt(255));
        paint.setColor(color);
    }

    public void draw(Canvas canvas, long drawTime) {
        this.drawTime = drawTime;
        draw(canvas);
        drawSpark(canvas);
        change();
    }

    private void drawSpark(Canvas canvas) {
        for (Spark spark : sparks) {
            spark.draw(canvas);
        }
    }

    private void draw(Canvas canvas) {
        ToggleLog.d("meteorDraw", "px=" + pX);
        ToggleLog.d("meteorDraw", "pY=" + pY);


        canvas.drawCircle(pX, pY, radius, paint);
    }

    private void change() {
        //改变xy
        long dTime = drawTime - (lastDrawTime == -1 ? drawTime : lastDrawTime);
        float moveDistance = dTime / 1000f * speed;
        float xMoveDistance = (float) (moveDistance * Math.cos(direction / 180f * Math.PI));
        float yMoveDistance = (float) (-moveDistance * Math.sin(direction / 180f * Math.PI));
        pX += xMoveDistance;
        pY += yMoveDistance;

        //移除
        Iterator<Spark> iterator = sparks.iterator();
        while (iterator.hasNext()) {
            Spark spark = iterator.next();
            if (spark.isDie()) {
                iterator.remove();
            }
        }
        //新增
        for (int i = 0; i < 3; i++) {
            int xRandomAdd = random.nextInt((radius * 2)) - radius;
            int yRandomAdd = random.nextInt((radius * 2)) - radius;

            int radiusRange = radius / 6;
            int radius = random.nextInt(radiusRange) + 1;
            sparks.add(new Spark(xRandomAdd + pX, yRandomAdd + pY, radius, color));
        }

        lastDrawTime = drawTime;
    }

    public boolean isDie() {
        if (pX < -canvasWidth * 0.2 || pX > canvasWidth * 1.2 || pY < -canvasHeight * 0.2 || pY > canvasHeight * 1.2) {
            return true;
        }
        return false;
    }
}
