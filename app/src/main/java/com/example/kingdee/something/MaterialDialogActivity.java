package com.example.kingdee.something;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import me.drakeet.materialdialog.MaterialDialog;

public class MaterialDialogActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_dialog);
        findViewById(R.id.show).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.show:
//                Toast.makeText(this,"show dialog",Toast.LENGTH_SHORT).show();
                showMaterialDialog();
                break;
        }
    }

    MaterialDialog dialog=new MaterialDialog(this);
    private void showMaterialDialog() {
        dialog.setTitle("title").setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MaterialDialogActivity.this,"点击了取消",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MaterialDialogActivity.this,"点击了确定",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).setMessage("This is a MaterialDialog~").show();
    }
}
