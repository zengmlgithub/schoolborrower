package com.sharebookcare.presenter;

import android.util.Log;

import com.sharebookcare.bean.Book;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.bean.MyUser;
import com.sharebookcare.common.AppUtil;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.UserCenterContract;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class CenterPresenter implements UserCenterContract.Presenter {

    private static final String TAG = CenterPresenter.class.getSimpleName();
    private UserCenterContract.View view;

    public CenterPresenter(UserCenterContract.View view) {
        this.view = view;
    }


    @Override
    public void getMyBooks() {
        Log.i(TAG,"getMyOutBooks~~~~");
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Book> bookQuery = new BmobQuery<>();
        bookQuery.addWhereEqualTo("owner",new BmobPointer(user));
        bookQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e != null || list == null || list.size() == 0){
                    view.updateMyBooks(null);
                }else{
                    ArrayList<Book> allList = new ArrayList<>();
                    allList.addAll(list);
                    view.updateMyBooks(allList);
                }
            }
        });
    }

    @Override
    public void getInTask() {
        Log.i(TAG,"getMyInTask~~~~~");
        MyUser user = UserState.getUser();
        BmobQuery<BookTask> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("sender",new BmobPointer(user));
        bmobQuery.include("sender");
        bmobQuery.include("receiver");
        bmobQuery.findObjects(new FindListener<BookTask>() {
            @Override
            public void done(List<BookTask> list, BmobException e) {
                if (e != null || list == null || list.size() <= 0){
                    view.updateInTask(null);
                }else{
                    ArrayList<BookTask> taskList = new ArrayList<>();
                    AppUtil.printBookTaskList(list);
                    taskList.addAll(list);
                    view.updateInTask(taskList);
                }
            }
        });
    }

    @Override
    public void getOutBook() {
        Log.i(TAG,"getMyInBoos~~~~");
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Book> bookQuery = new BmobQuery<>();
        bookQuery.addWhereEqualTo("owner",new BmobPointer(user));
        bookQuery.addWhereEqualTo("out",1);
        bookQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e != null || list == null || list.size() == 0){
                    view.updateOutBooks(null);
                }else{
                    ArrayList<Book> outList = new ArrayList<>();
                    outList.addAll(list);
                    view.updateOutBooks(outList);
                }
            }
        });
    }

    @Override
    public void getInBook() {
        Log.i(TAG,"getMyInBoos~~~~");
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Book> bookQuery = new BmobQuery<>();
        bookQuery.addWhereEqualTo("outer",new BmobPointer(user));
        bookQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e != null || list == null || list.size() == 0){
                    view.updateInBooks(null);
                }else{
                    ArrayList<Book> inList = new ArrayList<>();
                    inList.addAll(list);
                    view.updateInBooks(inList);
                }
            }
        });
    }
}
