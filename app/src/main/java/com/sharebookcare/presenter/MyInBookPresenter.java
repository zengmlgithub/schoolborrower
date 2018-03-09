package com.sharebookcare.presenter;

import com.sharebookcare.bean.Book;
import com.sharebookcare.bean.MyUser;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.MyInBookContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class MyInBookPresenter implements MyInBookContract.Presenter {
    private MyInBookContract.View view;
    public MyInBookPresenter(MyInBookContract.View view) {
        this.view = view;
    }

    @Override
    public void getMyInBook(Book book) {
        book.setOut(0);
        MyUser user = new MyUser();
        book.setOuter(user);
        book.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    updateList();
                }else{
                    view.getMyInBookFailure(e.getMessage());
                }
            }
        });
    }

    private void updateList(){
        MyUser user = UserState.getUser();
        BmobQuery<Book> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("outer", new BmobPointer(user));
        bmobQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e == null){
                    view.getMyInBookSuccess(list);
                }else{
                    view.getMyInBookFailure(e.getMessage());
                }
            }
        });
    }
}
