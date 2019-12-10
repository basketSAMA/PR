package com.example.a123.myService;

import com.example.a123.myClass.User;
import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService {

    private static final MediaType JSON = MediaType.parse("application/json");

    public static String getCheckResult(User user) {
        String res = null;
        String url = "http://119.23.231.17:8080/user/login";
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        RequestBody requestBody = FormBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
//        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
//        formBody.add("email", user.getEmail());
//        formBody.add("password", user.getPassword());
//        Request request = new Request.Builder()
//                .url(url)
//                .post(formBody.build())
//                .build();
        //异步
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException{
//                if(response.isSuccessful()) {
//                    res = response.body().string();
//                    Log.d("test", res);
//                }
//            }
//        });
        //同步
        try{
            Response response = client.newCall(request).execute();
            res = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            res = "e";
            if(e instanceof ConnectException) {
                //连接异常处理
                res = "contact_error";
            }
            else if(e instanceof SocketTimeoutException) {
                //超时异常处理
                res = "time_out";
            }
        }
        return res;
    }
}
