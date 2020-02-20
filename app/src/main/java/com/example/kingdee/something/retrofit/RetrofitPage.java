package com.example.kingdee.something.retrofit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kingdee.something.R;

public class RetrofitPage extends AppCompatActivity {

    private TextView jokeShow;
    private View getDataView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retorfit_page);
        getDataView = findViewById(R.id.page_retrofit_get_data);
        jokeShow = (TextView) findViewById(R.id.page_retrofit_joke_show);
        assert getDataView != null;
        getDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressShow("正在获取笑话...");
                RetrofitHelper retrofitHelper = new RetrofitHelper();
//                retrofitHelper.doJoke();
//                retrofitHelper.doJoke2();
                retrofitHelper.doJokeWithGson(new RetrofitHelper.CallBack() {
                    @Override
                    public void getSuc(String joke) {
                        progressHide();
                        jokeShow.setText(joke);
                    }

                    @Override
                    public void getFail(String msg) {
                        progressHide();
                        jokeShow.setText(msg);
                    }
                });
            }
        });

    }

    private void progressHide() {
        if(progressDialog!=null&&progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void progressShow(String show) {
        if(progressDialog ==null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(show);
        progressDialog.show();
    }
}
