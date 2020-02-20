package com.example.kingdee.something.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.kingdee.something.R;

public class MSKView extends ImageView {

    private int clearNum = 30;

    public MSKView(Context context) {
        super(context);
    }

    public MSKView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MSKView);
        clearNum = ta.getInt(R.styleable.MSKView_clearNum, 20);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        Canvas myCanvas = new Canvas();
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        myCanvas.setBitmap(b);
        super.onDraw(myCanvas);
        int rowCount = width / clearNum;
        if (width % clearNum != 0) {
            rowCount++;
        }
        int columnCount = height / clearNum;
        if (height / clearNum != 0) {
            columnCount++;
        }
        Paint paint = new Paint();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                int red = 0, green = 0, blue = 0;
                int count = 0;
                for (int k = 0; k < clearNum; k++) {
                    for (int l = 0; l < clearNum; l++) {
                        int x = k + i * clearNum;
                        if (x >= width) {
                            break;
                        }
                        int y = l + j * clearNum;
                        if (y >= height) {
                            break;
                        }
                        int pixel = b.getPixel(x, y);
                        red += Color.red(pixel);
                        green += Color.green(pixel);
                        blue += Color.blue(pixel);
                        count++;
                    }
                }
                if (count != 0) {
                    red = red / count;
                    green = green / count;
                    blue = blue / count;
                }
                int color = Color.rgb(red, green, blue);
                paint.setColor(color);
                int left = i * clearNum;
                int top = j * clearNum;
                int right = left + clearNum;
                int bottom = top + clearNum;
                Rect rect = new Rect(left, top, right, bottom);
                canvas.drawRect(rect, paint);
            }
        }
    }
}
