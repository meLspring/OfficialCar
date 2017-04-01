package com.example.yysh.officialcar;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import utils.NetUtils;

public class ZiXunActivity extends AppCompatActivity {

    private WebView first_webview;
    private LinearLayout net_linear;
    private TextView open_net;
    private WebSettings settings;
    private Button picture;
    private boolean flag=true;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                picture.setText("无图");
                settings.setBlockNetworkImage(false);
                flag=false;
            }
            if(msg.what==1){
                picture.setText("有图");
                settings.setBlockNetworkImage(true);
                flag=true;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zi_xun);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initData() {
        if(NetUtils.isNetworkAvailable(this)) {
            loadWebView();
        }else{
            net_linear.setVisibility(View.VISIBLE);
            picture.setVisibility(View.GONE);
            open_net.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    net_linear.setVisibility(View.GONE);
                    initData();
                }
            });
        }
    }
    //加载webview，显示内容
    public void loadWebView(){
        picture.setVisibility(View.VISIBLE);
        settings=first_webview.getSettings();
        settings.setJavaScriptEnabled(true); //设置支持js
        settings.setSupportMultipleWindows(true);       //设置webview支持多窗口
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        //webview内部跳转,但只能加载html，不能操作js脚本等操作
        first_webview.setWebViewClient(new WebViewClient());
        //设置这个是能操作js脚本等操作
        first_webview.setWebChromeClient(new WebChromeClient());
        first_webview.loadUrl("http://www.baidu.com");
    }

    private void initView() {
        first_webview = ((WebView) findViewById(R.id.first_webview));
        net_linear = ((LinearLayout) findViewById(R.id.net_linear));
        open_net = ((TextView) findViewById(R.id.open_net));
        picture = ((Button) findViewById(R.id.picture));
        net_linear.setVisibility(View.GONE);
    }

    public void to_login(View view) {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){ //监听返回键
            if(first_webview.canGoBack()){      //判断当前webview是否可以后退
                first_webview.goBack();
                return  true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //点击开启有图或关闭图片显示
    public void picture(View view) {
        if(flag){   //说明有图模式
            handler.sendEmptyMessage(0);
        }else{          //说明无图模式
            handler.sendEmptyMessage(1);
        }
    }
}
