package com.sharebookcare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sharebookcare.R;
import com.sharebookcare.bean.Book;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;


public class TaskAdapter extends BaseAdapter{
    private static final String TAG = TaskAdapter.class.getSimpleName();
    private List<BookTask> taskList = new ArrayList<>();
    private Context context;

    public TaskAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public BookTask getItem(int i) {
        return taskList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder ;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_task, null);
            holder = new ViewHolder();
            holder.nameTv = view.findViewById(R.id.name_tv);
            holder.authorTv = view.findViewById(R.id.author_tv);
            holder.publishTv = view.findViewById(R.id.publish_tv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        BookTask book = getItem(i);
        if (book != null){
            holder.nameTv.setText(book.getName());
            holder.authorTv.setText(book.getAuthor());
            holder.publishTv.setText(book.getPublish());
        }
        return view;
    }

    static class ViewHolder{
        TextView nameTv;
        TextView publishTv;
        TextView authorTv;
    }

    public void updateData(List<BookTask> list){
        taskList.clear();
        if (list != null) {
            taskList.addAll(list);
        }
        notifyDataSetChanged();
    }
}
