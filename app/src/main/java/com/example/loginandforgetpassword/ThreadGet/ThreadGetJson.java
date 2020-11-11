package com.example.loginandforgetpassword.ThreadGet;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ThreadGetJson {
    private String url;
    public ThreadGetJson(String url){
        this.url=url;
    }
    public JSONObject getJSON(){
        JSONObject jsonObject=null;
        try {
            Log.i("MyThread","线程开始了");
            HttpURLConnection httpURLConnection=(HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setDoInput(true);
//            httpURLConnection.setDoOutput(true);
//            httpURLConnection.setUseCaches(false);
//            httpURLConnection.setRequestProperty("Content-type","application/json");
            httpURLConnection.connect();
            int resultCode=httpURLConnection.getResponseCode();
            Log.i("resultCode",resultCode+"");
            if(resultCode==HttpURLConnection.HTTP_OK){
                InputStream is=httpURLConnection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                String s=br.readLine();
                StringBuilder sb=new StringBuilder();
                while(s!=null){
                    sb.append(s);
                    s=br.readLine();
                }
                jsonObject = new JSONObject(new String(sb));
                httpURLConnection.disconnect();
            }
            Log.i("MyThread","线程完毕了");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
