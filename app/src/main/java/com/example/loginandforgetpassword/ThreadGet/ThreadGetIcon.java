package com.example.loginandforgetpassword.ThreadGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadGetIcon {
    private String url;
    public ThreadGetIcon(String url){
        this.url=url;
    }
    public Bitmap getICON(){
        Bitmap bitmap=null;
        try {
            HttpURLConnection httpURLConnection=(HttpURLConnection)new URL(url).openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                InputStream is=httpURLConnection.getInputStream();
                bitmap=BitmapFactory.decodeStream(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
