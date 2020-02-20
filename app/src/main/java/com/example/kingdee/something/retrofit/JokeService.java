package com.example.kingdee.something.retrofit;

import com.example.kingdee.something.JHData.JHHelper;
import com.example.kingdee.something.gson.Joke;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JokeService {

    @GET(JHHelper.PATH_JOKE)
    Call<ResponseBody> getJoke(
            @Query(JHHelper.QUERY_KEY) String key,
            @Query(JHHelper.QUERY_SORT) String sort,
            @Query(JHHelper.QUERY_PAGE) int page,
            @Query(JHHelper.QUERY_PAGE_SIZE) int pageSize,
            @Query(JHHelper.QUERY_TIME) String time
    );

    @GET(JHHelper.PATH_JOKE)
    Call<ResponseBody> getJoke2(
            @QueryMap Map<String, Object> map
    );

    @GET(JHHelper.PATH_JOKE)
    Call<Joke> getJokeGson(
            @Query(JHHelper.QUERY_KEY) String key,
            @Query(JHHelper.QUERY_SORT) String sort,
            @Query(JHHelper.QUERY_PAGE) int page,
            @Query(JHHelper.QUERY_PAGE_SIZE) int pageSize,
            @Query(JHHelper.QUERY_TIME) String time
    );
}
