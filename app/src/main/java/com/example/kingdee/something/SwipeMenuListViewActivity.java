package com.example.kingdee.something;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class SwipeMenuListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu_list_view);
        SwipeMenuListView lv= (SwipeMenuListView) findViewById(R.id.listView);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Log.d("ooo","getViewposition="+position);
                TextView tv=new TextView(SwipeMenuListViewActivity.this);
                tv.setText("条目"+position);
                tv.setTextSize(30);
                tv.setTextColor(Color.parseColor("#ff0000"));
                return tv;
            }
        });
        SwipeMenuCreator creator=new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem item1=new SwipeMenuItem(SwipeMenuListViewActivity.this);
                item1.setWidth(200);
                item1.setTitle("菜单1");
                item1.setTitleSize(18);
                item1.setTitleColor(Color.WHITE);
                item1.setBackground(new ColorDrawable(Color.parseColor("#33ff0000")));
                menu.addMenuItem(item1);
                SwipeMenuItem item2=new SwipeMenuItem(SwipeMenuListViewActivity.this);
                item2.setWidth(200);
                item2.setTitle("菜单2");
                item2.setTitleSize(18);
                item2.setTitleColor(Color.WHITE);
                item2.setBackground(new ColorDrawable(Color.parseColor("#3300ff00")));
                menu.addMenuItem(item2);
            }
        };
        lv.setMenuCreator(creator);
        lv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Log.d("ooo","position="+position);
                Log.d("ooo","index="+index);
                return false;
            }
        });
    }
}
