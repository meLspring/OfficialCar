package com.example.yysh.officialcar;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Lspring on 2017/3/31.
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
