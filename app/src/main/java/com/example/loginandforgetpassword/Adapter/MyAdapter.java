package com.example.loginandforgetpassword.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.loginandforgetpassword.Bean.ContentBean;
import com.example.loginandforgetpassword.R;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<ContentBean> listBean;
    private ImageView lv_pic;
    private TextView lv_qqname;
    private TextView lv_message;
    private TextView lv_date;
    public MyAdapter(Context context, List<ContentBean> listBean){
        this.context=context;
        this.listBean=listBean;
    }
    @Override
    public int getCount() {
        return listBean.size();
    }

    @Override
    public Object getItem(int i) {
        return listBean.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ContentBean cb=listBean.get(i);
        if(view==null){//查找是否有缓存的view 如果有就直接用不用重新创建
            view = LayoutInflater.from(context).inflate(R.layout.lv_layout, viewGroup,false);
        }
        lv_pic=view.findViewById(R.id.lv_pic);
        lv_qqname=view.findViewById(R.id.lv_qqname);
        lv_message=view.findViewById(R.id.lv_message);
        lv_date=view.findViewById(R.id.lv_date);
        lv_pic.setImageBitmap(cb.getPic());
        lv_qqname.setText(cb.getName());
        lv_message.setText(cb.getMeaagae());
        lv_date.setText(cb.getDate());
        return view;
    }
}
