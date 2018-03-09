package com.sharebookcare.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    protected  View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(),null);
        initView(view);
        initData();
        initListener();
        return view;
    }

    protected abstract void initListener();

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract int getLayout();


}
