package com.example.kingdee.something;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.HorizontalScrollView;

public class CubeActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private ShowThread showThread;
    private Cube cube;
    private SurfaceView stage;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        stage = (SurfaceView) findViewById(R.id.stage);
        holder = stage.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        showThread = new ShowThread();
        showThread.showing = true;
        cube = new Cube();
        showThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        showThread.showing = false;
    }

    private class ShowThread extends Thread {
        public boolean showing = false;

        @Override
        public void run() {
            synchronized (this) {
                if (showing) {
                    Canvas canvas = holder.lockCanvas();
                    cube.draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            super.run();
        }
    }
}
