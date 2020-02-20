package com.example.kingdee.something;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class HandlerPage extends AppCompatActivity {

    private Handler mainHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG,Thread.currentThread().getName()+" 接受到数据:"+msg.obj);
        }
    };
    private Handler childHandler;
    private static final String TAG="MSGPAGE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_msg);
        openChildThread();
        /*ArrayList a=new ArrayList<Integer>();
        a.add(1);
        a.remove(2);
        a.size();*/
        HashMap<String,String> map=new HashMap<>();
        map.put("a","b");
        map.remove("a");
        map.size();


        LinkedList list=new LinkedList<String>();
        list.add(1,"1");
        list.addFirst(1);
        list.addLast(1);
        list.getFirst();
        list.getLast();
        list.get(1);
        list.getFirst();
        list.getLast();
        list.remove("1");
    }

    private void openChildThread() {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                childHandler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d(TAG,Thread.currentThread().getName()+" 接受到数据:"+msg.obj);
                    }
                };
                Looper.loop();
                while(true){
                    Log.d(TAG,Thread.currentThread().getName()+"子线程发送数据");
                    Message message = new Message();
                    message.obj="来自子线程的数据";
                    mainHandler.sendMessage(message);
                    SystemClock.sleep(2000);
                }
            }
        }.start();
    }

    public void sendMain(View v){

    }

    public void sendChild(View v){
        Log.d(TAG,"主线程发送数据");
        Message message = new Message();
        message.obj="来自主线程的数据";
        childHandler.sendMessage(message);
    }
}
