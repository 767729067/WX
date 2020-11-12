package com.example.loginandforgetpassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandforgetpassword.Adapter.FragmentAdapter;
import com.example.loginandforgetpassword.Fragment.ExpandableFragment;
import com.example.loginandforgetpassword.Fragment.QQContentFragment;
import com.example.loginandforgetpassword.Fragment.QQDynamicFragment;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetIcon;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager vp;
    private List<Fragment> fragmentList;
    private ConstraintLayout bottomlayout1,bottomlayout2,bottomlayout3;
    private ImageView iv1,iv2,iv3,pic;
    private Handler myHandler;
    private TextView qqname;
    private String tel;
    private JSONArray getFriends;
    private ImageView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_main2);
        vp=findViewById(R.id.vp);
        initViewAndListener();
        tel=getTel();
        Log.i("tel",tel);
        newThreadGetJson();
        getHandlerMessage();
    }
    private String myUtel;
    private String myIcon;
    private String myUid;
    private void getHandlerMessage() {
        myHandler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Bundle bundle=msg.getData();
                JSONObject jsonObject;
                Map<String,JSONArray> jsonArrayMap;
                jsonArrayMap= (Map<String, JSONArray>) msg.obj;
                getFriends=jsonArrayMap.get("getFriends");
                Log.i("messageGetJsonArray",String.valueOf(getFriends));
                try {
                    jsonObject=new JSONObject(bundle.getString("jo")).getJSONObject("data").getJSONObject("userinfo");
                    Log.i("jsonObject",String.valueOf(jsonObject));
                    qqname.setText(jsonObject.getString("unickname"));
                    myUtel=jsonObject.getString("utel");
                    myIcon=bundle.getString("iconUrl");
                    myUid=jsonObject.getString("uid");
                    Bitmap bitmap=bundle.getParcelable("bitmap");
                    pic.setImageBitmap(bitmap);
                    fragmentList=initData();
                    FragmentAdapter fa=new FragmentAdapter(getSupportFragmentManager(),fragmentList);
                    vp.setAdapter(fa);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            AlertDialog.Builder ab=new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("您确定要退出么?")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Main2Activity.this.finish();
                        }
                    })
                    .setNegativeButton("取消",null);
            ab.create().show();
            return false;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void newThreadGetJson() {
        Thread thread=new Thread(){
            @Override
            public void run() {
                super.run();
                ThreadGetJson tgj=new ThreadGetJson("http://115.29.202.70:8123/baseservice/simpleuserinfo/getuserinfobytel/"+tel);
                JSONObject jsonObject=tgj.getJSON();
                Log.i("jo",String.valueOf(jsonObject));
                String iconUrl=null;
                String uid=null;
                try {
                    iconUrl=jsonObject.getJSONObject("data").getJSONObject("userinfo").getString("uavatar");
                    uid=jsonObject.getJSONObject("data").getJSONObject("userinfo").getString("uid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ThreadGetIcon tgi=new ThreadGetIcon(iconUrl);
                Bitmap bitmap=tgi.getICON();
                Log.i("uid",uid);
                ThreadGetJson getFriend=new ThreadGetJson("http://115.29.202.70:8123/baseservice/simplefriends/allFriends/"+uid);
                JSONArray getFriends= null;
                try {
                    getFriends = getFriend.getJSON().getJSONObject("data").getJSONArray("friends");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("getFriends",String.valueOf(getFriends));
                Map<String,JSONArray> jsonArrayMap=new HashMap<>();
                jsonArrayMap.put("getFriends",getFriends);
                Bundle bundle=new Bundle();
                bundle.putParcelable("bitmap",bitmap);
                bundle.putString("iconUrl",iconUrl);
                bundle.putString("jo",String.valueOf(jsonObject));
                Message message=new Message();
                message.setData(bundle);
                message.obj=jsonArrayMap;
                myHandler.sendMessage(message);
            }
        };
        thread.start();
    }

    private void initViewAndListener() {
        add=findViewById(R.id.add);
        add.setOnClickListener(this);
        qqname=findViewById(R.id.qqname);
        pic=findViewById(R.id.pic);
        bottomlayout1=findViewById(R.id.buttonlayout1);
        bottomlayout2=findViewById(R.id.buttonlayout2);
        bottomlayout3=findViewById(R.id.buttonlayout3);
        iv1=findViewById(R.id.bottom_iv1);
        iv2=findViewById(R.id.bottom_iv2);
        iv3=findViewById(R.id.bottom_iv3);
        bottomlayout1.setOnClickListener(this);
        bottomlayout2.setOnClickListener(this);
        bottomlayout3.setOnClickListener(this);
        vp.addOnPageChangeListener(this);
    }

    private String getTel() {
        Intent intent=getIntent();
        String getTel=intent.getStringExtra("tel");
        return getTel;
    }


    private List<Fragment> initData() {
        fragmentList=new ArrayList<>();
        ExpandableFragment ef=new ExpandableFragment();
        QQContentFragment cf=new QQContentFragment(getFriends,myUtel,myIcon);
        QQDynamicFragment df=new QQDynamicFragment(myUid);
        fragmentList.add(ef);
        fragmentList.add(cf);
        fragmentList.add(df);
        return fragmentList;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonlayout1){
            vp.setCurrentItem(0);
        }
        if(view.getId()==R.id.buttonlayout2){
            vp.setCurrentItem(1);
        }
        if(view.getId()==R.id.buttonlayout3){
            vp.setCurrentItem(2);
        }
        if(view.getId()==R.id.add){
            Intent intent=new Intent(Main2Activity.this,AddFriendsActivity.class);
            intent.putExtra("myUtel",tel);
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            iv1.setImageResource(R.drawable.lianxiren2);
            iv2.setImageResource(R.drawable.xiaoxi1);
            iv3.setImageResource(R.drawable.dongtai1);
        }
        if(position==1){
            iv1.setImageResource(R.drawable.lianxiren1);
            iv2.setImageResource(R.drawable.xiaoxi2);
            iv3.setImageResource(R.drawable.dongtai1);
        }
        if(position==2){
            iv1.setImageResource(R.drawable.lianxiren1);
            iv2.setImageResource(R.drawable.xiaoxi1);
            iv3.setImageResource(R.drawable.dongtai2);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
