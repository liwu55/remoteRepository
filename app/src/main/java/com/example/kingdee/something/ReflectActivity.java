package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.reflect.Field;

public class ReflectActivity extends AppCompatActivity {
    @Mine(R.id.tv1)
    TextView tv1;
    @Mine(R.id.tv2)
    TextView tv2;
    @Mine(R.id.tv3)
    TextView tv3;
    @Mine(R.id.tv4)
    TextView tv4;
    @Mine(R.id.tv5)
    TextView tv5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        reflect();
    }

    private void reflect() {
        Class c=this.getClass();
        Field[] fields = c.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Mine mine = fields[i].getAnnotation(Mine.class);
            int id = mine.value();
            try {
                fields[i].set(this,findViewById(id));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
