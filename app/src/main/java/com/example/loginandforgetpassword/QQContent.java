package com.example.loginandforgetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loginandforgetpassword.Adapter.MyAdapter;
import com.example.loginandforgetpassword.Bean.ContentBean;

import java.util.ArrayList;
import java.util.List;

public class QQContent extends AppCompatActivity {
    private ListView listView;
    //准备好的数据
    private int[] pic=new int[]{R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4
                                ,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7
                                ,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10};
    private String[] qqName=new String[]{"久不愈i","＜桃花深处※","很酷也挺污","慰你心安","哥、活的有模有样"
                                            ,"追风人","古道印残灯","清晨┆一只鹿","′つ妖精","雨夜你在想谁←"};
    private String[] qqMessage=new String[]{"你好呀？","明天来南门","什么时候去吃饭？","你在干嘛?","来呀 开黑呀","你作业交了没有？"
                                                ,"收衣服啊","下午去哪里吃饭？","在不 借我点钱","哎 穷啊 不好混啊 迷茫"};
    private String[] qqDate=new String[]{"14:20","昨天 3:40","星期一","星期六","3:22","12:33","12:34","10:27","9:20","前天 9:26"};
    //将数据存在bean集合里
    private List<ContentBean> listBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqcontent);
        listBean=new ArrayList<>();
        //for循环用来存数据到集合里
        for(int i=0;i<pic.length;i++){
            ContentBean cb=new ContentBean();
//            cb.setPic(pic[i]);
            cb.setName(qqName[i]);
            cb.setMeaagae(qqMessage[i]);
            cb.setDate(qqDate[i]);
            listBean.add(cb);
        }
        listView=findViewById(R.id.lv_content);
        MyAdapter myAdapter=new MyAdapter(this,listBean);
        listView.setAdapter(myAdapter);
        Toast.makeText(QQContent.this,"登录成功",Toast.LENGTH_SHORT).show();
    }
}
