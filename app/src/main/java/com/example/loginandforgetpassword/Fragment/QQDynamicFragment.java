package com.example.loginandforgetpassword.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginandforgetpassword.Adapter.MyRecycleViewAdapter;
import com.example.loginandforgetpassword.Bean.FriendDetailsInfo;
import com.example.loginandforgetpassword.R;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetIcon;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class QQDynamicFragment extends Fragment {
    private Handler myHandler;
    private JSONArray getDetailValues;
    private List<FriendDetailsInfo> infos;
    private RecyclerView rv_dynamic;
    private String myUid;
    public QQDynamicFragment(String myUid){
        this.myUid=myUid;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dynamic_list,container,false);
        rv_dynamic=view.findViewById(R.id.rv_dynamic);
        initData();
        myHandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                List<FriendDetailsInfo> detailsInfos= (List<FriendDetailsInfo>) msg.obj;
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                rv_dynamic.setLayoutManager(linearLayoutManager);
                MyRecycleViewAdapter myRecycleViewAdapter=new MyRecycleViewAdapter(getContext(),detailsInfos,myUid);
                rv_dynamic.setAdapter(myRecycleViewAdapter);
            }
        };
        return view;
    }
    private void initData() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                ThreadGetJson tgjDetail=new ThreadGetJson("http://115.29.202.70:8123/baseservice/simple-dynamic-details/findFriendsDetails/"+myUid);
                JSONObject getFriendDetails=tgjDetail.getJSON();
//                JSONArray getDetailValues=null;
                try {
                    getDetailValues=getFriendDetails.getJSONObject("data").getJSONArray("detail");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                infos=new ArrayList<>();
                for(int i=0;i<getDetailValues.length();i++){
                    String url=null;
                    boolean b=false;
                    int count=0;
                    try {
                        url=getDetailValues.getJSONObject(i).getString("uavatar");
                        b=false;
                        for(int j=i;j>0;j--){
                            if(url.equals(getDetailValues.getJSONObject(j).getString("uavatar"))){
                                b=true;
                                count=j-1;
                                break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    FriendDetailsInfo detailsInfo=new FriendDetailsInfo();
                    if(b){
                        detailsInfo.setPic(infos.get(count).getPic());
                    }
                    else {
                        ThreadGetIcon tgi = new ThreadGetIcon(url);
                        Bitmap bitmap = tgi.getICON();
                        detailsInfo.setPic(bitmap);
                    }
                    Log.i("dynamic",String.valueOf(getDetailValues));
                    try {
                        detailsInfo.setName(getDetailValues.getJSONObject(i).getString("unickname"));
                        detailsInfo.setContent(getDetailValues.getJSONObject(i).getString("dynamicContent"));
                        detailsInfo.setLikeCount(getDetailValues.getJSONObject(i).getInt("likenum"));
                        detailsInfo.setLike(getDetailValues.getJSONObject(i).getBoolean("isLike"));
                        detailsInfo.setDynamicId(String.valueOf(getDetailValues.getJSONObject(i).getInt("dynamicId")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    infos.add(detailsInfo);
                }
                Looper.prepare();
                Message message=new Message();
                message.obj=infos;
                myHandler.sendMessage(message);
                Log.i("initData","线程结束了");
                Looper.loop();
            }
        }.start();
    }
}
