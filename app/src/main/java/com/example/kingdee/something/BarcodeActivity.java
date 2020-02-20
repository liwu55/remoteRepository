package com.example.kingdee.something;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public class BarcodeActivity extends AppCompatActivity {

    private ImageView barcode;
    private EditText mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        mNumber = (EditText) findViewById(R.id.number);
        barcode = (ImageView) findViewById(R.id.barcode);

    }

    public void get(View v) {
        try {
            Bitmap bitmap = null;
            String number = mNumber.getText().toString().trim();
            bitmap = generateBarCode(number);
            barcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap generateBarCode(String data){
        com.google.zxing.Writer c9 = new Code128Writer();
        Bitmap mBitmap = null;
        try {
            BitMatrix bm = c9.encode(data, BarcodeFormat.CODE_128, 500, 250);
            mBitmap = Bitmap.createBitmap(500, 250, Bitmap.Config.ARGB_8888);

            for (int i = 0; i < 500; i++) {
                for (int j = 0; j < 250; j++) {

                    mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return mBitmap;
    }
}
