package com.example.a123.myClass;

public class Like {
    private String email;
    private String pid;

    public Like() {
    }

    public Like(String email, String pid) {
        this.email = email;
        this.pid = pid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
