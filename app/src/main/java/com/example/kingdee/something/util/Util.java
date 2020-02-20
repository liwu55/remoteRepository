package com.example.kingdee.something.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static void saveListToCache(List<String> data,Context context,String name,int mode){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            String s=data.get(i);
            sb.append(s);
            sb.append("-");
        }
        sb.replace(data.size()-1,data.size(),"");
        SharedPreferences sp=context.getSharedPreferences(name,mode);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("data",sb.toString());
        edit.commit();
    }

    public static List<String> getSaveData(Context context,String name,int mode){
        List<String> datas=new ArrayList<>();
        SharedPreferences sp=context.getSharedPreferences(name, mode);
        String data = sp.getString("data", "");
        String[] urls = data.split("-");
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            datas.add(url);
        }
        return datas;
    }
}
