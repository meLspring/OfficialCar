package com.example.yysh.officialcar;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

//扫面二维码页面
public class QRCodeActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler{
    private Toolbar toobar;
    private ZBarScannerView zBarScannerView;
    private QRCodeActivity mcode;
    private QRCodeActivityBroad broad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        //注册广播
        register();
    }

    private void register() {
        broad=new QRCodeActivityBroad();
        IntentFilter filter=new IntentFilter();
        filter.addAction("closeActivity");
        registerReceiver(broad,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broad);
    }

    private void initView() {
        toobar= ((Toolbar) findViewById(R.id.QRToobar));
        zBarScannerView = ((ZBarScannerView) findViewById(R.id.zBarScannerView));



    }
    //点击箭头跳转到主页
    public void QR_back(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        zBarScannerView.setResultHandler(this);
        zBarScannerView.startCamera(-1);
        zBarScannerView.setFlash(false);
        zBarScannerView.setAutoFocus(true);
    }


    @Override
    protected void onStop() {
        super.onStop();
        zBarScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //得到扫描的结果
        String content=result.getContents();
        Intent intent=new Intent(this,SystemIntroduceActivity.class);
        intent.putExtra("QRContent",content);
        startActivity(intent);
    }

    //切换横竖屏调用的方法
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public class QRCodeActivityBroad extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }



}
