package com.example.a123.myClass;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.a123.myService.LoginService;

public class User {
    private String password;
    private String name;
    private String email;
    private Bitmap imageBitmap;
    private Bitmap backgroundBitmap;
    private String space;

    private String signature;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public void setImageBitmap(String imageUrl) throws Exception{
        Log.i("imageUrl", imageUrl);
        this.imageBitmap = BitmapUtil.compressImage(LoginService.getBitmap(imageUrl));
    }

    public Bitmap getBackgroundBitmap() {
        return backgroundBitmap;
    }

    public void setBackgroundBitmap(Bitmap backgroundBitmap) {
        this.backgroundBitmap = backgroundBitmap;
    }

    public void setBackgroundBitmap(String imageBackgroundUrl) throws Exception{
        Log.i("imageBackgroundUrl", imageBackgroundUrl);
        this.imageBitmap = BitmapUtil.compressImage(LoginService.getBitmap(imageBackgroundUrl));
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
        this.signature = space;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
        this.space = signature;
    }
}
