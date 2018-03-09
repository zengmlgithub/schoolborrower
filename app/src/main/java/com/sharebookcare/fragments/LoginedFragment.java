package com.sharebookcare.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sharebookcare.R;
import com.sharebookcare.activity.BookActivity;
import com.sharebookcare.activity.DetailActivity;
import com.sharebookcare.activity.InBookActivity;
import com.sharebookcare.activity.LoginActivity;
import com.sharebookcare.activity.MyInBookActivity;
import com.sharebookcare.activity.OutBookActivity;
import com.sharebookcare.activity.TaskActivity;
import com.sharebookcare.bean.Book;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.bean.MyUser;
import com.sharebookcare.common.AppUtil;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.UserCenterContract;
import com.sharebookcare.presenter.CenterPresenter;

import java.util.ArrayList;

import cn.bmob.v3.BmobUser;

/**
 * Created by administrator on 18/3/5.
 */

public class LoginedFragment extends BaseFragment implements UserCenterContract.View, View.OnClickListener{

    private static final String TAG = LoginedFragment.class.getSimpleName();

    private TextView nameTv;
    private TextView moneyTv;

    private TextView allBookTv; //所有读书
    private TextView inTaskTv; //求书
    private TextView outBookTv; //已借出书籍
    private TextView inBookTv;//已含借入书籍


    private Button outBookBtn;
    private Button inBookBtn;
    private Button logoutBtn;

    private ArrayList<Book> allBookList ; //所有读书
    private ArrayList<BookTask> allTaskList; //发布的任务
    private ArrayList<Book> allOutList;//所有借出的书
    private ArrayList<Book> allInList; //借入的书

    private LinearLayout allBookLl;
    private LinearLayout allTaskLl;
    private LinearLayout allOutLl;
    private LinearLayout allInLl;


    private UserCenterContract.Presenter presenter;

    @Override
    protected void initListener() {
        outBookBtn.setOnClickListener(this);
        inBookBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

        allBookTv.setOnClickListener(this);
        inTaskTv.setOnClickListener(this);
        outBookTv.setOnClickListener(this);
        inBookTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        MyUser user = UserState.getUser();
        presenter = new CenterPresenter(this);
        if (user != null){
            nameTv.setText(user.getUsername());
            Log.i(TAG,"money:" + user.getMoney());
            moneyTv.setText("书币：" + user.getMoney() + "个");
        }
    }

    @Override
    protected void initView(View view) {
        nameTv = view.findViewById(R.id.name_tv);
        moneyTv = view.findViewById(R.id.money_tv);

        allBookTv = view.findViewById(R.id.all_book_tv);
        inTaskTv = view.findViewById(R.id.in_task_tv);
        outBookTv = view.findViewById(R.id.out_num_tv);
        inBookTv = view.findViewById(R.id.in_num_tv);

        outBookBtn = view.findViewById(R.id.outBook_Btn);
        inBookBtn = view.findViewById(R.id.inBook_Btn);
        logoutBtn = view.findViewById(R.id.logout_Btn);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_center;
    }

    @Override
    public void updateMyBooks(ArrayList<Book> list) {
        if (list != null && list.size() > 0) {
            allBookTv.setText("图书：" + list.size() + "本");
            allBookList = list;
        }else{
            allBookTv.setText("图书：" + 0 + "本");
        }
    }

    @Override
    public void updateInBooks(ArrayList<Book> list) {
        if (list != null && list.size() > 0) {
            inBookTv.setText("借入：" + list.size() + "本");
            allInList = list;
        }else{
            inBookTv.setText("借入：" + 0 + "本");
        }
    }

    @Override
    public void updateInTask(ArrayList<BookTask> list) {
        if (list != null && list.size() > 0) {
            inTaskTv.setText("求书：" + list.size() + "本");
            allTaskList = list;
        }else{
            inTaskTv.setText("求书：" + 0 + "本");
        }
    }

    @Override
    public void updateOutBooks(ArrayList<Book> list) {
        if (list != null && list.size() > 0) {
            outBookTv.setText("借出：" + list.size() + "本");
            allOutList = list;
        }else{
            outBookTv.setText("借出：" + 0 + "本");
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == logoutBtn.getId()){
            BmobUser.logOut();
            AppUtil.startActivity(getActivity(),LoginActivity.class);
        }else if (view.getId() == outBookBtn.getId()){
            AppUtil.startActivity(getActivity(),OutBookActivity.class);
        }else if (view.getId() == inBookBtn.getId()){
            AppUtil.startActivity(getActivity(),InBookActivity.class);
        }else if (view.getId() == allBookTv.getId()){
            Log.i(TAG,"allBookTv click");
            Bundle bundle = new Bundle();
            if (allBookList != null && allBookList.size() > 0) {
                bundle.putSerializable(BookActivity.TITLE,"个人图书");
                bundle.putSerializable(BookActivity.BOOK_KEY, allBookList);
                AppUtil.startActivity(getActivity(), BookActivity.class, bundle);
            }
        }else if (view.getId() == inTaskTv.getId()){
            Bundle bundle = new Bundle();
            if (allTaskList != null && allTaskList.size() > 0) {
                bundle.putSerializable(TaskActivity.TITLE,"个人求书");
                bundle.putSerializable(TaskActivity.BOOK_TASK_LIST, allTaskList);
                AppUtil.startActivity(getActivity(), TaskActivity.class, bundle);
            }
        }else if (view.getId() == outBookTv.getId()){
            Log.i(TAG,"outBookTv click");
            Bundle bundle = new Bundle();
            if (allOutList != null && allOutList.size() > 0) {
                bundle.putSerializable(BookActivity.TITLE,"借出图书");
                bundle.putSerializable(BookActivity.BOOK_KEY, allOutList);
                AppUtil.startActivity(getActivity(), BookActivity.class, bundle);
            }
        }else if (view.getId() == inBookTv.getId()){
            Log.i(TAG,"inBookTv click");
            Bundle bundle = new Bundle();
            if (allInList != null && allInList.size() > 0) {
                bundle.putSerializable(BookActivity.TITLE,"借入图书");
                bundle.putSerializable(BookActivity.BOOK_KEY, allInList);
                AppUtil.startActivity(getActivity(), MyInBookActivity.class, bundle);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserState.isLogined()){
            presenter.getMyBooks();
            presenter.getInTask();
            presenter.getOutBook();
            presenter.getInBook();
        }
    }
}
