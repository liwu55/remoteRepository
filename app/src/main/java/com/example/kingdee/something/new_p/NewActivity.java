package com.example.kingdee.something.new_p;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.kingdee.something.AnimActivity;
import com.example.kingdee.something.BallActivity;
import com.example.kingdee.something.BarcodeActivity;
import com.example.kingdee.something.BigActivity;
import com.example.kingdee.something.CubeActivity;
import com.example.kingdee.something.DataBindTestActivity;
import com.example.kingdee.something.DialogActivity;
import com.example.kingdee.something.DragonActivity;
import com.example.kingdee.something.EatPage;
import com.example.kingdee.something.EditActivity;
import com.example.kingdee.something.FireWorkActivity;
import com.example.kingdee.something.FireWorkActivity2;
import com.example.kingdee.something.GameActivity;
import com.example.kingdee.something.HowMuchActivity;
import com.example.kingdee.something.KTTest;
import com.example.kingdee.something.MSKPage;
import com.example.kingdee.something.MaterialDialogActivity;
import com.example.kingdee.something.MatrixActivity;
import com.example.kingdee.something.MessageActivity;
import com.example.kingdee.something.HandlerPage;
import com.example.kingdee.something.MultiPoiontActivity;
import com.example.kingdee.something.MyFragmentActivity;
import com.example.kingdee.something.MyViewActivity;
import com.example.kingdee.something.OpenGLActivity;
import com.example.kingdee.something.PictureActivity;
import com.example.kingdee.something.R;
import com.example.kingdee.something.RecycleViewActivity;
import com.example.kingdee.something.ReflectActivity;
import com.example.kingdee.something.ReflectBallActivity;
import com.example.kingdee.something.anim.AnimPage;
import com.example.kingdee.something.retrofit.RetrofitPage;
import com.example.kingdee.something.SandGlassViewActivity;
import com.example.kingdee.something.SaveActivity;
import com.example.kingdee.something.ScrollActivity;
import com.example.kingdee.something.SensorActivity;
import com.example.kingdee.something.SoftKeyBoardActivity;
import com.example.kingdee.something.SpeakActivity;
import com.example.kingdee.something.SurfaceActivity;
import com.example.kingdee.something.SwipeMenuListViewActivity;
import com.example.kingdee.something.TabHostActivity;
import com.example.kingdee.something.ThreeListViewActivity;
import com.example.kingdee.something.TouchEventActivity;
import com.example.kingdee.something.WaveActivity;
import com.example.kingdee.something.WhatActivity;
import com.example.kingdee.something.ZrcActivity;
import com.example.kingdee.something.util.ToggleLog;
import com.example.kingdee.something.widget.meteor.MeteorActivity;
import com.liwu.lodoptrans.BlueToothTranslater;
import com.liwu.lodoptrans.LODOPTranslater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NewActivity extends Activity {

    private AutoNextContainer autoNextContainer;
    private static final int TYPE_LODOP_TO_BT = 0;
    private static final int TYPE_BT_TO_LODOP = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        autoNextContainer = (AutoNextContainer) findViewById(R.id.auto_next_container);
        autoNextContainer.addButton("ZrcListView");
        autoNextContainer.addButton("MateriaDialog");
        autoNextContainer.addButton("SwipeMenuListView");
        autoNextContainer.addButton("TabHost");
        autoNextContainer.addButton("SoftKeyBoard");
        autoNextContainer.addButton("Dialog");
        autoNextContainer.addButton("Wave");
        autoNextContainer.addButton("RecyclerView");
        autoNextContainer.addButton("ThreeOpen");
        autoNextContainer.addButton("SandGlassView");
        autoNextContainer.addButton("Sensor");
        autoNextContainer.addButton("Speak");
        autoNextContainer.addButton("Edit");
        autoNextContainer.addButton("Message");
        autoNextContainer.addButton("Save");
        autoNextContainer.addButton("Reflect");
        autoNextContainer.addButton("Fragment");
        autoNextContainer.addButton("Barcode");
        autoNextContainer.addButton("Scroll");
        autoNextContainer.addButton("MultiPoint");
        autoNextContainer.addButton("Game");
        autoNextContainer.addButton("Surface");
        autoNextContainer.addButton("Picture");
        autoNextContainer.addButton("Cube");
        autoNextContainer.addButton("HowMuch");
        autoNextContainer.addButton("Ball");
        autoNextContainer.addButton("What");
        autoNextContainer.addButton("Matrix");
        autoNextContainer.addButton("MyView");
        autoNextContainer.addButton("Dragon");
        autoNextContainer.addButton("OpenGL");
        autoNextContainer.addButton("TouchEvent");
        autoNextContainer.addButton("FireWork");
        autoNextContainer.addButton("FireWork2");
        autoNextContainer.addButton("ReflectBall");
        autoNextContainer.addButton("大写");
        autoNextContainer.addButton("LODOPTrans");
        autoNextContainer.addButton("BTTrans");
        autoNextContainer.addButton("Meteor");
        autoNextContainer.addButton("Kotlin");
        autoNextContainer.addButton("Eat");
        autoNextContainer.addButton("MSK");
        autoNextContainer.addButton("handler");
        autoNextContainer.addButton("dataBinding");
        autoNextContainer.addButton("animation");
        autoNextContainer.addButton("Retrofit");
        autoNextContainer.addButton("anim");
        autoNextContainer.setButtonClickListener(new AutoNextContainer.ButtonClickListener() {
            @Override
            public void buttonClick(String buttonText) {
                switch (buttonText) {
                    case "ZrcListView":
                        Intent zrcIntent = new Intent(NewActivity.this, ZrcActivity.class);
                        startActivity(zrcIntent);
                        break;
                    case "MateriaDialog":
                        Intent mdIntent = new Intent(NewActivity.this, MaterialDialogActivity.class);
                        startActivity(mdIntent);
                        break;
                    case "SwipeMenuListView":
                        Intent smlvIntent = new Intent(NewActivity.this, SwipeMenuListViewActivity.class);
                        startActivity(smlvIntent);
                        break;
                    case "TabHost":
                        Intent thIntent = new Intent(NewActivity.this, TabHostActivity.class);
                        startActivity(thIntent);
                        break;
                    case "SoftKeyBoard":
                        Intent skbIntent = new Intent(NewActivity.this, SoftKeyBoardActivity.class);
                        startActivity(skbIntent);
                        break;
                    case "Dialog":
                        Intent dialogIntent = new Intent(NewActivity.this, DialogActivity.class);
                        startActivity(dialogIntent);
                        break;
                    case "Wave":
                        Intent waveIntent = new Intent(NewActivity.this, WaveActivity.class);
                        startActivity(waveIntent);
                        break;
                    case "RecyclerView":
                        Intent recycleViewIntent = new Intent(NewActivity.this, RecycleViewActivity.class);
                        startActivity(recycleViewIntent);
                        break;
                    case "ThreeOpen":
                        Intent threeListViewIntent = new Intent(NewActivity.this, ThreeListViewActivity.class);
                        startActivity(threeListViewIntent);
                        break;
                    case "SandGlassView":
                        Intent SandGlassViewIntent = new Intent(NewActivity.this, SandGlassViewActivity.class);
                        startActivity(SandGlassViewIntent);
                        break;
                    case "Sensor":
                        Intent SensorIntent = new Intent(NewActivity.this, SensorActivity.class);
                        startActivity(SensorIntent);
                        break;
                    case "Speak":
                        Intent SpeakIntent = new Intent(NewActivity.this, SpeakActivity.class);
                        startActivity(SpeakIntent);
                        break;
                    case "Edit":
                        Intent EditIntent = new Intent(NewActivity.this, EditActivity.class);
                        startActivity(EditIntent);
                        break;
                    case "Message":
                        Intent MessageIntent = new Intent(NewActivity.this, MessageActivity.class);
                        startActivity(MessageIntent);
                        break;
                    case "Save":
                        Intent SaveIntent = new Intent(NewActivity.this, SaveActivity.class);
                        startActivity(SaveIntent);
                        break;
                    case "Reflect":
                        Intent reflectActivity = new Intent(NewActivity.this, ReflectActivity.class);
                        startActivity(reflectActivity);
                        break;
                    case "Fragment":
                        Intent myFragmentActivity = new Intent(NewActivity.this, MyFragmentActivity.class);
                        startActivity(myFragmentActivity);
                        break;
                    case "Barcode":
                        Intent barcodeActivity = new Intent(NewActivity.this, BarcodeActivity.class);
                        startActivity(barcodeActivity);
                        break;
                    case "Scroll":
                        Intent scrollActivity = new Intent(NewActivity.this, ScrollActivity.class);
                        startActivity(scrollActivity);
                        break;
                    case "MultiPoint":
                        Intent multiPoiontActivity = new Intent(NewActivity.this, MultiPoiontActivity.class);
                        startActivity(multiPoiontActivity);
                        break;
                    case "Game":
                        Intent gameActivity = new Intent(NewActivity.this, GameActivity.class);
                        startActivity(gameActivity);
                        break;
                    case "Surface":
                        Intent surfaceActivity = new Intent(NewActivity.this, SurfaceActivity.class);
                        startActivity(surfaceActivity);
                        break;
                    case "Picture":
                        Intent pictureActivity = new Intent(NewActivity.this, PictureActivity.class);
                        startActivity(pictureActivity);
                        break;
                    case "Cube":
                        Intent cubeActivity = new Intent(NewActivity.this, CubeActivity.class);
                        startActivity(cubeActivity);
                        break;
                    case "HowMuch":
                        Intent howMuchActivity = new Intent(NewActivity.this, HowMuchActivity.class);
                        startActivity(howMuchActivity);
                        break;
                    case "Ball":
                        Intent ballActivity = new Intent(NewActivity.this, BallActivity.class);
                        startActivity(ballActivity);
                        break;
                    case "What":
                        Intent whatActivity = new Intent(NewActivity.this, WhatActivity.class);
                        startActivity(whatActivity);
                        break;
                    case "Matrix":
                        Intent matrixIntent = new Intent(NewActivity.this, MatrixActivity.class);
                        startActivity(matrixIntent);
                        break;
                    case "MyView":
                        Intent myViewIntent = new Intent(NewActivity.this, MyViewActivity.class);
                        startActivity(myViewIntent);
                        break;
                    case "Dragon":
                        Intent dragonIntent = new Intent(NewActivity.this, DragonActivity.class);
                        startActivity(dragonIntent);
                        break;
                    case "OpenGL":
                        Intent openGLIntent = new Intent(NewActivity.this, OpenGLActivity.class);
                        startActivity(openGLIntent);
                        break;
                    case "TouchEvent":
                        Intent touchEventIntent = new Intent(NewActivity.this, TouchEventActivity.class);
                        startActivity(touchEventIntent);
                        break;
                    case "FireWork":
                        Intent fireWorkIntent = new Intent(NewActivity.this, FireWorkActivity.class);
                        startActivity(fireWorkIntent);
                        break;
                    case "FireWork2":
                        Intent fireWorkIntent2 = new Intent(NewActivity.this, FireWorkActivity2.class);
                        startActivity(fireWorkIntent2);
                        break;
                    case "ReflectBall":
                        Intent reflectBallIntent = new Intent(NewActivity.this, ReflectBallActivity.class);
                        startActivity(reflectBallIntent);
                        break;
                    case "大写":
                        Intent bigIntent = new Intent(NewActivity.this, BigActivity.class);
                        startActivity(bigIntent);
                        break;
                    case "LODOPTrans":
                        trans(TYPE_LODOP_TO_BT);
                        break;
                    case "BTTrans":
                        trans(TYPE_BT_TO_LODOP);
                        break;
                    case "Meteor":
                        Intent meteorIntent = new Intent(NewActivity.this, MeteorActivity.class);
                        startActivity(meteorIntent);
                        break;
                    case "Kotlin":
                        Intent ktIntent = new Intent(NewActivity.this, KTTest.class);
                        startActivity(ktIntent);
                        break;
                    case "Eat":
                        Intent eatIntent = new Intent(NewActivity.this, EatPage.class);
                        startActivity(eatIntent);
                        break;
                    case "MSK":
                        Intent mskIntent = new Intent(NewActivity.this, MSKPage.class);
                        startActivity(mskIntent);
                        break;
                    case "handler":
                        Intent hanlderIntent = new Intent(NewActivity.this, HandlerPage.class);
                        startActivity(hanlderIntent);
                        break;
                    case "dataBinding":
                        Intent dataBindingIntent = new Intent(NewActivity.this, DataBindTestActivity.class);
                        startActivity(dataBindingIntent);
                        break;
                    case "animation":
                        Intent animIntent = new Intent(NewActivity.this, AnimActivity.class);
                        startActivity(animIntent);
                        break;
                    case "Retrofit":
                        Intent retorfitIntent = new Intent(NewActivity.this, RetrofitPage.class);
                        startActivity(retorfitIntent);
                        break;
                    case "anim":
                        Intent AnimIntent = new Intent(NewActivity.this, AnimPage.class);
                        startActivity(AnimIntent);
                        break;
                }
            }
        });
    }

    private void trans(int type) {
        String source = null;
        switch (type) {
            case TYPE_LODOP_TO_BT:
                source = readFileContent("lodopSource.txt");
                break;
            case TYPE_BT_TO_LODOP:
                source = readFileContent("BTSource.txt");
                break;
        }
        if (source == null) {
            Toast.makeText(this, "读取内容为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String blueToothTemplate = null;
        switch (type) {
            case TYPE_LODOP_TO_BT:
                blueToothTemplate = new LODOPTranslater().translate(source, true, null, null, null, true);
                break;
            case TYPE_BT_TO_LODOP:
                blueToothTemplate = new BlueToothTranslater().translateToLODOP(source);
                break;
        }
        if (blueToothTemplate == null) {
            ToggleLog.d("trans", "return null");
            Toast.makeText(this, "转换出错", Toast.LENGTH_SHORT).show();
            return;
        }
        //全部写入
        BufferedWriter bw = null;
        try {
            String writePath = Environment.getExternalStorageDirectory() + "/template";
            File writeFile = null;
            switch (type) {
                case TYPE_LODOP_TO_BT:
                    writeFile = new File(writePath, "lodop2BT.txt");
                    break;
                case TYPE_BT_TO_LODOP:
                    writeFile = new File(writePath, "BT2LODOP.txt");
                    break;
            }
            if (!writeFile.exists()) {
                writeFile.createNewFile();
            } else {
                writeFile.delete();
                writeFile.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(writeFile));
            bw.write(blueToothTemplate);
            bw.flush();
            Toast.makeText(this, "转换完成", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "转换出错", Toast.LENGTH_SHORT).show();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String readFileContent(String fileName) {
        BufferedReader br = null;
        try {
            String readPath = Environment.getExternalStorageDirectory() + "/template";
            File file = new File(readPath, fileName);
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String readString;
            while ((readString = br.readLine()) != null) {
                sb.append(readString);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "找不到文件", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
