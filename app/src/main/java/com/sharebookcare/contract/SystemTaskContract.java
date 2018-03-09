package com.sharebookcare.contract;

import com.sharebookcare.bean.BookTask;

import java.util.List;


public interface SystemTaskContract {
    interface View{
        void updateTask(List<BookTask> taskList);
        void accessSuccess();
        void accessFailure(String msg);
    }
    interface Presenter{
        void getAllTask();
        void accessTask(BookTask task);
    }
}
