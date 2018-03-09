package com.sharebookcare.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.StrictMode;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;


public class ShareApplication extends Application {

    private static ShareApplication app;

    public static ShareApplication getInstance(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initBmob();
    }

    private void initBmob() {
        //第一：默认初始化

//        BmobConfig configBuilder = new BmobConfig.Builder(this)
//                .setConnectTimeout(60)
//                .setUploadBlockSize(500*1024)
//                .build();


        Bmob.initialize(this, "d74b50c2c6a44c3b45305a6a8c1a0f35");

        //7.0 sdk 解决拍照问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }

}
