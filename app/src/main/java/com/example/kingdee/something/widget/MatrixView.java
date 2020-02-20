package com.example.kingdee.something.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.kingdee.something.R;

/**
 * Created by liwu on 2016/11/11.
 * 矩阵变换
 */
public class MatrixView extends View implements View.OnTouchListener {

    private Paint p;
    private float degree=0;
    private Bitmap bitmap;
    private Matrix matrix;

    public MatrixView(Context context) {
        super(context);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        setOnTouchListener(this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        matrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        matrix.setTranslate(100,100);
        matrix.setSkew(1, 1);
        canvas.drawBitmap(bitmap,matrix,p);
        super.onDraw(canvas);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                degree+=5;
                break;
        }
        invalidate();
        return true;
    }
}
