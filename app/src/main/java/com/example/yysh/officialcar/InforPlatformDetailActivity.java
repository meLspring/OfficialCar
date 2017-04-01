package com.example.yysh.officialcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.InforDetailListViewAdapter;
import bean.InforDetailBean;

//信息平台详情页面
public class InforPlatformDetailActivity extends AppCompatActivity {

    private TextView showTitle;
    private RecyclerView detail_recyclerview;
    private List<InforDetailBean> beanList;
    private InforDetailListViewAdapter adapter;
    private String title;       //标题title
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_platform_detail);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        //获取传递过来的具体信息
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            title = bundle.getString("information", "");
            showTitle.setText(title);
        }
        beanList=new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            if(i==0) {
                InforDetailBean bean = new InforDetailBean();
                bean.setDetail_userName("奥巴马");
                bean.setDetail_linear(true);    //展示图片
                bean.setShowContent("八年总统白了头呀\n诺贝尔和平奖？\n荒谬\n军火商\na\nb\nc");
                beanList.add(bean);
            }
            if(i==1){
                InforDetailBean bean = new InforDetailBean();
                bean.setDetail_userName("特朗普");
                bean.setDetail_linear(false);    //展示图片
                bean.setShowContent("就是跟钱过不去");
                beanList.add(bean);
            }
        }
        adapter=new InforDetailListViewAdapter(beanList,this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        detail_recyclerview.setLayoutManager(manager);
        detail_recyclerview.setAdapter(adapter);
    }

    private void initView() {
        showTitle = ((TextView) findViewById(R.id.showTitle));
        detail_recyclerview = ((RecyclerView) findViewById(R.id.detail_recyclerview));
    }

    //点击回复跳转到信息平台页面
    public void reply(View view) {
        Intent intent=new Intent(this,InforPlatformActivity.class);
        intent.putExtra("information",title);
        startActivity(intent);
        finish();
    }

    public void detail_back(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        Bundle bundle=new Bundle();
        bundle.putInt("platform",3);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
