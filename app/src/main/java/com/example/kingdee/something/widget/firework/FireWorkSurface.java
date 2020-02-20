package com.example.kingdee.something.widget.firework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liwu on 2017/9/29.
 *
 */

public class FireWorkSurface extends MyBaseSurfaceView {

    private List<FireWork> fireWorks;
    private long lastCalcTime=-1;
    private int fCount=0;

    public FireWorkSurface(Context context) {
        super(context);
    }

    public FireWorkSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        fireWorks = new ArrayList<>();
    }

    @Override
    protected void drawSelf(Canvas canvas) {
        fCount++;
        //添加烟花
        tryAddFireWork();
        //删除无效的烟花
        deleteEndFireWork();
        //时间
        long curTime = System.currentTimeMillis();
        float rate = getRate(curTime);
        //背景
        canvas.drawColor(Color.BLACK);
        //烟花绽放
        for (FireWork fire:fireWorks){
            fire.drawSelf(canvas,rate);
        }
        //刷新时间
        lastCalcTime = curTime;
    }

    private void deleteEndFireWork() {
        Iterator<FireWork> iterator = fireWorks.iterator();
        while(iterator.hasNext()){
            FireWork next = iterator.next();
            if(next.isEnd()){
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

    private void tryAddFireWork() {
        if(fCount%20==0){
            fireWorks.add(new FireWork());
        }
    }
}
