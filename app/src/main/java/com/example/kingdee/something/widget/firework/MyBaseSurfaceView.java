package com.example.kingdee.something.widget.firework;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.kingdee.something.util.ToggleLog;

/**
 * Created by liwu on 2016/10/24.
 * surfaceview 基类
 */
public class MyBaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private boolean mCanDraw;
    private int fps=40;

    public MyBaseSurfaceView(Context context) {
        this(context, null);
    }

    public MyBaseSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCanDraw = true;
        new DrawThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCanDraw = false;
    }

    private class DrawThread extends Thread {
        @Override
        public void run() {
            while (mCanDraw) {
                try {
                    long start=System.currentTimeMillis();
                    Canvas canvas = mHolder.lockCanvas();
                    drawSelf(canvas);
                    mHolder.unlockCanvasAndPost(canvas);
                    long end=System.currentTimeMillis();
                    long dur=end-start;
                    long wantDur=1000/fps;
                    long sleepTime=wantDur-dur;
                    if(sleepTime>0){
                        ToggleLog.d("fps","sleep "+sleepTime);
                        SystemClock.sleep(sleepTime);
                    }else{
                        ToggleLog.d("fps","out of time");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            super.run();
        }
    }

    protected void drawSelf(Canvas canvas) {

    }
}
