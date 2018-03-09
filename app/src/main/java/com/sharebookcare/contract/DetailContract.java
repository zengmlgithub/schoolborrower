package com.sharebookcare.contract;

import com.sharebookcare.bean.Book;


public interface DetailContract {
    interface View{
        void borrowSuccess();
        void borrowFailure(String msg);
    }
    interface Presenter{
        void borrow(Book book);
    }
}
