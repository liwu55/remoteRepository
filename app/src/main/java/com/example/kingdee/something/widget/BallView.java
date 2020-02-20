package com.example.kingdee.something.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liwu on 2016/9/5.
 *
 */
public class BallView extends View {

    private int rowCount = 200;
    private int columnCount = 20;
    private List<List<Rect>> data;

    public BallView(Context context) {
        super(context);
    }

    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int eachHeight = height / rowCount;
        int eachWidth = width / columnCount;
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        data = new ArrayList<>();
        initData(eachHeight, eachWidth, data);
        drawBackGround(canvas, paint, data);
        drawBall(canvas,width,height,paint);
        super.draw(canvas);
    }

    private void drawBall(Canvas canvas, int width, int height, Paint paint) {
        Matrix matrix=new Matrix();
        matrix.setRotate(90,width/2,height/2);
        Bitmap drawingCache = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas1=new Canvas(drawingCache);
        drawBackGround(canvas1, paint, data);
        Bitmap ballBitmap= Bitmap.createBitmap(drawingCache, 0, 0, width, height,matrix,false);
        paint.setShader(new BitmapShader(ballBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        canvas.drawCircle(width / 2, height / 2, width * 0.4f, paint);
    }

    private void drawBackGround(Canvas canvas, Paint paint, List<List<Rect>> data) {
        for (int a = 0; a < data.size(); a++) {
            initColor(a,paint);
            List<Rect> rowRects = data.get(a);
            for (int b = 0; b <rowRects.size() ; b++) {
                Rect rect = rowRects.get(b);
                canvas.drawRect(rect,paint);
                changeColor(paint);
            }
        }
    }

    private void initData(int eachHeight, int eachWidth, List<List<Rect>> data) {
        for (int i = 0; i < rowCount; i++) {
            List<Rect> rowRects = new ArrayList<>();
            int top = i * eachHeight;
            int bottom = top + eachHeight;
            for (int j = 0; j < columnCount; j++) {
                int left=j*eachWidth;
                int right=left+eachWidth;
                Rect r = new Rect(left,top,right,bottom);
                rowRects.add(r);
            }
            data.add(rowRects);
        }
    }

    private void initColor(int row,Paint paint) {
        paint.setColor(row%2==0? Color.BLACK: Color.WHITE);
    }

    private void changeColor(Paint paint) {
        if(paint.getColor()== Color.BLACK){
            paint.setColor(Color.WHITE);
        }else{
            paint.setColor(Color.BLACK);
        }
    }
}
