package com.sharebookcare.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.common.MyToast;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.LoginContract;
import com.sharebookcare.presenter.LoginPresenter;

import cn.bmob.v3.BmobUser;


public class LoginActivity extends BaseActivity implements LoginContract.View, View.OnClickListener{
    private static final String TAG = "loginactivity";
    private EditText nameEt;
    private EditText passwordEt;
    private EditText telEt;
    private Button submitBtn;
    private LoginContract.Presenter presenter;
    @Override
    protected void initListener() {
        submitBtn.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        presenter = new LoginPresenter(this);
        setTitle(R.string.login_or_register);
    }

    @Override
    protected void initView() {
        nameEt = findViewById(R.id.name_et);
        passwordEt = findViewById(R.id.password_et);
        telEt = findViewById(R.id.tel_et);
        submitBtn = findViewById(R.id.submit_btn);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void loginSuccess() {
        MyToast.showToast(this,R.string.loginSuccess);
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == submitBtn.getId()){
            String nameStr = nameEt.getText().toString();
            String passwordStr = passwordEt.getText().toString();
            String telStr = telEt.getText().toString();
            String checkStr = checkoutString(nameStr,passwordStr,telStr);
            if (TextUtils.isEmpty(checkStr)){
                presenter.loginOrRegister(nameStr,passwordStr,telStr);
            }else{
                Toast.makeText(this,checkStr,Toast.LENGTH_LONG).show();
            }
        }
    }

    private String checkoutString(String name,String password,String tel){
        Log.i(TAG,"checkoutString~~~name:" + name + ",password:" + password);
        StringBuilder builder = new StringBuilder();
        if (TextUtils.isEmpty(name)){
            builder.append(getResources().getString(R.string.name_empty));
        }

        if (TextUtils.isEmpty(password)){
            if (builder.length() > 0){
                builder.append("\n");
            }
           builder.append(getResources().getString(R.string.password_empty));
        }else if (password.length() < 6){
            if (builder.length() > 0){
                builder.append("\n");
            }
            builder.append(getResources().getString(R.string.password_not_enough));
        }

        if (TextUtils.isEmpty(tel)){
            if (builder.length() > 0){
                builder.append("\n");
            }
            builder.append(getResources().getString(R.string.tel_empty));
        }else if (tel.length() < 11){
            if (builder.length() > 0){
                builder.append("\n");
            }
            builder.append(getResources().getString(R.string.tel_not_enough));
        }

        String result = builder.toString();
        return result;
    }
}
