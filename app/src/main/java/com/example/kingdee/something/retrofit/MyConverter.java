package com.example.kingdee.something.retrofit;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyConverter implements Converter<ResponseBody,String> {

    @Override
    public String convert(ResponseBody value) throws IOException {
        return null;
    }
}
