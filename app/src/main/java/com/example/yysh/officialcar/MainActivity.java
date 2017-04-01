package com.example.yysh.officialcar;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fragment.CarConditionFragment;
import fragment.InformationFragment;
import fragment.PhoneFragment;
import fragment.RecordFragment;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rg;
    private ArrayList<Fragment> fragmentList;
    private TextView main_toobarTitle;
    private int mIndex=0;
    private boolean firstFragment=true; //判断是否第一次开启MainActivity
    private boolean addIcon;       //判断点击信息fragment后右上角图标的改变
    private ImageView check_add_icon;
    private RadioButton rb_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView();
        initData();

    }



    //将Fragemtn放到集合里
    public void initData() {
        fragmentList=new ArrayList<>();
        CarConditionFragment carCondition=new CarConditionFragment();
        RecordFragment record=new RecordFragment();
        //AddMileageFragment addMileage=new AddMileageFragment();
        PhoneFragment phone=new PhoneFragment();
        InformationFragment information=new InformationFragment();
        fragmentList.add(carCondition);
        fragmentList.add(record);
        fragmentList.add(phone);
        fragmentList.add(information);
        switchFragment(mIndex,fragmentList.get(0));      //传入0时默认显示的Fragment

        //从信息平台详情跳到MainActivity制定的信息平台Fragment
        ToMainActivity();
    }

    private void ToMainActivity() {
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            int page = bundle.getInt("platform");
            if (page == 3) {
                switchFragment(page,fragmentList.get(3));
                main_toobarTitle.setText("信息平台");
                changeIcon(3);
            }
        }
    }


    public void initView() {
        rg= ((RadioGroup) findViewById(R.id.main_rg));
        main_toobarTitle = ((TextView) findViewById(R.id.main_toobarTitle));
        rb_add = ((RadioButton) findViewById(R.id.rb_add));
        check_add_icon = ((ImageView) findViewById(R.id.check));
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.carCondition:
                        switchFragment(0,fragmentList.get(0));
                        changeIcon(0);
                        break;
                    case R.id.record:
                        main_toobarTitle.setText("维修记录");
                        switchFragment(1,fragmentList.get(1));
                        changeIcon(1);
                        break;
                    case R.id.phone:
                        main_toobarTitle.setText("电话表");
                        switchFragment(2,fragmentList.get(2));
                        changeIcon(2);
                        break;
                    case R.id.information:
                        main_toobarTitle.setText("信息平台");
                        switchFragment(3,fragmentList.get(3));
                        changeIcon(3);
                        break;
                }
            }
        });

        rb_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddMileageActivity.class);
                startActivity(intent);
            }
        });

    }

    private void switchFragment(int index,Fragment fragment) {
        if(index==0){
            main_toobarTitle.setText("车况详情");
            if(firstFragment){
                getSupportFragmentManager().beginTransaction().add(R.id.container,fragmentList.get(0)).commit();
                firstFragment=false;
                return;
            }
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }

    //更改右上角图标
    private void changeIcon(int i) {
        if (i==3){       //说明是信息页面，更改图标
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
            check_add_icon.setImageBitmap(bitmap);
            addIcon=true;
        }else{
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            check_add_icon.setImageBitmap(bitmap);
            addIcon=false;
        }
    }

        //点击toobar上二维码图标跳转到二维码扫描页面
      public void main_QRClick(View view){
          Intent intent=new Intent(this,QRCodeActivity.class);
          startActivity(intent);

      }



    //点击toobar上维修图标跳转到维修页面
    public void checkClick(View view){
        //先判断当前radiogroup点击的是否是信息radiobutton
        if(addIcon){
            Intent intent=new Intent(this,InforPlatformActivity.class);
            startActivity(intent);
        }else{
            Intent intent=new Intent(MainActivity.this,NewsCenterActivity.class);
            startActivity(intent);
        }
    }


    //使用singleTask启动模式，所以要在onNewIntent方法里对当前MainActivity进行刷新
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
