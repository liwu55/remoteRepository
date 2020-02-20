package com.example.kingdee.something.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kingdee.something.util.ToggleLog;


/**
 * Created by kingdee on 2016/5/31.
 */
public class NumberTextView extends EditText {
    private boolean changing=false;

    public NumberTextView(Context context) {
        super(context);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setClickable(true);
//        setFocusable(true);
//        setFocusableInTouchMode(true);
//        setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(changing){
            canvas.drawColor(Color.GREEN);
        }
        super.onDraw(canvas);
        int width=canvas.getWidth();
        int height=canvas.getHeight();
        ToggleLog.d("ondraw","width="+width+" height="+height);
        Paint paint=getPaint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0,0,2,height,paint);
        canvas.drawRect(0,0,width,2,paint);
        canvas.drawRect(width-2,0,width,height,paint);
        canvas.drawRect(0, height - 2, width, height, paint);
    }

    public void setChanging(boolean changing) {
        this.changing=changing;
    }

    public boolean isChanging(){
        return changing;
    }
}
