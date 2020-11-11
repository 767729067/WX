package com.example.loginandforgetpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.loginandforgetpassword.Adapter.ChatListViewAdapter;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetIcon;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetJson;
import com.example.loginandforgetpassword.websocketclient.MyWebSocketClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_bottom_layout;
    private Button send_bottom_layout;
    private ListView list_content;
    private TextView user_name;
    private ConstraintLayout back_home;
    private String utel,myUtel,icon,myIcon;
    private Handler myHandler;
    private List<String> strList=new ArrayList<>();
    private List<JSONObject> meeeagesInfo=new ArrayList<>();
    private Bitmap myBitmap;
    private MyWebSocketClient wsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.GRAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        String name=bundle.getString("name");
        //对方的电话
        utel=bundle.getString("utel");
        //传递对方头像
        icon=bundle.getString("icon");
        Log.i("icon",icon);
        //自己的电话
        myUtel=bundle.getString("myUtel");
        //传递自己头像
        myIcon=bundle.getString("myIcon");
        Log.i("myIcon",myIcon);
        Log.i("utel",utel);
        Log.i("myUtel",myUtel);
        user_name.setText(name);
        getSendJson();
        myHandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                ChatListViewAdapter chatListViewAdapter=new ChatListViewAdapter(ChatActivity.this,meeeagesInfo,myBitmap);
                list_content.setAdapter(chatListViewAdapter);
            }
        };
    }

    private void getSendJson() {
        new Thread(){
            @Override
            public void run() {
                super.run();
//                ThreadGetJson tgj=new ThreadGetJson("http://115.29.202.70:8123/baseservice/simpleuserinfo/getuserinfobytel/"+myUtel);
//                JSONObject jsonObject=tgj.getJSON();
                initWebSocket();
            }
        }.start();
    }

    private void initWebSocket() {
//        JSONObject data=null;
//        JSONObject user_info=null;
//        try {
//            data=jsonObject.getJSONObject("data");
//            user_info=data.getJSONObject("userinfo");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        byte[] value=String.valueOf(user_info).getBytes();
//        Log.i("Base64",new String(Base64.encode(value,Base64.DEFAULT)));

        String wsuri="ws://echo.websocket.org";
//        String wsuri="ws://115.29.202.70:8123/WebSocketLink/"+ new String(Base64.encode(value,Base64.DEFAULT));
        URI uri=null;
        try {
            URL url=new URL(wsuri);
            uri=url.toURI();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        URI uri= URI.create(wsuri);
        Log.i("111","111111");
        wsc=new MyWebSocketClient(uri){
            @Override
            public void onMessage(String message) {
                super.onMessage(message);
                JSONObject other=new JSONObject();
                try {
                    other.put("isMyMsg",false);
                    other.put("message",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                meeeagesInfo.add(other);
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Message message1=Message.obtain();
                        myHandler.sendMessage(message1);
                    }
                }.start();
            }
        };
        try {
            wsc.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getIcon() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                if(myBitmap==null) {
                    //利用成员变量缓存机制
                    ThreadGetIcon t1 = new ThreadGetIcon(myIcon);
                    myBitmap = t1.getICON();
                }
                Looper.prepare();
                Message message=Message.obtain();
                myHandler.sendMessage(message);
                Looper.loop();
            }
        }.start();
    }

    private void initView() {
        et_bottom_layout=findViewById(R.id.et_bottom_layout);
        send_bottom_layout=findViewById(R.id.send_bottom_layout);
        list_content=findViewById(R.id.list_content);
        user_name=findViewById(R.id.user_name);
        back_home=findViewById(R.id.back_home);
        back_home.setOnClickListener(this);
        send_bottom_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.send_bottom_layout && !(TextUtils.isEmpty(et_bottom_layout.getText()))){
            strList.add(et_bottom_layout.getText().toString());
            wsc.send(et_bottom_layout.getText().toString());
            JSONObject myMsg=new JSONObject();
            try {
                myMsg.put("isMyMsg",true);
                myMsg.put("message",et_bottom_layout.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            meeeagesInfo.add(myMsg);
            et_bottom_layout.setText("");
            getIcon();
        }
        if(view.getId()==R.id.back_home){
            ChatActivity.this.finish();
        }
    }
}
