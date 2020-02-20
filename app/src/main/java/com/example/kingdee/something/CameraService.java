package com.example.kingdee.something;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class CameraService extends Service {
    public CameraService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(){
            @Override
            public void run() {
                while(true) {
                    Log.d("camera","running");
                    SystemClock.sleep(500);
                    try{
                        Camera open = Camera.open();
                        List<Camera.Area> focusAreas = open.getParameters().getFocusAreas();
                        List<Camera.Area> meteringAreas = open.getParameters().getMeteringAreas();
                        if (focusAreas != null && focusAreas.size() != 0) {

                        } else {
                            Log.d("camera", "focus null");
                        }
                        if (meteringAreas != null && meteringAreas.size() != 0) {

                        } else {
                            Log.d("camera", "meter null");
                        }
                    }catch(Exception e){e.printStackTrace();}
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
