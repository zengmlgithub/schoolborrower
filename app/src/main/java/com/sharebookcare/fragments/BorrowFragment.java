package com.sharebookcare.fragments;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.activity.LoginActivity;
import com.sharebookcare.activity.SystemTaskActivity;
import com.sharebookcare.adapter.TaskAdapter;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.common.MyToast;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.SystemTaskContract;
import com.sharebookcare.presenter.SystemTaskPresenter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class BorrowFragment extends BaseFragment implements SystemTaskContract.View {

    private static final String TAG = SystemTaskActivity.class.getSimpleName();

    private ListView taskList;
    private TaskAdapter adapter;
    private SystemTaskContract.Presenter presenter;
    private TextView emptyView;

    private AVLoadingIndicatorView loadingView;

    @Override
    protected void initListener() {
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG,"onItem:" + i);
                if (!UserState.isLogined()){
                    try{
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                    }catch (Exception e){
                        Log.e(TAG,e.toString());
                    }
                }else{
                    presenter.accessTask(adapter.getItem(i));
                    loadingView.show();
                }
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new TaskAdapter(getActivity());
        taskList.setAdapter(adapter);
//        taskList.setEmptyView(emptyView);
        presenter = new SystemTaskPresenter(this);
    }

    @Override
    protected void initView(View view) {
        taskList = view.findViewById(R.id.task_lv);
        emptyView = view.findViewById(R.id.empty_tv);
        loadingView = view.findViewById(R.id.loading);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_system;
    }

    @Override
    public void updateTask(List<BookTask> taskList) {
        List<BookTask> goodTask = getGood(taskList);
        adapter.updateData(goodTask);

        if (goodTask == null){
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
        }

        loadingView.hide();
    }

    private List<BookTask> getGood(List<BookTask> taskList) {
        if (taskList == null || taskList.size() <= 0){
            return null;
        }else{
            ArrayList<BookTask> goodList = new ArrayList<>();
            for (BookTask task:taskList){
                Log.i(TAG,"task.name=" + task.getName() + ",task.recieved=" + task.getReceived());
                if (task != null && task.getReceived() == 0){
                    goodList.add(task);
                }
            }
            if (goodList.size() <= 0){
                return null ;
            }else{
                return goodList;
            }
        }

    }

    @Override
    public void accessSuccess() {
        MyToast.showToast(getActivity(),R.string.access_success);
        presenter.getAllTask();
    }

    @Override
    public void accessFailure(String msg) {
        MyToast.showToast(getActivity(),msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getAllTask();
        loadingView.show();
    }
}
