package com.example.kingdee.something;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScrollActivity extends AppCompatActivity {

    private List<String> data1;
    private List<String> data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        ListView lv1 = (ListView) findViewById(R.id.listview1);
        ListView lv2 = (ListView) findViewById(R.id.listview2);
        data1 = new ArrayList<>();
        data2 = new ArrayList<>();
        initData();
        lv1.setAdapter(new MyAdapter(data1));
        lv2.setAdapter(new MyAdapter(data2));

    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            data1.add("名称"+i);
            data2.add("数据"+i);
        }
    }

    private class MyAdapter extends BaseAdapter {

        private List<String> data;

        public MyAdapter(List<String> data){
            this.data=data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=View.inflate(ScrollActivity.this,R.layout.item_list,null);
            TextView tv= (TextView) view.findViewById(R.id.number);
            tv.setText(data.get(position));
            return view;
        }

    }
}
