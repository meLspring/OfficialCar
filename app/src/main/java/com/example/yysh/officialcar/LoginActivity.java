package com.example.yysh.officialcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Base64Utils;
import utils.RSAUtils;
import utils.RSAUtils2;

public class LoginActivity extends AppCompatActivity {
    private final static String GET_ENTRY="http://10.101.170.137:8369/usermgr/userlogin/rsapublickey";
    private EditText phoneNumber;
    private EditText code;
    private String get_entryStr;        //服务器返回的需要加密字符串
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initView();
        initData();
    }


    private void initData() {
        getStringFromNet();

    }


    //从接口获取服务器返回来的需要加密字符串
    private void getStringFromNet() {
        RequestParams params=new RequestParams(GET_ENTRY);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                get_entryStr=result;
                try {
                    JSONObject object = new JSONObject(get_entryStr);
                    String PublicKey=object.getString("PublicKey");
                    //加密方法
                    encryKey(PublicKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    //对解析xml中得到的秘钥进行加密
    private void encryKey(String key) {
        try {
            String str="8889";
            //将字符串转为流
            InputStream is=getResources().getAssets().open("rsa_public_key.pem");//加载公钥
            PublicKey publicKey = RSAUtils.loadPublicKey(is);
           //对公钥加密
            byte[] bytes = RSAUtils.encryptData(str.getBytes(), publicKey);
            //用base64进行加密
            String encode= Base64Utils.encode(bytes);
            if(!TextUtils.isEmpty(encode)){
                Log.e("encode",encode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        phoneNumber = ((EditText) findViewById(R.id.phoneNumber));
        code = ((EditText) findViewById(R.id.code));
    }

    //登录按钮
    public void sumbitClick(View view){
        String phone=phoneNumber.getText().toString().trim();
        String scode=code.getText().toString().trim();
        if(!TextUtils.isEmpty(scode)) {
            if (isMobileNO(phone)) {
                Intent intent = new Intent(this, QRCodeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "请输入正确的手机号或验证码", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //获取验证码方法
    public void getCode(View view) {
        String phone=phoneNumber.getText().toString().trim();
        if(!TextUtils.isEmpty(phone)) {
            if (!isMobileNO(phone)) {
                Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
            } else {    //判断手机号正确的逻辑操作
                Toast.makeText(this, "手机号正确", Toast.LENGTH_SHORT).show();
                //这里讲手机号和接口拼接上，传到服务器，服务器验证手机号失败则弹出小弹窗提示
            }
        }else{
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        }
    }
    //正则表达式判断是否是手机号
    public static boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
