package com.sharebookcare.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.sharebookcare.R;
import com.sharebookcare.common.UserState;


public class CenterFragment extends BaseFragment {
    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager manager = getChildFragmentManager();
        boolean logined = UserState.isLogined();
        FragmentTransaction fragmentTransaction =  manager.beginTransaction();
        if (logined){
            fragmentTransaction.replace(R.id.container,new LoginedFragment());
        }else{
            fragmentTransaction.replace(R.id.container,new NotLoginFragment());
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_user_center;
    }
}
