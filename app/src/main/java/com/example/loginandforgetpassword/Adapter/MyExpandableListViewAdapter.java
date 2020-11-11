package com.example.loginandforgetpassword.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.loginandforgetpassword.Bean.ExpandableBean;
import com.example.loginandforgetpassword.R;
import java.util.HashMap;
import java.util.List;

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupName;
    private HashMap<String,List<ExpandableBean>> childmap;
    private TextView ebg_groupName;
    private TextView ebg_groupNumber;
    private ImageView lv_childPic;
    private TextView tv_childName,tv_childloginInfor,tv_childMotto;
    //构造方法传值
    public MyExpandableListViewAdapter(Context context, List<String> groupName, HashMap<String, List<ExpandableBean>> childList){
        this.context=context;
        this.groupName=groupName;
        this.childmap=childList;
    }
    @Override
    //获得主列表列表数目
    public int getGroupCount() {
        return groupName.size();
    }
    //获得主列表对应子列表数目
    @Override
    public int getChildrenCount(int i) {
        return childmap.get(groupName.get(i)).size();
    }
    //获得主列表对象
    @Override
    public Object getGroup(int i) {
        return groupName.get(i);
    }
    //获得主列表对应子列表对象
    @Override
    public Object getChild(int i, int i1) {
        return childmap.get(groupName.get(i)).get(i1);
    }
    //获得主列表id
    @Override
    public long getGroupId(int i) {
        return i;
    }
    ///获得主列表对应子列表id
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    //创建主列表视图控件并且绑定对应的数据
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.expandable_group_item, viewGroup, false);
        }
        ebg_groupName=view.findViewById(R.id.ebg_groupName);
        ebg_groupNumber=view.findViewById(R.id.ebg_groupNumber);
        ebg_groupName.setText(groupName.get(i));
        ebg_groupNumber.setText(this.getChildrenCount(i)+"");
        return view;
    }
    //设置主列表对应子列表布局 并且绑定数据
    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        List<ExpandableBean> childList=childmap.get(groupName.get(i));
        ExpandableBean expandableBean=childList.get(i1);
        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.expandable_child_item, viewGroup, false);
        }
        lv_childPic =view.findViewById(R.id.qqpic);
        tv_childName=view.findViewById(R.id.qqname);
        tv_childloginInfor=view.findViewById(R.id.loginInfor);
        tv_childMotto=view.findViewById(R.id.motto);
        lv_childPic.setImageResource(expandableBean.getQqpic());
        tv_childName.setText(expandableBean.getName());
        tv_childloginInfor.setText(expandableBean.getLoginInfo());
        tv_childMotto.setText(expandableBean.getMotto());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}