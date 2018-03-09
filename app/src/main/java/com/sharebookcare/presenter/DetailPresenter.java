package com.sharebookcare.presenter;

import android.util.Log;

import com.sharebookcare.bean.Book;
import com.sharebookcare.common.CommonUtil;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.DetailContract;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


public class DetailPresenter implements DetailContract.Presenter {

    private static final String TAG = DetailPresenter.class.getSimpleName();
    private DetailContract.View view;

    public DetailPresenter(DetailContract.View view) {
        this.view = view;
    }

    @Override
    public void borrow(Book book) {
        BmobUser user = UserState.getUser();
        if (user != null && book != null) {
            Log.i(TAG,"borrow--->book:" + book.toString());
            book.setOuter(user);
            book.setOut(CommonUtil.IS_OUT);
            Log.i(TAG,"borrow--->book:" + book.toString());
            book.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null ){
                        view.borrowSuccess();

                    }else{
                        view.borrowFailure(e.getMessage());
                    }
                }
            });
        }
    }
}
