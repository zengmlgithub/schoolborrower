package com.sharebookcare.presenter;

import android.util.Log;

import com.sharebookcare.bean.BookTask;
import com.sharebookcare.bean.MyUser;
import com.sharebookcare.common.CommonUtil;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.OperatorTaskContract;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class OperatorPresenter implements OperatorTaskContract.Presenter {

    private static final String TAG = OperatorPresenter.class.getCanonicalName();

    private OperatorTaskContract.View view;


    public OperatorPresenter(OperatorTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void cancelBookTask(BookTask task) {
        Log.i(TAG,"cancelBookTask:" + task.toString());
        task.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    updateMyInBook();
                }else{
                    view.cancelFailure();
                }
            }
        });
    }

    @Override
    public void receiveBookTask(BookTask task) {
        Log.i(TAG,"cancelBookTask:" + task.toString());
        task.setReceived(CommonUtil.FINISH_OUT);
        task.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    updateMyInBook();
                }else{
                    view.receiveFailure();
                }
            }
        });
    }

    @Override
    public void updateMoney(BookTask bookTask) {
        //减少个人的money,增加收入
        MyUser sender = bookTask.getSender();
        MyUser receiver = bookTask.getReceiver();
        Log.i(TAG,sender.toString());
        Log.i(TAG,receiver.toString());

        try {
            int senderMoney = sender.getMoney();
            int receiverMoney = receiver.getMoney();


            sender.setMoney(senderMoney - 10);
            receiver.setMoney(receiverMoney + 10);

            bookTask.setSender(sender);
            bookTask.setReceiver(receiver);

            bookTask.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.i(TAG, "update money success");
                    } else {
                        Log.i(TAG, "update money failure");
                    }
                }
            });
        }catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }


    private void updateMyInBook(){
        BmobQuery<BookTask> bmobQuery = new BmobQuery<>();
        MyUser user = UserState.getUser();
        bmobQuery.addWhereEqualTo("sender", new BmobPointer(user));
        bmobQuery.findObjects(new FindListener<BookTask>() {
            @Override
            public void done(List<BookTask> list, BmobException e) {
                if (e == null){
                    view.cancelSuccess(list);
                }
            }
        });
    }
}
