package com.sharebookcare.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sharebookcare.R;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.OperatorTaskContract;
import com.sharebookcare.presenter.OperatorPresenter;

import java.util.ArrayList;
import java.util.List;

import static com.sharebookcare.common.CommonUtil.FINISH_OUT;
import static com.sharebookcare.common.CommonUtil.IS_OUT;
import static com.sharebookcare.common.CommonUtil.NOT_OUT;
import static com.sharebookcare.common.CommonUtil.RECEIVING_OUT;
import static com.sharebookcare.common.CommonUtil.START_OUT;


public class OperatorTaskAdapter extends BaseAdapter implements OperatorTaskContract.View{

    private static final String TAG = OperatorTaskAdapter.class.getSimpleName();
    private List<BookTask> taskList = new ArrayList<>();
    private Context context;
    private OperatorPresenter presenter;

    public OperatorTaskAdapter(Context context){
        this.context = context;
        presenter = new OperatorPresenter(this);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_operator_task, null);
            holder = new ViewHolder();
            holder.nameTv = view.findViewById(R.id.name_tv);
            holder.authorTv = view.findViewById(R.id.author_tv);
            holder.publishTv = view.findViewById(R.id.publish_tv);
            holder.receiverBtn = view.findViewById(R.id.receiver_btn);
            holder.cancelBtn = view.findViewById(R.id.cancel_btn);
            holder.finishTv = view.findViewById(R.id.finish_tv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final BookTask book = getItem(i);
        if (book != null){
            holder.nameTv.setText(book.getName());
            holder.authorTv.setText(book.getAuthor());
            holder.publishTv.setText(book.getPublish());

            int received  = book.getReceived();
            Log.i(TAG,"received:" + book.getName() + ",received:" + received);
            if (received == START_OUT){
                holder.cancelBtn.setVisibility(View.VISIBLE);
                holder.receiverBtn.setVisibility(View.GONE);
                holder.finishTv.setVisibility(View.GONE);
            }else if (received == RECEIVING_OUT){
                holder.cancelBtn.setVisibility(View.GONE);
                holder.receiverBtn.setVisibility(View.VISIBLE);
                holder.finishTv.setVisibility(View.GONE);
            }else if (received == FINISH_OUT){
                holder.cancelBtn.setVisibility(View.GONE);
                holder.receiverBtn.setVisibility(View.GONE);
                holder.finishTv.setVisibility(View.VISIBLE);
            }

            holder.receiverBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int money = UserState.getUser().getMoney();
                    if (money - 10 > 0) {
//                        presenter.updateMoney(book);
                        presenter.receiveBookTask(book);
                    }
                }
            });

            holder.cancelBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    presenter.cancelBookTask(book);
                }
            });


        }
        return view;
    }

    private void receiveCurrentTask(BookTask bookTask) {
        presenter.receiveBookTask(bookTask);
    }

    private void cancelBookTask(BookTask bookTask) {
        presenter.cancelBookTask(bookTask);
    }

    @Override
    public void cancelSuccess(List<BookTask> list) {
        this.updateData(list);
    }

    @Override
    public void cancelFailure() {
        Log.e(TAG,"cancelFailure~~~~~");
    }

    @Override
    public void receiveSuccess(List<BookTask> list) {
        this.updateData(list);
    }

    @Override
    public void receiveFailure() {
        Log.e(TAG,"receiveFailure~~~~~");
    }


    static class ViewHolder{
        TextView nameTv;
        TextView publishTv;
        TextView authorTv;
        Button receiverBtn;
        Button cancelBtn;
        TextView finishTv;
    }

    public void updateData(List<BookTask> list){
        taskList.clear();
        if (list != null) {
            taskList.addAll(list);
        }
        notifyDataSetChanged();
    }
}
