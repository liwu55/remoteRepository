package com.example.kingdee.something;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kingdee on 2016/7/5.
 */
public class FirstFragment extends Fragment {


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("fragment_","onAttach");
        if(getActivity()!=null){
            Log.d("fragment_","can get activity now!");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fragment_","onCreate");
        if(getActivity()!=null){
            Log.d("fragment_","can get activity now!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragment_","onCreateView");
        if(getActivity()!=null){
            Log.d("fragment_","can get activity now!");
        }
        TextView tv=new TextView(getActivity());
        tv.setText(this.getClass().getSimpleName());
        tv.setTextSize(40);
        tv.setTextColor(Color.GREEN);
        return tv;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d("fragment_","onViewCreated");
        if(getActivity()!=null){
            Log.d("fragment_","can get activity now!");
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d("fragment_","onStart");
        if(getActivity()!=null){
            Log.d("fragment_","can get activity now!");
        }
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("fragment_","onResume");
        if(getActivity()!=null){
            Log.d("fragment_","can get activity now!");
        }
        super.onResume();
    }
}

