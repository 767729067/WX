package com.example.loginandforgetpassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginandforgetpassword.Adapter.AddFriendAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.lang.reflect.Method;

public class AddFriendsActivity extends AppCompatActivity implements View.OnClickListener {
    private ConstraintLayout back_home;//用来返回的按钮
    private EditText search_friend_byTel;//填写要找寻的好友账号
    private Button search;// 查询按钮
    private ConstraintLayout search_for_result;//点击查找按钮之后返回的好友的布局
    private ImageView search_friend_icon;//点击查找按钮之后返回的好友的头像
    private TextView search_friend_name;//点击查找按钮之后返回的好友的昵称
    private TextView search_friend_lastDynamic;//点击查找按钮之后返回的好友的最后一条动态的内容
    private Button add_bt;//点击添加的按钮
    private ListView request_friends;//请求添加的好友列表
    private RequestQueue requestQueue;
    private final String friendHead="http://115.29.202.70:8123/baseservice/simplefriends/";
    private final String dynamicHead="http://115.29.202.70:8123/baseservice/simple-dynamic-details/findFriendsDetails/";
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(Color.GRAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        initView();
        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");
        requestQueue= Volley.newRequestQueue(AddFriendsActivity.this);
        loadRequsetAddFriend(uid);
    }

    private void initView() {
        back_home=findViewById(R.id.back_home);
        back_home.setOnClickListener(this);
        search_friend_byTel=findViewById(R.id.search_friend_byTel);
        search=findViewById(R.id.search);
        search.setOnClickListener(this);
        search_for_result=findViewById(R.id.search_for_result);
        search_friend_icon=findViewById(R.id.search_friend_icon);
        search_friend_name=findViewById(R.id.search_friend_name);
        search_friend_lastDynamic=findViewById(R.id.search_friend_lastDynamic);
        add_bt=findViewById(R.id.add_bt);
        request_friends=findViewById(R.id.request_friends);
    }
    //打开页面请求 需要同意的好友的请求列表
    private void loadRequsetAddFriend(final String uid){
        JsonObjectRequest addFriendRequestForMe=new JsonObjectRequest(friendHead + "selectNoAgree/" + uid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        JSONArray friendsRequest=response.getJSONObject("data").getJSONArray("friends");
                        Log.i("friendsRequest",String.valueOf(friendsRequest));
                        AddFriendAdapter addFriendAdapter=new AddFriendAdapter(AddFriendsActivity.this,friendsRequest,uid);
                        request_friends.setAdapter(addFriendAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(addFriendRequestForMe);
    }
    //点击添加按钮发送加好友请求
    private void requestAddFrind(String uid,String utel) throws JSONException {
        JSONObject pra=new JSONObject();
        pra.put("uid",uid);
        pra.put("utel",utel);
        JsonObjectRequest requestAddFrind = new JsonObjectRequest(Request.Method.POST, friendHead+"saveFriend", pra, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(AddFriendsActivity.this,"你向对方发送了申请",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddFriendsActivity.this,"你已经发送过请求,请等待对方同意",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(requestAddFrind);
    }
    //获取查询好友的最后一条动态
    private void setDynamicText(String uid){
        JsonObjectRequest jsonDynamicText=new JsonObjectRequest(dynamicHead + uid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        JSONArray dynimicArray=response.getJSONObject("data").getJSONArray("detail");
                        JSONObject lastDynamic=dynimicArray.getJSONObject(0);
                        if(dynimicArray.length()>0) {
                            search_friend_lastDynamic.setText(Jsoup.parse(lastDynamic.getString("dynamicContent")).body().text());
                        }else {
                            search_friend_lastDynamic.setText("这个家伙很懒没有留下他的动态");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonDynamicText);
    }
    //用get请求来获取查询好友的图片
    private void setIcon(JSONObject friendInfo) throws JSONException {
        ImageRequest imageRequest=new ImageRequest(friendInfo.getString("uavatar"), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                search_friend_icon.setImageBitmap(response);
            }
        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(imageRequest);
    }
    //用uid+好友账号来获取好友信息
    private void searchFriendIofo(final String uid){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("uid",uid);
            jsonObject.put("utel",utel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonRequest=new JsonObjectRequest(Request.Method.POST,friendHead+"check", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success")){
                        //成功才显示result布局
                        search_for_result.setVisibility(View.VISIBLE);
                        JSONObject friendInfo=response.getJSONObject("data").getJSONObject("friend");
                        Log.i("friendinfo",String.valueOf(friendInfo));
                        search_friend_name.setText(friendInfo.getString("unickname"));
                        setIcon(friendInfo);
                        //根据是否添加好友来设置点击的按钮是否为可点击
                        if(response.getJSONObject("data").getBoolean("isAdd")){
                            add_bt.setEnabled(false);
                            add_bt.setBackgroundColor(Color.parseColor("#EBEBEB"));
                        }else {
                            add_bt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    add_bt.setEnabled(false);
                                    add_bt.setBackgroundColor(Color.parseColor("#EBEBEB"));
                                    //点击添加 发送请求申请添加好友
                                    try {
                                        requestAddFrind(uid,utel);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonRequest);
    }
    private String utel;
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.search){//点击查找好友
            if(TextUtils.isEmpty(search_friend_byTel.getText().toString())){
                Toast.makeText(AddFriendsActivity.this,"请输入你查询的好友账号",Toast.LENGTH_SHORT).show();
            }else {
                utel = search_friend_byTel.getText().toString();
                searchFriendIofo(uid);
                setDynamicText(uid);
            }
        }
        if(view.getId()==R.id.back_home){
            finish();
        }
    }
}
