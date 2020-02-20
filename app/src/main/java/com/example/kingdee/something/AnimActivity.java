package com.example.kingdee.something;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimActivity extends AppCompatActivity {

    private View animObj;
    private View animDo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        animDo = findViewById(R.id.activity_anim_anim_do);
        animObj = findViewById(R.id.activity_anim_anim_obj);
        animDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //anim();
                anim2();
            }
        });

    }

    private void anim2() {
        ObjectAnimator ta = ObjectAnimator.ofObject(animObj, "translationX", new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                return null;
            }
        }, 0, 100);
        ta.setDuration(600);
        ta.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return 2*input;
            }
        });
        ta.start();
    }

    private void anim() {

        TranslateAnimation ta=new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,1,
                Animation.RELATIVE_TO_SELF,0,
                Animation.RELATIVE_TO_SELF,1
        );
        ta.setDuration(600);
        ta.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                return 0;
            }
        });
        animObj.startAnimation(ta);
    }
}
