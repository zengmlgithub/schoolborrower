package com.sharebookcare.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.sharebookcare.bean.Book;
import com.sharebookcare.bean.MyUser;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.OutBookContract;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


public class OutBookPresenter implements OutBookContract.Presenter {
    private static final String TAG = OutBookPresenter.class.getSimpleName();
    private OutBookContract.View view;
    public OutBookPresenter(OutBookContract.View view ) {
        this.view = view;
    }

    @Override
    public void upload(final Book book,String filePath) {
        Log.i(TAG,"upload file start~~~~~");
        if (TextUtils.isEmpty(filePath)){
            Log.e(TAG,"filePath is null");
            view.uploadfailure("图片文件找不到");
            return;
        }else{
            final BmobFile bmobFile = new BmobFile(new File(filePath));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    Log.i(TAG,"upload file end~~~~~");
                    if (e == null){
                        Log.i(TAG,"book file is " + bmobFile.getUrl());
                        Log.i(TAG,"book file is " + bmobFile.getFileUrl());

                        book.setImg(bmobFile.getFileUrl());
                    }else{
                        Log.e(TAG,e.toString());
                    }

                    uploadNoFile(book);
                }
            });
        }
    }

    private void uploadNoFile(Book book){
        Log.i(TAG,"upload No File~~~~~~");
        MyUser user = UserState.getUser();
        book.setOwner(user);
        book.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null){
                    view.uploadSuccess();
                }else{
                    view.uploadfailure(e.getMessage());
                }
            }
        });
    }
}
