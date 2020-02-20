package com.example.kingdee.something.anim;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.kingdee.something.R;

public class AnimPage extends Activity {

    private View animPart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_anim);
        animPart = findViewById(R.id.anim_part);
        View start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animStart();
            }
        });
    }

    private void animStart() {
        int measuredHeight = animPart.getMeasuredHeight();
        int measuredWidth = animPart.getMeasuredWidth();
        int toBeHeight= (int) (measuredHeight*1.3);
        int toBeWidth= (int) (measuredWidth*1.3);
        ValueAnimator heightAnim = ValueAnimator.ofInt(measuredHeight, toBeHeight);
        heightAnim.setDuration(600);
        heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = animPart.getLayoutParams();
                layoutParams.height= (int) animation.getAnimatedValue();
                Log.d("anim","height="+layoutParams.height);
                animPart.setLayoutParams(layoutParams);
            }
        });
        heightAnim.start();

        ValueAnimator widthAnim = ValueAnimator.ofInt(measuredWidth, toBeWidth);
        widthAnim.setDuration(600);
        widthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = animPart.getLayoutParams();
                layoutParams.width= (int) animation.getAnimatedValue();
                Log.d("anim","width="+layoutParams.width);
                animPart.setLayoutParams(layoutParams);
            }
        });
        widthAnim.start();
    }
}
