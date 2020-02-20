package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host_acitivity);
        TabHost th= (TabHost) findViewById(R.id.tabhost);
        th.setup();
        th.addTab(th.newTabSpec("标签1").setIndicator("标签1").setContent(R.id.tab_content_first));
        th.addTab(th.newTabSpec("标签2").setIndicator("标签2").setContent(R.id.tab_content_second));
    }
}
