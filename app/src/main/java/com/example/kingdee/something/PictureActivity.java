package com.example.kingdee.something;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class PictureActivity extends AppCompatActivity {
    Bitmap b;
    private ImageView src;
    private ImageView change;
    private EditText edge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        src = (ImageView) findViewById(R.id.src);
        change = (ImageView) findViewById(R.id.change);
        edge = (EditText) findViewById(R.id.edge);
        //jd,debangwuliu,annengwuliu
        String path="https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2672600088,3931601039&fm=173&app=25&f=JPEG?w=640&h=492&s=DD0AA3577A2A569E8E247D6D0300F013";
        Glide.with(this).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                b=resource;
                src.setImageBitmap(b);
            }
        });

    }

    public void change(View v){
        String edgeStr=edge.getText().toString().trim();
        int edgeInt=0;
        try {
            edgeInt = Integer.parseInt(edgeStr);
        }catch(Exception e){
            Toast.makeText(PictureActivity.this, "临界值错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if(b==null){
            Toast.makeText(PictureActivity.this, "图片加载错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if(edgeInt<0||edgeInt>255){
            Toast.makeText(PictureActivity.this, "请输入0-255的数", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap changedB = convertToBlackWhite(b, edgeInt);
        change.setImageBitmap(changedB);
    }


    /**
     * 将彩色图转换为纯黑白二色
     *
     * @param bmp 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp,int edge) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                //分离三原色
                int srcAlpha=((grey&0xFF000000)>>24);
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                //转化成灰度像素
                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
//                Log.d("color",i+" "+j+" grey="+grey);
                if(grey>edge){
                    grey=0xFF;
                }else{
                    grey=0;
                }
                if(srcAlpha==0){
                    grey=0xFF;
                }
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        //新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //设置图片数据
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

        return newBmp;
//        return newBmp;
    }
}
