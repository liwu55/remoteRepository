package com.example.kingdee.something.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liwu on 2016/8/11.
 *
 */
public class MyProgressView extends View {
    //底颜色
    private int color1= Color.GREEN;
    //上层颜色
    private int color2=Color.RED;
    //进度
    private int progress=0;
    
    public MyProgressView(Context context) {
        this(context, null);
    }

    public MyProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(color1);
        int width=canvas.getWidth();
        int height=canvas.getHeight();
        float left=(100-progress)/100f*width;
        Paint p=new Paint();
        p.setColor(color2);
        canvas.drawRect(left,0,width,height,p);
    }

    public void setProgress (int p){
        if(p<0||p>100){
            return;
        }
        this.progress=p;
        invalidate();
    }
}
