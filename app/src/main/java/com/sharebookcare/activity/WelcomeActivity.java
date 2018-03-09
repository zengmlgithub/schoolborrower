package com.sharebookcare.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sharebookcare.R;
import com.sharebookcare.common.AppUtil;


public class WelcomeActivity extends BaseActivity{
    private static final String TAG = WelcomeActivity.class.getSimpleName();
    private Handler handler;
    private static final int MSG_START_MAIN = 0x100;
    private static final int DELLAY = 3000;
    @Override
    protected void initListener() {

    }

    @Override
    protected void setData() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                AppUtil.startActivity(WelcomeActivity.this,MainActivity.class);
                finish();

            }
        };

        handler.sendEmptyMessageDelayed(MSG_START_MAIN,DELLAY);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }
}
