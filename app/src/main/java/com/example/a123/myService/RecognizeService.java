package com.example.a123.myService;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.a123.R;
import com.example.a123.myClass.Plant;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecognizeService {

//    private static final MediaType IMAGE = MediaType.parse("multipart/form-data; boundary=--letv");
//
//    public static Plant uploadImage(String imagePath){
//        Plant plant = new Plant();
//        String url = "http://119.23.231.17:8080/Recognitation/PlantIdentification";
//        OkHttpClient client = new OkHttpClient.Builder()
//                .callTimeout(500, TimeUnit.SECONDS)
//                .connectTimeout(500, TimeUnit.SECONDS)
//                .readTimeout(500, TimeUnit.SECONDS)
//                .writeTimeout(500, TimeUnit.SECONDS)
//                .build();
//        Log.i("imagePath", imagePath);
//        File file = new File(imagePath);
//        RequestBody image = RequestBody.create(file, IMAGE);
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", imagePath, image)
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                //.addHeader("Connection","close")
//                .post(requestBody)
//                .build();
//        try{
//            Response response = client.newCall(request).execute();
//            String jUser = response.body().string();
//            Log.i("jUser", jUser);
//            String[] parts = jUser.replace("\"", "").replace(" ", "").replace("\n", "")
//                    .replace("{", "").replace("}", "").split(",");
//            for(String part: parts) {
//                Log.d("getInfo", part);
//                String[] pairs = part.split(":", 2);
//                if(pairs[0].equals("pic"))
//                    plant.setImageUrl(pairs[1]);
//                if(pairs[0].equals("name"))
//                    plant.setName(pairs[1]);
//                Log.i(pairs[0], pairs[1]);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            if(e instanceof ConnectException) {
//                //连接异常
//            }
//            else if(e instanceof SocketTimeoutException) {
//                //超时异常
//            }
//        }
//        return plant;
//    }

    private static final int TIME_OUT = 100000000;
    private static final String CHARSET = "utf-8";

    public static Plant recognizeByImage(String imagePath, Context context) {
        Plant plant = new Plant();
        File file = new File(imagePath);
        String urlStr="http://119.23.231.17:8080/Recognitation/PlantIdentification";

        Boolean result = false;
        String BOUNDARY = "letv";
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);

            if (file != null) {
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);

                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());

                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024 * 1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();

                int res = conn.getResponseCode();

                if (res == 200) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder getresult = new StringBuilder();
                    String line;
                    while((line = input.readLine()) != null)
                    {
                        getresult.append(line).append("\n");
                    }
                    String jUser = getresult.toString();
                    Log.i("jUser", jUser);
                    String[] parts = jUser.replace("\"", "").replace(" ", "").replace("\n", "")
                            .replace("{", "").replace("}", "").split(",");
                    for(String part: parts) {
                        Log.d("getInfo", part);
                        String[] pairs = part.split(":", 2);
                        if (pairs[0].equals("pic"))
                            try {
                                plant.setImageBitmap(pairs[1]);
                            }catch (Exception e) {
                                plant.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.m4));
                            }
                        if (pairs[0].equals("name"))
                            plant.setName(pairs[1]);
                        Log.i(pairs[0], pairs[1]);
                    }
                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return plant;
    }
}
