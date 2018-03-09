package com.sharebookcare.presenter;

import com.sharebookcare.activity.InBookActivity;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.contract.InBookContract;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class InBookPresenter implements InBookContract.Presenter {
    private InBookContract.View view;
    public InBookPresenter(InBookContract.View view) {
        this.view = view;
    }

    @Override
    public void checkout(BookTask bookTask) {
        if (bookTask != null){
            bookTask.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        view.checkoutSuccess();
                    }else{
                        view.checkoutFailure(e.getMessage());
                    }
                }
            });
        }
    }
}
