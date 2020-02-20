package com.example.kingdee.something;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

public class SoftKeyBoardActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_soft_key_board);
        EditText et= (EditText) findViewById(R.id.et);
        et.requestFocus();
        et.setText("something");
    }
}
