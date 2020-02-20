package com.example.kingdee.something;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DialogActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在加载");
        dialog.setCancelable(false);
        dialog.show();
        new Thread(){
            @Override
            public void run() {

            }
        }.start();
    }
}
