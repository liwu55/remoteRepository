package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.kingdee.something.widget.MyEditDialog;

import java.lang.reflect.Field;

public class EditActivity extends AppCompatActivity {

    private MyEditDialog mNewEditDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        findViewById(R.id.showdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewEditDialog = new MyEditDialog(EditActivity.this);
                    mNewEditDialog.setOnButtonClickListener(new MyEditDialog.OnButtonClickListener() {
                        @Override
                        public void onCalcelClick() {
                        }

                        @Override
                        public void onEnsureClick() {
                        }
                    });
                mNewEditDialog.setNumber("13512345678");
                mNewEditDialog.clearChanging();
                mNewEditDialog.setIsInput(false);
                mNewEditDialog.show();
            }
        });
    }
}
