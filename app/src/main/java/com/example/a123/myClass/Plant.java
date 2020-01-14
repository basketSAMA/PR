package com.example.a123.myClass;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.a123.myService.LoginService;

public class Plant extends Family{
    private String pid = "-1";
    private String name;
    private boolean isLike = false;
    private int imageRes;
    private Bitmap imageBitmap;
    private String detail;

    public Plant() {
        this.pid = "-1";
    }

    public Plant(String name, boolean isLike, int imageRes) {
        this.pid = "-1";
        this.name = name;
        this.isLike = isLike;
        this.imageRes = imageRes;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void setImageBitmap(final String imageUrl) throws Exception{
        Log.i("imageUrl", imageUrl);
        this.imageBitmap = BitmapUtil.compressImage(LoginService.getBitmap(imageUrl));
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
