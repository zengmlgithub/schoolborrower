package com.sharebookcare.presenter;

import android.util.Log;

import com.sharebookcare.bean.Book;
import com.sharebookcare.contract.AllBookContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class AllBookPresenter implements AllBookContract.Presenter {
    private static final String TAG = AllBookPresenter.class.getCanonicalName();
    private AllBookContract.View view;

    public AllBookPresenter(AllBookContract.View view) {
        this.view = view;
    }

    @Override
    public void getBooks() {
        Log.i(TAG,"getBooks~~~~");
        BmobQuery<Book> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(100);

        //执行查询方法
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> bookList, BmobException e) {
                if (e == null) {
                    if (bookList != null && bookList.size() > 0) {
                        Log.i(TAG, "查询成功：共" + bookList.size() + "条数据。");
//                        printBooks(bookList);
                    }else{
                        Log.i(TAG,"查到0条记录");
                    }
                    view.updateBooks(bookList);
                } else {
                    view.queryFailure(e.getMessage());
                    Log.e(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void printBooks(List<Book> bookList) {
        for (Book book : bookList){
            if (book != null){
                Log.i(TAG,"book--->" + book.getImg());
            }
        }
    }
}
