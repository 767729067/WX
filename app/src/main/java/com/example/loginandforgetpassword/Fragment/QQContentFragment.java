package com.example.loginandforgetpassword.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loginandforgetpassword.Adapter.MyAdapter;
import com.example.loginandforgetpassword.Bean.ContentBean;
import com.example.loginandforgetpassword.ChatActivity;
import com.example.loginandforgetpassword.ForgetPassWordActivity;
import com.example.loginandforgetpassword.Main2Activity;
import com.example.loginandforgetpassword.QQContent;
import com.example.loginandforgetpassword.R;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetIcon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QQContentFragment extends Fragment implements AdapterView.OnItemClickListener {
    private JSONArray getFriends;
    private Handler mhandler;
    private View view;
    private ListView listView;
    private String myUtel;
    private String myIcon;
    public QQContentFragment(){}
    public QQContentFragment(JSONArray getFriends,String myUtel,String myIcon){
        this.getFriends=getFriends;
        this.myUtel=myUtel;
        this.myIcon=myIcon;
    }
    private ArrayList<ContentBean> listBean;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_qqcontent,container,false);
        listView=view.findViewById(R.id.lv_content);
        listView.setOnItemClickListener(this);
        this.getFriendIcons();
        mhandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i("mHandler","开始执行mHandler");
                List<Bitmap> bitmapList=(List<Bitmap>) msg.obj;
                listBean=new ArrayList<>();
                for(int i=0;i<getFriends.length();i++){
                    ContentBean cb=new ContentBean();
                    try {
                        cb.setName(getFriends.getJSONObject(i).getString("unickname"));
                        cb.setDate(getFriends.getJSONObject(i).getString("uidUpdatetime"));
                        cb.setUtel(getFriends.getJSONObject(i).getString("utel"));
                        cb.setPic(bitmapList.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    listBean.add(cb);
                }
                MyAdapter myAdapter=new MyAdapter(getContext(),listBean);
                listView.setAdapter(myAdapter);
            }
        };
        return view;
    }
    private void getFriendIcons(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                List<Bitmap> bitmapList=new ArrayList<>();
                for(int i=0;i<getFriends.length();i++) {
                    try {
                        ThreadGetIcon tgi = new ThreadGetIcon(getFriends.getJSONObject(i).getString("uavatar"));
                        bitmapList.add(tgi.getICON());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Looper.prepare();
                Message message=new Message();
                message.obj=bitmapList;
                mhandler.sendMessage(message);
                Looper.loop();
            }
        }.start();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Adapter adapter=adapterView.getAdapter();
        ContentBean contentBean= (ContentBean) adapter.getItem(i);
        Intent intent=new Intent(getContext(), ChatActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("name",contentBean.getName());
        bundle.putString("utel",contentBean.getUtel());
        try {
            bundle.putString("icon",getFriends.getJSONObject(i).getString("uavatar"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bundle.putString("myUtel",myUtel);
        bundle.putString("myIcon",myIcon);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
