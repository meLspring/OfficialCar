package com.example.yysh.officialcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.NewsCenterListViewAdapter;
import bean.InformationBean;

//维修检查，消息中心页面
public class NewsCenterActivity extends AppCompatActivity {

    private Toolbar newsToobar;
    private RecyclerView news_centre_recyclerview;
    private ArrayList<InformationBean> beanList;
    private NewsCenterListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_center);
        getSupportActionBar().hide();
        initView();
        initData();
    }


    private void initData() {
        beanList=new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            InformationBean bean=new InformationBean();
            Date date=new Date();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=format.format(date);
            if(i==0){
                bean.setTime(time);
                bean.setInformation("您的车还有500公里保养到期");
            }
            if(i==1){
                bean.setTime(time);
                bean.setInformation("您的车还有15天进行保养到期");
            }
            if(i==2){
                bean.setTime(time);
                bean.setInformation("您的车还有15天需要更换轮胎");
            }
            beanList.add(bean);
        }
        adapter=new NewsCenterListViewAdapter(beanList,this);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        news_centre_recyclerview.setLayoutManager(manager);
        news_centre_recyclerview.setAdapter(adapter);
    }

    private void initView() {
        newsToobar = ((Toolbar) findViewById(R.id.news_centre_toobar));
        news_centre_recyclerview = ((RecyclerView) findViewById(R.id.news_centre_recyclerview));
    }

    public void news_back(View view) {
        Intent intent=new Intent(NewsCenterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
