package com.sharebookcare.presenter;

import android.util.Log;

import com.sharebookcare.bean.MyUser;
import com.sharebookcare.contract.LoginContract;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.View view;
    MyUser user ;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void loginOrRegister(final String username, final String password,final String tel) {
        user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setMobilePhoneNumber(tel);
        user.setMoney(100);
        user.signUp(new SaveListener<MyUser>() {

            @Override
            public void done(MyUser bmobUser, BmobException e) {
                if (e == null){
                    view.loginSuccess();
                }else{
                    String errorMsg = e.getMessage();
                    Log.i(TAG,e.toString());
                    //TODO:注册失败
                    if (e.getErrorCode() == 202){
                        login(username,password);
                    }else{
                        view.loginFailure(errorMsg);
                    }

                }
            }
        });
    }

    private void login(String username,String password){
        user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null){
                    view.loginSuccess();
                }else{
                    view.loginFailure(e.getMessage());
                }
            }
        });
    }
}
