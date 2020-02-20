package com.example.kingdee.something.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.kingdee.something.util.ToggleLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingdee on 2016/5/17.
 */
public class SandGlassView extends ImageView {

    private final int HEIGHT_DIBIAN = 10;
    private final float rateSrc = 0.005f;
    private float rate = rateSrc;
    private final String COLOR_SANDGLASS = "#FC8724";
    private int fps=10;
    private int ac=3;

    private int width;
    private Paint paint;
    private float percent = 0f;
    private float lastX11, lastY11, lastX44, lastY44;
    private boolean notInit = true;
    private List<Float> rateList;
    private int index = 0;
    private int size;
    private int height;
    private int left1;
    private int top1;
    private int right1;
    private int bottom1;
    private int left2;
    private int top2;
    private int right2;
    private int bottom2;
    private int tondao;
    private int bihou;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;
    private float x4;
    private float y4;
    private float x5;
    private float y5;
    private float x6;
    private float y6;
    private float x7;
    private float y7;
    private float x8;
    private float y8;
    private float x11;
    private float y11;
    private float x22;
    private float y22;
    private float x33;
    private float y33;
    private float x44;
    private float y44;
    private float sandHeight;
    private float x111;
    private float y111;
    private float x222;
    private float y222;
    private float x333;
    private float y333;
    private float x444;
    private float y444;
    private int bian;
    private int sand;
    private int bianju;

    public SandGlassView(Context context) {
        this(context, null);
    }

    public SandGlassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    private void initData() {

        rateList = new ArrayList<>();
        rateList.add(rate);
        while (percent < 2) {
            percent += rate;
            rate = ((percent / 2) * ac + 1) * rateSrc;
            rateList.add(rate);
        }
        size = rateList.size();
        paint = new Paint();
        paint.setColor(Color.parseColor(COLOR_SANDGLASS));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (notInit) {
            notInit = false;
            width = canvas.getWidth();
            height = canvas.getHeight();
            //上面的底边
            left1 = width / 10;
            top1 = 0;
            right1 = width - left1;
            bottom1 = height / HEIGHT_DIBIAN;
            //下面的底边
            left2 = left1;
            top2 = height - bottom1;
            right2 = right1;
            bottom2 = height;

            tondao = width / 15;
            if(tondao<3){
                tondao=3;
            }
            bian = tondao/2;
            if(bian<1){
                bian=1;
            }
            sand = tondao/4;
            if(sand<1){
                sand=1;
            }
            bihou = tondao - bian - sand;
            bianju = bottom1;

            x1 = left2 + bianju;
            y1 = bottom1;
            x2 = width / 2 - tondao;
            y2 = x2 - x1 + y1;
            x3 = x2 + bian;
            y3 = y2;
            x4 = x1 + bian;
            y4 = y1;
            x5 = x1;
            y5 = top2;
            x6 = x2;
            y6 = y5 - x6 + x5;
            x7 = x3;
            y7 = y6;
            x8 = x4;
            y8 = y5;

            //上面沙子
            x11 = x4 + bihou;
            y11 = y4;

            x22 = x3 + bihou;
            y22 = y3;

            x33 = width - x22;
            y33 = y3;

            x44 = width - x11;
            y44 = y4;

            //上
            lastX11 = x11;
            lastY11 = y11;
            lastX44 = x44;
            lastY44 = y44;

            sandHeight = y22 - y11;
            //下面沙子
            x111 = x3 + bihou;
            y111 = y5;
            x222 = x3 + bihou;
            y222 = y5;
            x333 = width - x222;
            y333 = y5;
            x444 = width - x222;
            y444 = y5;
        }
        canvas.drawRect(left1, top1, right1, bottom1, paint);
        canvas.drawRect(left2, top2, right2, bottom2, paint);
        drawPath(canvas, x1, y1, x2, y2, x6, y6, x5, y5, x8, y8, x7, y7, x3, y3, x4, y4, x1, y1);
        //右边的半边
        oppsite(canvas, x1, y1, x2, y2, x6, y6, x5, y5, x8, y8, x7, y7, x3, y3, x4, y4);

        float d = rateList.get(index) * sandHeight;
        float d2 = rateList.get(size - index - 1) * sandHeight;
        index++;
        long leftOrRight = Math.round(Math.random());
        long leftOrRight2 = Math.round(Math.random());
        y222 -= d2 / 2;
        y333 -= d2 / 2;
        if (leftOrRight2 == 0) {
            if(x111<(x4 + bihou)){
                x444 += d2;
            }else {
                x111 -= d2;
            }
        } else {
            if(x444>(width-x4-bihou)){
                x111 -= d2;
            }else {
                x444 += d2;
            }
        }
        if (leftOrRight == 0) {
            if(lastX11>x3 + bihou){
                lastX44 -= d;
                lastY44 += d;
            }else {
                lastX11 += d;
                lastY11 += d;
            }
        } else {
            if(lastX44<(width-x3 - bihou)){
                lastX11 += d;
                lastY11 += d;
            }else {
                lastX44 -= d;
                lastY44 += d;
            }
        }
        if (index == rateList.size()) {
            index = 0;
            lastX11 = x4 + bihou;
            lastY11 = y4;
            lastX44 = width - x4 - bihou;
            lastY44 = y4;

            y222 = y5;
            y333 = y5;
            x111 = x3 + bihou;
            x444 = width - x111;
        }

        drawPath(canvas, lastX11, lastY11, x22, y22, x33, y33, lastX44, lastY44);

        //中间流体
        float x55 = x22;
        float y55 = top2;

        float x66 = x33;
        float y66 = top2;

        drawPath(canvas, x22, y22, x33, y33, x66, y66, x55, y55, x22, y22);

        drawPath(canvas, x111, y111, x222, y222, x333, y333, x444, y444, x111, y111);
        postInvalidateDelayed(1000/fps);
    }

    private void drawPath(Canvas canvas, float... point) {
        float x = 0;
        float y = 0;
        Path path = new Path();
        for (int i = 0; i < point.length; i++) {
            if (i % 2 == 0) {
                x = point[i];
            } else {
                y = point[i];
                if (i >= 1) {
                    if (i == 1) {
                        path.moveTo(x, y);
                    } else {
                        path.lineTo(x, y);
                    }
                }
            }
        }
        canvas.drawPath(path, paint);
    }

    private void oppsite(Canvas canvas, float... point) {
        float x = 0;
        float y = 0;
        Path path = new Path();
        for (int i = 0; i < point.length; i++) {
            if (i % 2 == 0) {
                x = width - point[i];
            } else {
                y = point[i];
                if (i >= 1) {
                    if (i == 1) {
                        path.moveTo(x, y);
                    } else {
                        path.lineTo(x, y);
                    }
                }
            }
        }
        canvas.drawPath(path, paint);
    }

}
