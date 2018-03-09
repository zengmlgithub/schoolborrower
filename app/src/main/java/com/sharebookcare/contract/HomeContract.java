package com.sharebookcare.contract;

import com.sharebookcare.bean.Book;

import java.util.ArrayList;


public interface HomeContract {
    interface View{
        void getRecommandSuccess(ArrayList<Book> recommandList);
        void getLatestSuccess(ArrayList<Book> latestList);
        void getAllSuccess(ArrayList<Book> allList);
    }

    interface Presenter{
        void getRecommandList();
        void getLatestList();
        void getAllList();
    }
}
