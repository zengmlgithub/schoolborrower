package com.sharebookcare.contract;

import cn.bmob.v3.BmobUser;


public interface LoginContract {

    interface View{
        void loginSuccess();
        void loginFailure(String msg);
    }

    interface Presenter{
        void loginOrRegister(String name,String password,String tel);
    }
}
