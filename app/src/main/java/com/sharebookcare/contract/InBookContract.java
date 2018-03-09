package com.sharebookcare.contract;

import com.sharebookcare.bean.BookTask;



public interface InBookContract {
    interface View{
        void checkoutSuccess();
        void checkoutFailure(String msg);
    }

    interface Presenter{
        void checkout(BookTask bookTask);
    }
}
