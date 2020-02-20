package com.example.kingdee.something.util;

import android.util.Log;

/**
 * Created by kingdee on 2016/4/8.
 */
public class ToggleLog {

    private static boolean canLog=true;
    private static final String TAG ="------ForDebug------";

    public static void d(String str){
        if(canLog) {
            Log.d(TAG, str);
        }
    }

    public static void d(String TAG,String str){
        if(canLog) {
            Log.d(TAG, str);
        }
    }
}
