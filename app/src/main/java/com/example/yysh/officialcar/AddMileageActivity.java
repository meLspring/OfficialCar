package com.example.yysh.officialcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddMileageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mileage);
        getSupportActionBar().hide();
    }

    //点击取消按钮，不保存返回到主界面
    public void cancle(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    //点击确定按钮，保存数据返回到主界面
    public void confirm(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
