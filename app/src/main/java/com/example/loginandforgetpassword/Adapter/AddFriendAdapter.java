package com.example.loginandforgetpassword.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginandforgetpassword.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class AddFriendAdapter extends BaseAdapter {
    private final String FriendHead="http://115.29.202.70:8123/baseservice/simplefriends/";
    private Context context;
    private JSONArray jsonArray;
    private String uid;
    private RequestQueue requestQueue;
    public AddFriendAdapter(Context context,JSONArray jsonArray,String uid){
        this.context=context;
        this.jsonArray=jsonArray;
        this.uid=uid;
    }
    @Override
    public int getCount() {
        return jsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        JSONObject jsonObject=null;
        try {
            jsonObject= jsonArray.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        requestQueue= Volley.newRequestQueue(context);
        if(view==null) {
            view= LayoutInflater.from(context).inflate(R.layout.item_add_friends, viewGroup, false);
        }
        final ImageView request_friends_icon=view.findViewById(R.id.request_friends_icon);
        TextView request_friends_name=view.findViewById(R.id.request_friends_name);
        TextView request_friends_userName=view.findViewById(R.id.request_friends_userName);
        final Button request_friends_btAgree=view.findViewById(R.id.request_friends_btAgree);
        final Button request_friends_btRefuse=view.findViewById(R.id.request_friends_btRefuse);
        try {
            final JSONObject friendInfo=jsonArray.getJSONObject(i);
            //设置对应按钮的监听事件
            request_friends_btAgree.setOnClickListener(new View.OnClickListener() {
                //设置同意按钮的监听事件
                @Override
                public void onClick(View view) {
                    try {
                        String friendId=friendInfo.getString("uid");
                        JSONObject parameter=new JSONObject();
                        parameter.put("uid",uid);
                        parameter.put("friendId",friendId);
                        JsonObjectRequest agreeAddFriend=new JsonObjectRequest(Request.Method.POST, FriendHead + "AgreeFriend", parameter, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                request_friends_btAgree.setEnabled(false);
                                request_friends_btRefuse.setEnabled(false);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(agreeAddFriend);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            request_friends_btRefuse.setOnClickListener(new View.OnClickListener() {
                //设置拒绝按钮的监听事件
                @Override
                public void onClick(View view) {
                    try {
                        String friendId=friendInfo.getString("uid");
                        JSONObject parameter=new JSONObject();
                        parameter.put("uid",uid);
                        parameter.put("friendId",friendId);
                        JsonObjectRequest disagreeAddFriend=new JsonObjectRequest(Request.Method.POST, FriendHead + "disAgreeFriend", parameter, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                request_friends_btRefuse.setEnabled(false);
                                request_friends_btAgree.setEnabled(false);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        requestQueue.add(disagreeAddFriend);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            //显示名字和账号
            request_friends_name.setText(friendInfo.getString("unickname"));
            request_friends_userName.setText("( 账号:"+friendInfo.getString("utel")+" )");
            //设置图片
            ImageRequest setItemIcon=new ImageRequest(friendInfo.getString("uavatar"), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    request_friends_icon.setImageBitmap(response);
                }
            }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(setItemIcon);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
