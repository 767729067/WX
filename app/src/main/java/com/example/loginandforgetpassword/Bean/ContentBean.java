package com.example.loginandforgetpassword.Bean;

import android.graphics.Bitmap;

public class ContentBean {
    private Bitmap pic;
    private String name;
    private String meaagae;
    private String Date;
    private String utel;

    public String getUtel() {
        return utel;
    }

    public void setUtel(String utel) {
        this.utel = utel;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaagae() {
        return meaagae;
    }

    public void setMeaagae(String meaagae) {
        this.meaagae = meaagae;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
