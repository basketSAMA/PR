package com.example.a123.myClass;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.a123.myService.LoginService;

public class Family {
    private String nameC = "";
    private String nameE = "";
    private int imageRes;
    private Bitmap imageBitmap;

    public Family() {
        this.nameC = "";
        this.nameE = "";
        this.imageRes = -1;
    }

    public Family(String nameC, String nameE, int imageRes) {
        this.nameC = nameC;
        this.nameE = nameE;
        this.imageRes = imageRes;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE;
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
}
