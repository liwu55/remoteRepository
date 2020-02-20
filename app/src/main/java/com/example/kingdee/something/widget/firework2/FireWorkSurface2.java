package com.example.kingdee.something.widget.firework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.kingdee.something.widget.firework.MyBaseSurfaceView;

/**
 * Created by liwu on 2017/12/4.
 * 烟花2
 */

public class FireWorkSurface2 extends MyBaseSurfaceView {

    private FireWorkCode code;

    public FireWorkSurface2(Context context) {
        super(context);
    }

    public FireWorkSurface2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawSelf(Canvas canvas) {
        if(code==null){
            code=new FireWorkCode(canvas);
        }
        canvas.drawColor(Color.BLACK);
        code.draw(canvas);
        super.drawSelf(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                code.fire(true);
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                code.sign(x,y);
                break;
            case MotionEvent.ACTION_UP:
                code.fire(false);
                break;
        }
        return true;
    }
}
