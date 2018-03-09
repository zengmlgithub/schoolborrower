package com.sharebookcare.contract;

import com.sharebookcare.bean.BookTask;

import java.util.List;


public interface OperatorTaskContract {
    interface View{
       void cancelSuccess(List<BookTask> list);
       void cancelFailure();


       void receiveSuccess(List<BookTask> list);
       void receiveFailure();


    }

    interface Presenter{
        void cancelBookTask(BookTask task);
        void receiveBookTask(BookTask task);
        void updateMoney(BookTask task);
    }
}
