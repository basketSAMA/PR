package com.example.a123.myClass;

import com.example.a123.R;

public class Genera {
    private String name;
    private boolean isLike = false;
    private int imageRes;

    public Genera(String name, boolean isLike, int imageRes) {
        this.name = name;
        this.isLike = isLike;
        this.imageRes = imageRes;
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
}
