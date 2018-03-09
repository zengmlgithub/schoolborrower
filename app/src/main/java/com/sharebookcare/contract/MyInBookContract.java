package com.sharebookcare.contract;

import com.sharebookcare.bean.Book;

import java.util.ArrayList;
import java.util.List;


public interface MyInBookContract {
    interface View{
        void getMyInBookSuccess(List<Book> myInBookList);
        void getMyInBookFailure(String msg);
    }

    interface Presenter{
        void getMyInBook(Book book);
    }
}
