package com.example.yysh.officialcar;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by Lspring on 2017/3/27.
 */

public class ScannerActivity extends Activity implements ZBarScannerView.ResultHandler{
    private ZBarScannerView zBarScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zBarScannerView=new ZBarScannerView(this);
        setContentView(zBarScannerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启相机
        zBarScannerView.setResultHandler(this);
        zBarScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //关闭相机
        zBarScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //得到扫描的结果
        String content=result.getContents();
        Log.e("QRContent",content);
    }
}
