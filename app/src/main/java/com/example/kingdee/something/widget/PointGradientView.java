package com.example.kingdee.something.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liwu on 2016/7/19.
 *
 */
public class PointGradientView extends View{

    public PointGradientView(Context context) {
        super(context);
    }

    public PointGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x= (int) event.getX();
                int y= (int) event.getY();

                break;
        }
        return true;
    }
}
