package com.sharebookcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.bean.Book;
import com.sharebookcare.common.CommonUtil;
import com.sharebookcare.contract.MyInBookContract;
import com.sharebookcare.presenter.MyInBookPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyInBookAdapter extends BaseAdapter implements MyInBookContract.View {
    private static final String TAG = "bookadapter";

    private List<Book> bookList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;
    private MyInBookContract.Presenter presenter;
    public MyInBookAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        presenter = new MyInBookPresenter(this);
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Book getItem(int i) {
        return bookList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_my_in_book, null);
            holder = new ViewHolder();
            holder.nameTv = view.findViewById(R.id.name_tv);
            holder.authorTv = view.findViewById(R.id.author_tv);
            holder.publisherTv = view.findViewById(R.id.publish_tv);
//            holder.outState = view.findViewById(R.id.out_state);
            holder.imgIv = view.findViewById(R.id.book_iv);
            holder.returnBtn = view.findViewById(R.id.return_book_btn);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        final Book book = getItem(i);
        if (book != null){
            int isOut = book.getOut();
            holder.nameTv.setText(book.getTitle());
            holder.authorTv.setText(book.getAuthor());
            holder.publisherTv.setText(book.getPublisher());
            if (isOut == CommonUtil.NOT_OUT){
//                holder.outState.setText(R.string.not_out);
//                holder.outState.setTextColor(context.getResources().getColor(R.color.color_515151));
                //此状态是不存在的。
                holder.returnBtn.setVisibility(View.GONE);
            }else if (isOut == CommonUtil.IS_OUT){
//                holder.outState.setText("已借入");
//                holder.outState.setTextColor(context.getResources().getColor(R.color.color_00FF00));
                holder.returnBtn.setVisibility(View.VISIBLE);
            }

            //TODO:实现还书功能
            holder.returnBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.getMyInBook(book);
                }
            });

            String path = book.getImg();
            Picasso.with(context).load(path).into(holder.imgIv);
        }

        return view;
    }

    @Override
    public void getMyInBookSuccess(List<Book> myInBookList) {
        bookList = myInBookList;
        notifyDataSetChanged();
    }

    @Override
    public void getMyInBookFailure(String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    static class ViewHolder {
        TextView nameTv;
        TextView authorTv;
        TextView publisherTv;
        ImageView imgIv;
        Button returnBtn;

    }

    public void updateData(List<Book> bookList) {
        this.bookList = bookList;
//        AppUtil.printBookList(bookList);
        notifyDataSetChanged();
    }


}
