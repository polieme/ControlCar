package com.example.daniel.common;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Daniel on 2018/6/20.
 */

public class HttpUtil {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }



    public String doPost(String url,String paramJson){
        HttpUtil httpUtil = new HttpUtil();
        String responseStr = "";
        try {
            responseStr = httpUtil.post(url,paramJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }
}
