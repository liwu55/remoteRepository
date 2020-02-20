package com.example.kingdee.something;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EatPage extends Activity {

    private Button eatWhat;
    private TextView eatText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_eat);
        eatWhat = (Button) findViewById(R.id.page_eat_eat_what);
        eatWhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eatWhat.setText("不想吃这个");
                String food=getRandomFood();
                eatText.setText(getTextWithFood(food));
            }
        });
        eatText = (TextView) findViewById(R.id.page_eat_eat_text);
    }

    @NonNull
    private String getTextWithFood(String food) {
        return "吃"+food+"吧";
    }

    private String getRandomFood() {
        return "辣子鸡";
    }
}
