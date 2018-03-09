package com.sharebookcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.sharebookcare.R;
import com.sharebookcare.adapter.OperatorTaskAdapter;
import com.sharebookcare.bean.BookTask;

import java.util.ArrayList;


public class TaskActivity extends BaseActivity {
    private ArrayList<BookTask> taskList;

    private OperatorTaskAdapter adapter;
    private ListView taskLv;
    private String title;
    public static final String BOOK_TASK_LIST = "book_task_list";
    public static final String TITLE = "title";

    @Override
    protected void initListener() {

    }

    @Override
    protected void setData() {
        getIntentData();
        setTitle(title);
        adapter = new OperatorTaskAdapter(this);
        taskLv.setAdapter(adapter);
        adapter.updateData(taskList);
    }

    @Override
    protected void initView() {
        taskLv = findViewById(R.id.task_lv);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_task;
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                title = (String) bundle.getSerializable(TITLE);
                taskList = (ArrayList<BookTask>)bundle.getSerializable(BOOK_TASK_LIST);
            }
        }

    }
}
