package com.sharebookcare.presenter;

import android.util.Log;

import com.sharebookcare.bean.Book;
import com.sharebookcare.contract.HomeContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getCanonicalName();
    private HomeContract.View view;

    public HomePresenter(HomeContract.View view){
        this.view = view;
    }

    @Override
    public void getRecommandList() {

        Log.i(TAG,"getRecommandList~~~~~");

        BmobQuery<Book> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("recommand","1");
        bmobQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e == null){
                    if (list != null && list.size() > 0) {
                        Log.i(TAG,"getRecommandSize:" + list.size());
                        ArrayList<Book> bookArrayList = new ArrayList<>();
                        bookArrayList.addAll(list);
                        view.getRecommandSuccess(bookArrayList);
                    }else{
                        Log.e(TAG,e.toString());
                    }
                }else{
                    Log.e(TAG,e.toString());
                }
            }
        });

    }

    @Override
    public void getLatestList() {
        Log.i(TAG,"getLatestList~~~~");
        final String startString = getStartString();
        BmobQuery<Book> BmobQuery = new BmobQuery<>();
        BmobQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e == null){
                    List<Book> updateList = new ArrayList<>();
                    if (list != null && list.size() > 0){
                        for (Book book : list){
                            if (book.getCreatedAt().compareTo(startString) > 0){
                                updateList.add(book);
                            }
                        }

                        if (updateList.size() > 0){
                            ArrayList<Book> bookArrayList = new ArrayList<>();
                            bookArrayList.addAll(list);
                            view.getLatestSuccess(bookArrayList);
                        }
                    }

                }else{
                    Log.i(TAG,e.toString());
                }
            }
        });
    }

    @Override
    public void getAllList() {
        Log.i(TAG,"getAllList~~~~");
        BmobQuery<Book> BmobQuery = new BmobQuery<>();
        BmobQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e == null){
                    if (list != null && list.size() > 0) {
                        ArrayList<Book> myAllList = new ArrayList<>();
                        myAllList.addAll(list);
                        view.getAllSuccess(myAllList);
                    }

                }else{
                    Log.i(TAG,e.toString());
                }
            }
        });
    }

    private String getStartString() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-2);
        SimpleDateFormat sj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startStr = sj.format(calendar.getTime());
        Log.i(TAG,"get start str:" + startStr);
        return startStr;
    }
}
