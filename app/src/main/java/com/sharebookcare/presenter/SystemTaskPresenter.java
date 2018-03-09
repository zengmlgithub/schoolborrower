package com.sharebookcare.presenter;

import com.sharebookcare.bean.BookTask;
import com.sharebookcare.bean.MyUser;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.SystemTaskContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class SystemTaskPresenter implements SystemTaskContract.Presenter {

    private SystemTaskContract.View view;
    private static final String TAG = SystemTaskPresenter.class.getSimpleName();

    public SystemTaskPresenter(SystemTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void getAllTask() {
        BmobQuery<BookTask> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<BookTask>() {
            @Override
            public void done(List<BookTask> list, BmobException e) {
                if (e == null && list != null && list.size() > 0){
                    view.updateTask(list);
                }else{
                    view.updateTask(null);
                }
            }
        });
    }

    @Override
    public void accessTask(BookTask task) {
        MyUser receiver = UserState.getUser();
        task.setReceiver(receiver);
        task.setReceived(1);
        task.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    view.accessSuccess();
                }else{
                    view.accessFailure(e.getMessage());
                }
            }
        });
    }
}
