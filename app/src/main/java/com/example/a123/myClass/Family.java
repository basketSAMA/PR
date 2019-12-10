package com.example.a123.myClass;

public class Family {
    private String nameC;
    private String nameE;
    private int imageRes;

    public Family() {
        this.nameC = null;
        this.nameE = null;
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
}
