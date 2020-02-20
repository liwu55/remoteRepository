package com.example.kingdee.something.retrofit;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.kingdee.something.JHData.JHHelper;
import com.example.kingdee.something.gson.Joke;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private Retrofit retrofit;

    @NonNull
    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(JHHelper.API_JH_URL) //设置网络请求的Url地址
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private JokeService getJokeService() {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(JokeService.class);
    }

    public void doJoke() {
        Call<ResponseBody> getJokeCall = getJokeService().getJoke(JHHelper.API_KEY, "asc", 1, 20, JHHelper.API_JOKE_TIME);
        getJokeCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Retrofit", "thread=" + Thread.currentThread());
                try {
                    String body = response.body().string();
                    Log.d("Retrofit", "body=" + body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void doJokeWithGson(final CallBack callBack) {
        Call<Joke> getJokeCall = getJokeService().getJokeGson(JHHelper.API_KEY, "asc", 1, 20, JHHelper.API_JOKE_TIME);
        getJokeCall.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                Joke joke = response.body();
                Joke.ResultBean result = joke.getResult();
                List<Joke.ResultBean.DataBean> datas = result.getData();
                StringBuilder sb = new StringBuilder();
                int index = 1;
                for (Joke.ResultBean.DataBean dataBean : datas) {
                    sb.append(index++);
                    sb.append(".");
                    sb.append(dataBean.getContent());
                    sb.append("\r\n");
                    sb.append("\r\n");
                }
                callBack.getSuc(sb.toString());
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                callBack.getFail("失败");
            }
        });
    }

    public void doJoke2() {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("key", JHHelper.API_KEY);
        queryMap.put("sort", "asc");
        queryMap.put("page", 1);
        queryMap.put("pageSize", 20);
        queryMap.put("time", JHHelper.API_JOKE_TIME);
        Call<ResponseBody> getJoke2Call = getJokeService().getJoke2(queryMap);
        getJoke2Call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Retrofit", "thread=" + Thread.currentThread());
                try {
                    String body = response.body().string();
                    Log.d("Retrofit", "body=" + body);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public interface CallBack {
        void getSuc(String joke);
        void getFail(String msg);
    }
}
