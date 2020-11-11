package com.example.loginandforgetpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandforgetpassword.ThreadGet.ThreadGetJson;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName;
    private EditText passWord;
    private Button btn_login;
    private ObjectAnimator oa;
    private TextView tv_forget;
    private ConstraintLayout cl_qq,cl_weChat,cl_phoneNumber;
    private Handler mhandler;
    private CheckBox cb_remember;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);
        initView();
        setUserNameDrawableSize();
        setPassWordDrawableSize();
        setButtonAnimation();
        getInfo();
        sp=getSharedPreferences("userInfo",MODE_PRIVATE);
        userName.setText(sp.getString("userName", ""));
        passWord.setText(sp.getString("passWord", ""));
        if(!(sp.getString("userName","").isEmpty())){
            cb_remember.setChecked(true);
        }
    }

    private void getInfo() {
        mhandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle=msg.getData();
                String tel=bundle.getString("tel");
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("tel",tel);
                startActivity(intent);
                MainActivity.this.finish();
            }
        };
    }

    //自定义按钮动画
    private void setButtonAnimation() {
        oa=ObjectAnimator.ofFloat(btn_login,"scaleX",1.01f,1);
        oa.setDuration(200);
        btn_login.setOnClickListener(this);
    }
//设置密码的图标大小
    private void setPassWordDrawableSize() {
        Drawable drawable=getResources().getDrawable(R.drawable.password);
        drawable.setBounds(0,0,80,80);
        passWord.setCompoundDrawables(drawable,null,null,null);
        passWord.setCompoundDrawablePadding(10);
    }
//设置账号图标的大小
    private void setUserNameDrawableSize() {
        Drawable drawable=getResources().getDrawable(R.drawable.username);
        drawable.setBounds(0,0,80,80);
        userName.setCompoundDrawables(drawable,null,null,null);
        userName.setCompoundDrawablePadding(10);

    }
//初始化控件
    private void initView() {
        cb_remember=findViewById(R.id.cb_remember);
        cl_qq=findViewById(R.id.cl_qq);
        cl_weChat=findViewById(R.id.cl_weChat);
        cl_phoneNumber=findViewById(R.id.cl_phoneNumber);
        cl_weChat.setOnClickListener(this);
        cl_phoneNumber.setOnClickListener(this);
        cl_qq.setOnClickListener(this);
        userName=findViewById(R.id.userName);
        passWord=findViewById(R.id.passWord);
        btn_login=findViewById(R.id.btn_login);
        tv_forget=findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);
    }
//按钮点击事件动画开始
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_login){
            oa.start();
            Toast.makeText(MainActivity.this,"登录中 请稍后...",Toast.LENGTH_SHORT).show();
            //记住密码
            SharedPreferences.Editor editor=sp.edit();
            if(cb_remember.isChecked()){

                editor.putString("userName",userName.getText().toString());
                editor.putString("passWord",passWord.getText().toString());
                editor.commit();
            }
            else {
                editor.putString("userName","");
                editor.putString("passWord","");
                editor.commit();
            }
        //网络数据访问测试
            final Thread thread=new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("utel",userName.getText().toString().trim());
                        jsonObject.put("upassword",passWord.getText().toString().trim());
                        String url="http://115.29.202.70:8123/baseservice/simpleuserinfo/userLogin";
                        HttpURLConnection urlConn=(HttpURLConnection)new URL(url).openConnection();
                        urlConn.setDoOutput(true);
                        urlConn.setDoInput(true);
                        urlConn.setUseCaches(false);
                        urlConn.setRequestMethod("POST");
                        urlConn.setRequestProperty("Content-type","application/json");
                        //向数据流中写请求参数
                        urlConn.getOutputStream().write(String.valueOf(jsonObject).getBytes());//先要获取到输出流 在把参数写到获取到的输出流中 传递参数;
//                        DataOutputStream dos=new DataOutputStream(urlConn.getOutputStream());
//                        dos.writeBytes("");
//                        dos.flush();
//                        dos.close();
                        //获得数据流，读取服务器相应数据
                        //responseCode==HttpURLConnection.HTTP_OK
                        int responseCode=urlConn.getResponseCode();
                        Log.i("resp",responseCode+"");
                        if(responseCode==HttpURLConnection.HTTP_OK){
                            InputStream is=urlConn.getInputStream();
                            InputStreamReader isr=new InputStreamReader(is);
                            BufferedReader br=new BufferedReader(isr);
                            String s=br.readLine();
                            StringBuilder builder=new StringBuilder();
//                            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//                            int n=0;
//                            byte[] b=new byte[1024];
//                            while((n=is.read(b))!=-1){
//                                baos.write(b,0,n);
//                            }
                            while(s!=null){
                                builder.append(s);
                                s=br.readLine();
                            }
//                            String str=baos.toString("UTF-8");
                            is.close();
                            isr.close();
                            br.close();
                            String value=new String(builder);
                            Log.i("value",value);
//                            baos.close();
                            urlConn.disconnect();
                            Bundle bundle=new Bundle();
                            bundle.putString("tel",userName.getText().toString().trim());
                            Message message=new Message();
                            message.setData(bundle);
                            mhandler.sendMessage(message);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                        }
                        urlConn.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {

                    }
                }
            };
            thread.start();
        }
        if(view.getId()==R.id.tv_forget){
            Intent intent=new Intent(this,ForgetPassWordActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.cl_qq){
            Toast.makeText(this,"点击了qq",Toast.LENGTH_SHORT).show();
        }
        if(view.getId()==R.id.cl_weChat){
            Toast.makeText(this,"点击了weChat",Toast.LENGTH_SHORT).show();
        }
        if (view.getId()==R.id.cl_phoneNumber){
            Toast.makeText(this,"点击了phone",Toast.LENGTH_SHORT).show();
        }
    }
}
