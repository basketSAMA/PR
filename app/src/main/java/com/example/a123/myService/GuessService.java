package com.example.a123.myService;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.a123.R;
import com.example.a123.myActivity.FamilyActivity;
import com.example.a123.myClass.Plant;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuessService {

    public static List<Plant> getGuessLike(String email, int count, Context context) {
        List<Plant> plants = new ArrayList<>();
        String url = "http://119.23.231.17:8080/history/getRecommend?email=" + email + "&count=" + count;
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
            String[] partss = jUser.split("\\},", count);
            for(String parts: partss) {
                parts = parts.replace(" ", "")
                        .replace("\n", "").replace("\r", "")
                        .replace("[", "").replace("]", "")
                        .replace("{", "").replace("}", "");
                Log.i("parts", parts);
                Plant plant = new Plant();
                for(String part: parts.split(",")) {
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
                    plant.setLike(LikeService.isLike(FamilyActivity.userLikeList, plant.getPid()));
                }
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

}
