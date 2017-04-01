package com.example.yysh.officialcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


//系统介绍页面
public class SystemIntroduceActivity extends AppCompatActivity {

    private Toolbar system_toobar;
    private TextView system_intro_one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_introduce);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        String content=getIntent().getStringExtra("QRContent");
        system_intro_one.setText("1."+content);
    }

    private void initView() {
        system_toobar = ((Toolbar) findViewById(R.id.system_toobar));
        system_intro_one = ((TextView) findViewById(R.id.system_intro_one));
    }

    public void okClick(View view) {
        Intent intentTo=new Intent(this,MainActivity.class);
        startActivity(intentTo);
        finish();
        Intent intent=new Intent();
        intent.setAction("closeActivity");
        sendBroadcast(intent);
    }

    //点击返回箭头
    public void system_back(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
