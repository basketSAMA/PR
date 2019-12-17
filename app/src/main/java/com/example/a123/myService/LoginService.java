package com.example.a123.myService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.a123.R;
import com.example.a123.myClass.BitmapUtil;
import com.example.a123.myClass.User;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService {

    private static final MediaType JSON = MediaType.parse("application/json");

    public static String getCheckResult(User user, Context context) {
        String res = null;
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        String url = "http://119.23.231.17:8080/user/login";
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
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
                .addHeader("Connection","close")
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
                //连接异常
                res = "contact_error";
            }
            else if(e instanceof SocketTimeoutException) {
                //超时异常
                res = "time_out";
            }
            else if(e instanceof InterruptedIOException) {
                //自定义超时异常
                res = "time_out";
            }
        }
        return res;
    }

    public static User getUserInfo(String email, Context context) {
        String url = "http://119.23.231.17:8080/user/getInfo?email=" + email;
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection","close")
                .build();
        User user = new User();
        user.setEmail(email);
        try{
            Response response = client.newCall(request).execute();
            String jUser = response.body().string();
            Log.i("jUser", jUser);
            String[] parts = jUser.replace("\"", "").replace(" ", "").replace("\n", "")
                    .replace("{", "").replace("}", "").split(",");
            for(String part: parts) {
                Log.i("getInfo", part);
                String[] pairs = part.split(":", 2);
                if(pairs[0].equals("name"))
                    user.setName(pairs[1]);
                if(pairs[0].equals("password"))
                    user.setPassword(pairs[1]);
                if(pairs[0].equals("avatar"))
                    try{
                        user.setImageBitmap(pairs[1]);
                    } catch (Exception e) {
                        user.setImageBitmap(BitmapUtil.compressImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.d4)));
                    }
                if(pairs[0].equals("background"))
                    try{
                        user.setBackgroundBitmap(pairs[1]);
                    } catch (Exception e) {
                        user.setBackgroundBitmap(BitmapUtil.compressImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.jzl)));
                    }
                if(pairs[0].equals("signature"))
                    user.setSpace(pairs[1]);
                Log.d(pairs[0], pairs[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof ConnectException) {
                //连接异常处理
            }
            else if(e instanceof SocketTimeoutException) {
                //超时异常处理
            }
        }
        return user;
    }

    public static Bitmap getBitmap(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200) {
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
