package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kingdee.something.widget.WaveView;
import com.example.kingdee.something.widget.WaveViewOther;

public class WaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        WaveView waveView= (WaveView) findViewById(R.id.waveview);
        WaveViewOther waveViewOther= (WaveViewOther) findViewById(R.id.waveviewother);
//        waveViewOther.s
        waveViewOther.startWave();
    }
}
