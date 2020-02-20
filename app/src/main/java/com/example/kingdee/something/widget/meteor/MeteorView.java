package com.example.kingdee.something.widget.meteor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.example.kingdee.something.util.ToggleLog;
import com.example.kingdee.something.widget.firework.MyBaseSurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MeteorView extends MyBaseSurfaceView {

    private List<Meteor> meteors = new ArrayList<>();
    private Random random = new Random();
    private int f = 0;
    private long drawTime;
    private int canvasWidth = -1, canvasHeight = -1;

    public MeteorView(Context context) {
        super(context);
    }

    public MeteorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawSelf(Canvas canvas) {
        if (canvasWidth == -1 || canvasHeight == -1) {
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
        }
        drawTime = System.currentTimeMillis();
        drawBackGround(canvas);
        drawMeteor(canvas);
        f++;
    }

    private void drawMeteor(Canvas canvas) {
        removeMeteors();
        addMeteors(canvas);
        drawMeteors(canvas);
    }

    private void removeMeteors() {
        Iterator<Meteor> iterator = meteors.iterator();
        while (iterator.hasNext()) {
            Meteor next = iterator.next();
            if (next.isDie()) {
                iterator.remove();
            }
        }
    }

    private void drawMeteors(Canvas canvas) {
        for (Meteor meteor : meteors) {
            meteor.draw(canvas, drawTime);
        }
    }

    private void addMeteors(Canvas canvas) {
        if (f % 10 == 0) {
            //int randomY = random.nextInt(100);
            int baseSpeed = canvasWidth / 5;
            int randomSpeedRange = canvasWidth / 10;
            int randomSpeed = random.nextInt(randomSpeedRange) + baseSpeed;

            int randomDirection = random.nextInt(60) - 30;

            int baseRadius = canvasWidth / 60;
            int randomRadiusRange = canvasWidth / 120;
            int randomRadius = random.nextInt(randomRadiusRange) + baseRadius;

            meteors.add(new Meteor(0, canvasHeight / 2, randomSpeed, randomDirection, randomRadius, canvas));
        }
        ToggleLog.d("meteorsCount", "count=" + meteors.size());
    }

    private void drawBackGround(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }
}
