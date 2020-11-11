//package com.example.loginandforgetpassword.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.loginandforgetpassword.Bean.ContentBean;
//import com.example.loginandforgetpassword.R;
//
//import java.util.List;
//
//public class RecycleViewAdapter extends RecyclerView.Adapter {
//    private Context context;
//    private List<ContentBean> contentBeanList;
//    //构造方法传值
//    public RecycleViewAdapter(Context context, List<ContentBean> contentBeanList){
//        this.context=context;
//        this.contentBeanList=contentBeanList;
//    }
//    @NonNull
//    @Override
//    //生成view对象  创建view的缓存对象
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(context).inflate(R.layout.lv_layout,parent,false);
//        return new Myholder(view);
//    }
//    //绑定数据
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ContentBean contentBean=contentBeanList.get(position);
//        Myholder myholder= (Myholder) holder;
//        myholder.pic.setImageBitmap(contentBean.getPic());
//        myholder.name.setText(contentBean.getName());
//        myholder.message.setText(contentBean.getMeaagae());
//        myholder.date.setText(contentBean.getDate());
//    }
//    //自定义类继承RecycleView.ViewHolder;用来缓存创建的view对象和 缓存findViewById的过程
//    @Override
//    public int getItemCount() {
//        return contentBeanList.size();
//    }
//    public class Myholder extends RecyclerView.ViewHolder{
//        private ImageView pic;
//        private TextView name,message,date;
//        public Myholder(@NonNull View itemView) {
//            super(itemView);
//            pic=itemView.findViewById(R.id.lv_pic);
//            name=itemView.findViewById(R.id.lv_qqname);
//            message=itemView.findViewById(R.id.lv_message);
//            date=itemView.findViewById(R.id.lv_date);
//        }
//    }
//}
