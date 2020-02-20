package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.kingdee.something.util.ToggleLog;

public class MultiPoiontActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_poiont);
        ImageView iv = (ImageView) findViewById(R.id.pic);
        if (iv != null) {
            iv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float x1 = event.getX(0);
                    float y1 = event.getY(0);
                    float x2=0;
                    float y2=0;
                    if(event.getPointerCount()>1) {
                         x2= event.getX(1);
                        y2 = event.getY(1);
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            TLd("ACTION_DOWN",x1,y1,x2,y2);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            TLd("ACTION_MOVE",x1,y1,x2,y2);
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            TLd("ACTION_POINTER_DOWN",x1,y1,x2,y2);
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            TLd("ACTION_POINTER_UP",x1,y1,x2,y2);
                            break;
                        case MotionEvent.ACTION_UP:
                            TLd("ACTION_UP",x1,y1,x2,y2);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    private void TLd(String str,float x1,float y1,float x2,float y2) {
        ToggleLog.d("motion",str + " x1=" + x1 + " y1=" + y1 + " x2=" + x2 + " y2=" + y2);
    }
}
