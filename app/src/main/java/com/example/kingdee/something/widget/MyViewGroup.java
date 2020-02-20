package com.example.kingdee.something.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by liwu on 2017/9/14.
 *
 */

public class MyViewGroup extends RelativeLayout{

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("touchEvent","ViewGroup onInterceptTouchEvent="+ev.getAction());
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("touchEvent","ViewGroup dispatchTouchEvent="+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("touchEvent","ViewGroup onTouchEvent="+event.getAction());
        return true;
    }
}
