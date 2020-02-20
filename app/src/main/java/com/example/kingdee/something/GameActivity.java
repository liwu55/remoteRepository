package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kingdee.something.widget.MyProgressView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        MyProgressView progress= (MyProgressView) findViewById(R.id.progress);
        progress.setProgress(98);
    }
}
