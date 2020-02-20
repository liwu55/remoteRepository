package com.example.kingdee.something.widget.firework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by liwu on 2017/11/28.
 * 碎片尾巴
 */

public class FireWorkFragmentTail {

    private final int red;
    private final int green;
    private final int blue;
    private final Path path;
    private Paint paint;
    private int f;
    private int F_MAX=10;

    public FireWorkFragmentTail(Path path, int red, int green, int blue){
        this.path=path;
        this.red=red;
        this.green=green;
        this.blue=blue;
        f=F_MAX;
        paint=new Paint();
    }

    public void draw(Canvas c){
        int a= (int) (0xff*(f/(float)F_MAX)+0.5);
        int color= Color.argb(a,red,green,blue);
        paint.setColor(color);
        c.drawPath(path,paint);
        f--;
    }

    public boolean isEnd(){
        return f<=0;
    }
}
