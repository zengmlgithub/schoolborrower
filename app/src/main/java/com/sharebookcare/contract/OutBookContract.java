package com.sharebookcare.contract;

import com.sharebookcare.bean.Book;


public interface OutBookContract {
    interface View{
        void uploadSuccess();
        void uploadfailure(String msg);
    }

    interface Presenter{
        void upload(Book book,String filePath);
    }
}
