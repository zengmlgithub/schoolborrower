package com.sharebookcare.fragments;

import android.view.View;
import android.widget.Button;

import com.sharebookcare.R;
import com.sharebookcare.activity.LoginActivity;
import com.sharebookcare.common.AppUtil;


public class NotLoginFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = NotLoginFragment.class.getCanonicalName();
    private Button loginBtn;

    @Override
    protected void initListener() {
        loginBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        loginBtn = view.findViewById(R.id.login_btn);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_not_login;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == loginBtn.getId()){
            AppUtil.startActivity(getActivity(), LoginActivity.class);
        }
    }
}
