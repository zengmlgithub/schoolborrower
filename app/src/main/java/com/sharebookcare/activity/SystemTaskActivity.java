package com.sharebookcare.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.adapter.TaskAdapter;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.SystemTaskContract;
import com.sharebookcare.presenter.SystemTaskPresenter;

import java.util.List;


public class SystemTaskActivity extends BaseActivity implements SystemTaskContract.View {

    private static final String TAG = SystemTaskActivity.class.getSimpleName();

    private ListView taskList;
    private TaskAdapter adapter;
    private SystemTaskContract.Presenter presenter;

    @Override
    protected void initListener() {
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG,"onItem:" + i);
                if (!UserState.isLogined()){
                    try{
                        startActivity(new Intent(SystemTaskActivity.this,LoginActivity.class));
                    }catch (Exception e){
                        Log.e(TAG,e.toString());
                    }
                }else{
                    presenter.accessTask(adapter.getItem(i));
                }
            }
        });
    }

    @Override
    protected void setData() {
        adapter = new TaskAdapter(this);
        taskList.setAdapter(adapter);
        presenter = new SystemTaskPresenter(this);

    }

    @Override
    protected void initView() {
        taskList = findViewById(R.id.task_lv);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_system;
    }

    @Override
    public void updateTask(List<BookTask> taskList) {
        Log.i(TAG,"taskList.size=" + taskList.size());
        adapter.updateData(taskList);
    }

    @Override
    public void accessSuccess() {
        Toast.makeText(this,R.string.access_success,Toast.LENGTH_LONG).show();
        presenter.getAllTask();
    }

    @Override
    public void accessFailure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllTask();
    }
}
