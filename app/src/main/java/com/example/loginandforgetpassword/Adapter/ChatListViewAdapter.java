package com.example.loginandforgetpassword.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.loginandforgetpassword.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ChatListViewAdapter extends BaseAdapter {
    private Context context;
    private List<JSONObject> messagesInfo;
    private Bitmap myBitmap;
    public ChatListViewAdapter(Context context, List<JSONObject> messagesInfo, Bitmap myBitmap){
        this.context= context;
        this.messagesInfo=messagesInfo;
        this.myBitmap=myBitmap;
    }
    @Override
    public int getCount() {
        return messagesInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return messagesInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        JSONObject info=messagesInfo.get(i);
        boolean isMyMsg=true;
        String msg=null;
        try {
            isMyMsg=info.getBoolean("isMyMsg");
            msg=info.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        View myView=null;
        if(isMyMsg) {
            myView = LayoutInflater.from(context).inflate(R.layout.chat_item_bg_right, viewGroup, false);
        }else {
            myView = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
        }
        TextView message=myView.findViewById(R.id.message);
        message.setText(msg);
        ImageView icon=myView.findViewById(R.id.chat_icon);
        icon.setImageBitmap(myBitmap);
        return myView;
    }
}
