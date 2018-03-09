package com.sharebookcare.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.sharebookcare.R;
import com.sharebookcare.fragments.BaseFragment;
import com.sharebookcare.fragments.BorrowFragment;
import com.sharebookcare.fragments.CenterFragment;
import com.sharebookcare.fragments.AllBookFagment;
import com.sharebookcare.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private int currentPage = 0;

    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentList = new ArrayList<>();
        BaseFragment homeFragment = new HomeFragment();
        BaseFragment borrowFragment = new BorrowFragment();
        BaseFragment centerFragment = new CenterFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(borrowFragment);
        fragmentList.add(centerFragment);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.i(TAG,"pageselected:" + position);
                int menuId = 0;
                switch (position){
                    case 0:
                        menuId = R.id.navigation_home;
                        break;
                    case 1:
                        menuId = R.id.navigation_dashboard;
                        break;
                    case 2:
                        menuId = R.id.navigation_notifications;
                }
                navigation.setSelectedItemId(menuId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initView() {
        viewPager = findViewById(R.id.viewpage);
    }


    @Override
    public void onClick(View view) {

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };



}
