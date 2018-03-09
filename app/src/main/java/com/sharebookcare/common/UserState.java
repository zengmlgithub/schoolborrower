package com.sharebookcare.common;

import com.sharebookcare.bean.MyUser;

import cn.bmob.v3.BmobUser;


public class UserState {
    public static MyUser getUser(){
        return BmobUser.getCurrentUser(MyUser.class);
    }

    public static boolean isLogined(){
        BmobUser bmobUser = getUser();
        return bmobUser != null;
    }
}
