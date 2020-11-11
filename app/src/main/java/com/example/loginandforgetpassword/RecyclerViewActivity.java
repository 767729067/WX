//package com.example.loginandforgetpassword;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//
//import com.example.loginandforgetpassword.Adapter.RecycleViewAdapter;
//import com.example.loginandforgetpassword.Bean.ContentBean;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RecyclerViewActivity extends AppCompatActivity {
//    private RecyclerView rv;
//    //自定义数据
//    private int[] pic=new int[]{R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,R.drawable.pic4
//            ,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7
//            ,R.drawable.pic8,R.drawable.pic9,R.drawable.pic10};
//    private String[] qqName=new String[]{"久不愈i","＜桃花深处※","很酷也挺污","慰你心安","哥、活的有模有样"
//            ,"追风人","古道印残灯","清晨┆一只鹿","′つ妖精","雨夜你在想谁←"};
//    private String[] qqMessage=new String[]{"你好呀？","明天来南门","什么时候去吃饭？","你在干嘛?","来呀 开黑呀","你作业交了没有？"
//            ,"收衣服啊","下午去哪里吃饭？","在不 借我点钱","哎 穷啊 不好混啊 迷茫"};
//    private String[] qqDate=new String[]{"14:20","昨天 3:40","星期一","星期六","3:22","12:33","12:34","10:27","9:20","前天 9:26"};
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recycler_view);
////        List<ContentBean> contentBeansList=new ArrayList<>();
//        for(int i=0;i<pic.length;i++){
//            ContentBean contentBean=new ContentBean();
////            contentBean.setPictrue(pic[i]);
//            contentBean.setName(qqName[i]);
//            contentBean.setMeaagae(qqMessage[i]);
//            contentBean.setDate(qqDate[i]);
//            contentBeansList.add(contentBean);
//        }
//        //初始化控件
//        rv=findViewById(R.id.rv);
//        设置布局管理器
//        LinearLayoutManager llm=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        rv.setLayoutManager(llm);//设置布局管理器
//        RecycleViewAdapter recycleViewAdapter=new RecycleViewAdapter(this,contentBeansList);//创建自定义适配器对象
//        rv.setAdapter(recycleViewAdapter);//绑定适配器
//    }
//}
