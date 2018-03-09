package com.sharebookcare.common;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


public class MyToast {
    public static void showToast(Context activity, String msg){
        Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();
    }
    public static void showToast(Context activity, int msgId){
        Toast.makeText(activity,msgId,Toast.LENGTH_LONG).show();
    }
}
