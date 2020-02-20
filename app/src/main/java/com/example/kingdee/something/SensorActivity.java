package com.example.kingdee.something;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class SensorActivity extends AppCompatActivity {


    private List<String> data;
    private List<Sensor> sensors;
    private Sensor curSensor;
    private SensorManager m;
    private SensorEventListener listener;
    private TextView tvX;
    private TextView tvY;
    private TextView tvZ;
    private TextView tvDX;
    private TextView tvDY;
    private TextView tvDZ;
    private TextView tvCurSensor;
    private int Time=1000000;
    private float lastX;
    private float lastY;
    private float lastZ;
    private boolean init=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        data=new ArrayList<>();
        ListView listView= (ListView) findViewById(R.id.listview);
        listView.setAdapter(new MyAdapter());
        tvDX = (TextView) findViewById(R.id.dx);
        tvDY = (TextView) findViewById(R.id.dy);
        tvDZ = (TextView) findViewById(R.id.dz);
        tvX = (TextView) findViewById(R.id.x);
        tvY = (TextView) findViewById(R.id.y);
        tvZ = (TextView) findViewById(R.id.z);
        tvCurSensor = (TextView) findViewById(R.id.curSensor);
        m = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = m.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < sensors.size(); i++) {
            Sensor sensor = sensors.get(i);
            Log.d("sensor","type="+ sensor.getStringType());
            if(i==0){
                curSensor=sensor;
                tvCurSensor.setText("curSensor:"+curSensor.getName());
            }
            data.add(sensor.getName());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m.unregisterListener(listener,curSensor);
                curSensor= sensors.get(position);
                tvCurSensor.setText("curSensor:"+curSensor.getName());
                m.registerListener(listener,curSensor, Time);
                init=false;
            }
        });
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                float valueX = values[0];
                float valueY = values[1];
                float valueZ = values[2];
                tvX.setText(valueX+"");
                tvY.setText(valueY+"");
                tvZ.setText(valueZ+"");
                if(!init){
                    lastX=valueX;
                    lastY=valueY;
                    lastZ=valueZ;
                    init=true;
                }
                Log.d("value","lastX="+lastX);
                Log.d("value","lastY="+lastY);
                Log.d("value","lastZ="+lastZ);
                Log.d("value","valueX="+valueX);
                Log.d("value","valueY="+valueY);
                Log.d("value","valueZ="+valueZ);
                Log.d("value","------------------");
                float dX=lastX-valueX;
                float dY=lastY-valueY;
                float dZ=lastZ-valueZ;
                tvDX.setText(dX+"");
                tvDY.setText(dY+"");
                tvDZ.setText(dZ+"");
                lastX=valueX;
                lastY=valueY;
                lastZ=valueZ;
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        m.registerListener(listener,curSensor,Time);
    }
    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            if(data!=null) {
                return data.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if(data!=null) {
                return data.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv=new TextView(SensorActivity.this);
            tv.setText(data.get(position));
            return tv;
        }
    }
}
