package com.example.kingdee.something.widget.firework2;

import android.graphics.Canvas;

import com.example.kingdee.something.widget.firework.FireWorkFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liwu on 2017/12/4.
 */

public class FireWorkCode {

    private static final int FIRE_F = 1;
    private static final int FIRE_EACH_COUNT = 1;
    List<FireWorkFragment> fragments = new ArrayList<>();
    private int f = 1;
    private long lastCalcTime = -1;
    private final int TYPE_TOUCH = 0;
    private final int TYPE_AUTO = 1;
    private int type = TYPE_TOUCH;
    private final double x;
    private final double y;
    private float touchX;
    private float touchY;
    private boolean fire;

    public FireWorkCode(Canvas canvas) {
        x = canvas.getWidth() / 2;
        y = canvas.getHeight();
    }

    public void draw(Canvas canvas) {
        long curTime = System.currentTimeMillis();
        float rate = getRate(curTime);
        removeOutFragment();
        createFragment(canvas);
        for (FireWorkFragment fragment : fragments) {
            fragment.calc(rate);
            fragment.draw(canvas);
        }
        lastCalcTime = curTime;
    }

    private void removeOutFragment() {
        Iterator<FireWorkFragment> iterator = fragments.iterator();
        while (iterator.hasNext()) {
            FireWorkFragment next = iterator.next();
            if (!next.isFlying()) {
                iterator.remove();
            }
        }
    }

    private float getRate(long curTime) {
        if (lastCalcTime == -1) {
            lastCalcTime = curTime;
        }
        long dur = curTime - lastCalcTime;
        return dur / 1000f;
    }

    private void createFragment(Canvas canvas) {
        if (type == TYPE_AUTO) {
            if (f % FIRE_F == 0) {
                for (int i = 0; i < FIRE_EACH_COUNT; i++) {
                    fragments.add(new FireWorkFragment(x, y, touchX, touchY, canvas, FireWorkFragment.TYPE_GROUND));
                }
            }
        } else if (type == TYPE_TOUCH) {
            if (fire && f % FIRE_F == 0) {
                for (int i = 0; i < FIRE_EACH_COUNT; i++) {
                    fragments.add(new FireWorkFragment(x, y, touchX, touchY, canvas, FireWorkFragment.TYPE_GROUND));
                }
            }
        }
        f++;
    }

    public void sign(float x, float y) {
        this.touchX = x;
        this.touchY = y;
    }

    public void fire(boolean fire) {
        this.fire = fire;
    }
}
