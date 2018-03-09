package com.sharebookcare.contract;

import com.sharebookcare.bean.Book;

import java.util.List;


public interface AllBookContract {
    interface View{
        void updateBooks(List<Book> bookList);
        void queryFailure(String msg);
    }
    interface Presenter{
        void getBooks();
    }
}
