package com.example.loginandforgetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.loginandforgetpassword.Adapter.MyExpandableListViewAdapter;
import com.example.loginandforgetpassword.Bean.ExpandableBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpandableListViewActivity extends AppCompatActivity {
    private ExpandableListView elv;
    private List<String> grouplist;
    private Map<String,List<ExpandableBean>> childData;
    private List<ExpandableBean> expandableBeanList;
    private ExpandableBean expandableBean;
    //自定义主列表数据
    private String[] groupName=new String[]{"郭","郝","黄","胡","贺"};
    //用二维数组创建主列表对应子列表头像数组将同一个主列表的子列表数据数组放在同一个数组中
    private int[][] pic=new int[][]{{R.drawable.pic1,R.drawable.pic2},{R.drawable.pic3},{R.drawable.pic4,R.drawable.pic5,R.drawable.pic6},{R.drawable.pic7,R.drawable.pic8},
            {R.drawable.pic9,R.drawable.pic10}};
    private String[][] name=new String[][]{{"郭泽宇","郭辉"},{"郝真"},{"黄佳明","黄国腾","黄建"},{"胡坚","胡艳云"},{"贺超","贺叶龙"}};
    private String[][] loginInfo=new String[][]{{"在线","手机在线"},{"4G在线"},{"2G-手机在线","4G在线","在线"},{"3G在线","5G在线"},{"4G在线","4G在线"}};
    private String[][] motto=new String[][]{{"我无法回头,我无法挽留","一花一世界,一叶一追寻"},
            {"我用三生烟火,换你一世迷离"},{"山有木兮木有枝,心悦君兮君不知",
            "如果你给我的,和你给别人的是一样的,那我就不要了",
            "等待,不是为了你能回来,而是找个借口,不离开"},
            {"不要太在乎一些人,越在乎,越卑微",
                    "念了十几年书,想起来还是幼儿园好混"},
            {"相信太难,不如沉默来得简单","人总是珍惜未得到的,而遗忘了所拥有的的"}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        //初始化控件对象
        elv=findViewById(R.id.elv);
        //创建主列表数据集合对象
        grouplist=new ArrayList<>();
        //子列表用一个Map储存方便与主列表数据进行关联 讲key设置成主列表数据 value设置成子列表数组数据集合
        childData=new HashMap<>();
        //用for循环给创建集合对象并且存入map中
        for(int i=0;i<groupName.length;i++){
            //将主数据创建的数组groupName的元素挨个分别存入用for循环创建出来的grouplist对象中
            grouplist.add(groupName[i]);
            //创建expandableBeanlist 这里有多少个主列表数据就有多少个不同的集合对象之后再将不同的集合对象装入对应的主数据列表的对应value中
            expandableBeanList=new ArrayList<>();
            //利用循环来给list填入bean对象 每个列表中的bean对象中的个数不同 取决于二维数组元素的元素的个数
            for(int j=0;j<name[i].length;j++){
                //创建出bean对象 分解将二维数组中元素的元素填入到bean对象中 再讲bean对象加入到集合对象中
                expandableBean=new ExpandableBean();
                //去对应的值分别填入bean对象中
                expandableBean.setQqpic(pic[i][j]);
                expandableBean.setName(name[i][j]);
                expandableBean.setLoginInfo(loginInfo[i][j]);
                expandableBean.setMotto(motto[i][j]);
                //把每个bean放在list中构成集合(类似于object数组)...
                expandableBeanList.add(expandableBean);
            }
            //将主数据中的元素和构成的list集合用key与value的形式进行绑定 这样可以在适配器中方便对应绑定取值
            childData.put(groupName[i],expandableBeanList);
        }
        //适配器对象初始化
        MyExpandableListViewAdapter myExpandableListViewAdapter=new MyExpandableListViewAdapter(ExpandableListViewActivity.this,grouplist, (HashMap<String, List<ExpandableBean>>) childData);
        //给ExpandableListView绑定自定义的适配器对象
        elv.setAdapter(myExpandableListViewAdapter);
    }
}

