package com.example.loginandforgetpassword.Adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.loginandforgetpassword.Bean.FriendDetailsInfo;
import com.example.loginandforgetpassword.R;
import com.example.loginandforgetpassword.ThreadGet.ThreadGetJson;
import java.util.List;
public class MyRecycleViewAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<FriendDetailsInfo> detailsInfos;
    private String myUid;
    public MyRecycleViewAdapter(Context context,List<FriendDetailsInfo> detailsInfos,String myUid){
        this.context=context;
        this.detailsInfos=detailsInfos;
        this.myUid=myUid;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.dynamic_qq_item,parent,false);
        return new MyRecycleHolder(view);
    }

    public class MyRecycleHolder extends RecyclerView.ViewHolder{
        //初始化控件
        private ImageView icon_dynamic;
        private TextView name_dynamic;
        private TextView content_dynamic;
        private ImageView like_dynamic;
        private ImageView write_dynamic;
        private TextView numLike_dynamic;
        private TextView numWrite_dynamic;
        private TextView id_dynamic;
        private TextView item_count_dynamic;
        public MyRecycleHolder(@NonNull View itemView) {
            super(itemView);
            icon_dynamic=itemView.findViewById(R.id.icon_dynamic);
            name_dynamic=itemView.findViewById(R.id.name_dynamic);
            content_dynamic=itemView.findViewById(R.id.content_dynamic);
            like_dynamic=itemView.findViewById(R.id.like_dynamic);
            write_dynamic=itemView.findViewById(R.id.write_dynamic);
            numLike_dynamic=itemView.findViewById(R.id.numLike_dynamic);
            numWrite_dynamic=itemView.findViewById(R.id.numWrite_dynamic);
            id_dynamic=itemView.findViewById(R.id.id_dynamic);
            item_count_dynamic=itemView.findViewById(R.id.item_count_dynamic);
        }
    }
    private FriendDetailsInfo fdi;
    private String dynamicId;
    private String newCount;
    private int currentCount;
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        fdi=detailsInfos.get(position);
        final MyRecycleHolder myRecycleHolder= (MyRecycleHolder) holder;
        myRecycleHolder.icon_dynamic.setImageBitmap(fdi.getPic());
        myRecycleHolder.name_dynamic.setText(fdi.getName());
        myRecycleHolder.content_dynamic.setText(fdi.getContent());
        myRecycleHolder.numLike_dynamic.setText(String.valueOf(fdi.getLikeCount()));
        myRecycleHolder.id_dynamic.setText(fdi.getDynamicId());
        myRecycleHolder.item_count_dynamic.setText(String.valueOf(position));
        if(fdi.isLike()){
            myRecycleHolder.like_dynamic.setImageResource(R.drawable.dianzan2);
        }else {
            myRecycleHolder.like_dynamic.setImageResource(R.drawable.dianzan1);
            myRecycleHolder.like_dynamic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myRecycleHolder.like_dynamic.setImageResource(R.drawable.dianzan2);
                    myRecycleHolder.like_dynamic.setEnabled(false);
                    newCount=String.valueOf(Integer.parseInt(myRecycleHolder.numLike_dynamic.getText().toString())+1);
                    myRecycleHolder.numLike_dynamic.setText(newCount);
                    dynamicId=myRecycleHolder.id_dynamic.getText().toString();
                    currentCount=Integer.parseInt(myRecycleHolder.item_count_dynamic.getText().toString());
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            ThreadGetJson threadGetJson=new ThreadGetJson("http://115.29.202.70:8123/baseservice/simpledynamicliked/clickLike/"+myUid+"/"+dynamicId);
                            Log.i("点赞",String.valueOf(threadGetJson.getJSON()));
                            fdi=detailsInfos.get(currentCount);
                            fdi.setLike(true);
                            fdi.setLikeCount(Integer.parseInt(newCount));
                        }
                    }.start();
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return detailsInfos.size();
    }

}
