package com.example.a123.myService;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.a123.R;
import com.example.a123.myActivity.FamilyActivity;
import com.example.a123.myClass.Like;
import com.example.a123.myClass.Plant;
import com.google.gson.Gson;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LikeService {

    private static final MediaType JSON = MediaType.parse("application/json");

    public static List<Plant> getLike(String email, Context context) {
        List<Plant> plants = new ArrayList<>();
        String url = "http://119.23.231.17:8080/like/getLikes?email=" + email;
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
//                .addHeader("Connection","close")
                .build();
        try{
            Response response = client.newCall(request).execute();
            String jUser = response.body().string();
            Log.i("jUser", jUser);
            String[] partss = jUser.split("\\},");
            for(String parts: partss) {
                parts = parts.replace(" ", "")
                        .replace("\n", "").replace("\r", "")
                        .replace("[", "").replace("]", "")
                        .replace("{", "").replace("}", "");
                Log.i("parts", parts);
                Plant plant = new Plant();
                for(String part: parts.split(",\"")) {
                    part = part.replace("\"", "");
                    String[] pairs = part.split(":", 2);
                    if(pairs.length < 2)
                        continue;
                    if(pairs[0].equals("pid"))
                        plant.setPid(pairs[1]);
                    if(pairs[0].equals("name"))
                        plant.setName(pairs[1]);
                    if(pairs[0].equals("pic"))
                        try {
                            plant.setImageBitmap(pairs[1]);
                        }catch (Exception e) {
                            plant.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.m4));
                        }
                    if(pairs[0].equals("kind"))
                        plant.setNameC(pairs[1]);
                }
                if(!plant.getPid().equals("-1"))
                    plants.add(plant);
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

        return plants;
    }

    public static boolean insertLike(String email, String pid) {
        String url = "http://119.23.231.17:8080/like/insertLike";
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(new Like(email, pid));
        RequestBody requestBody = FormBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection","close")
                .post(requestBody)
                .build();
        try{
            Response response = client.newCall(request).execute();
            return true;
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

    public static boolean deleteLike(String email, String pid) {
        String url = "http://119.23.231.17:8080/like/deleteLike";
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Gson gson = new Gson();
        String json = gson.toJson(new Like(email, pid));
        RequestBody requestBody = FormBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection","close")
                .post(requestBody)
                .build();
        try{
            Response response = client.newCall(request).execute();
            return true;
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

    public static boolean isLike(List<Plant> likeList, String pid) {
        if(pid == null)
            return false;
        for(Plant plant: likeList) {
            if(plant.getPid().equals(pid))
                return true;
        }
        return false;
    }

}
