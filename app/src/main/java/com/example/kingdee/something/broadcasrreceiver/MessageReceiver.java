package com.example.kingdee.something.broadcasrreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by kingdee on 2016/6/12.
 */
public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"短信来了",Toast.LENGTH_SHORT).show();
    }
}
