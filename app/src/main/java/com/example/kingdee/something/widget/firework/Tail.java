package com.example.kingdee.something.widget.firework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by liwu on 2017/11/27.
 *  主体尾巴
 */

public class Tail {

    private Paint paint;
    private Path path;
    private int f;
    private int F_MAX=10;

    public Tail(Path path){
        this.path=path;
        f=F_MAX;
        paint=new Paint();
    }

    public void draw(Canvas c){
        int a= (int) (0xff*(f/(float)F_MAX)+0.5);
        int color= Color.argb(a,0xff,0xff,0xff);
        paint.setColor(color);
        c.drawPath(path,paint);
        f--;
    }

    public boolean isEnd(){
        return f<=0;
    }
}
