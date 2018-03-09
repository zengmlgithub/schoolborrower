package com.sharebookcare.contract;

import com.sharebookcare.bean.Book;
import com.sharebookcare.bean.BookTask;

import java.util.ArrayList;


public interface UserCenterContract {
    interface View{
        void updateMyBooks(ArrayList<Book> list);
        void updateOutBooks(ArrayList<Book> list);
        void updateInBooks(ArrayList<Book> list);
        void updateInTask(ArrayList<BookTask> list);
    }

    interface Presenter{

        void getMyBooks();

        void getInTask();

        void getOutBook();

        void getInBook();

    }
}
