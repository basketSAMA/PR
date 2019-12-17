package com.example.a123.myService;

import android.content.Context;
import android.util.Log;

import com.example.a123.myClass.Like;
import com.example.a123.myClass.User;
import com.google.gson.Gson;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateService {

    private static final MediaType JSON = MediaType.parse("application/json");

    public static boolean updateSpace(String email, String space, Context context) {
        String url = "http://119.23.231.17:8080/user/updateSignature";
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();
        User user = new User();
        user.setEmail(email);
        user.setSpace(space);
        String json = gson.toJson(user);
        RequestBody requestBody = FormBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection","close")
                .post(requestBody)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String res = response.body().toString();
            Log.i("updateSpace", res);
            if(res.equals("update succeed"))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ConnectException) {
                //连接异常
            }
            else if(e instanceof SocketTimeoutException) {
                //超时异常
            }
            else if(e instanceof InterruptedIOException) {
                //自定义超时异常
            }
            return false;
        }
    }

}
