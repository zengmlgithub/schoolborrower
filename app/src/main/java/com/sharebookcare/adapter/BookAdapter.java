package com.sharebookcare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharebookcare.R;
import com.sharebookcare.bean.Book;
import com.sharebookcare.common.AppUtil;
import com.sharebookcare.common.CommonUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;


public class BookAdapter extends BaseAdapter {
    private static final String TAG = "bookadapter";

    private List<Book> bookList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public BookAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.item_book, null);
            holder = new ViewHolder();
            holder.nameTv = view.findViewById(R.id.name_tv);
            holder.authorTv = view.findViewById(R.id.author_tv);
            holder.publisherTv = view.findViewById(R.id.publish_tv);
            holder.outState = view.findViewById(R.id.out_state);
            holder.imgIv = view.findViewById(R.id.book_iv);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Book book = getItem(i);
        if (book != null){
            int isOut = book.getOut();
            holder.nameTv.setText(book.getTitle());
            holder.authorTv.setText(book.getAuthor());
            holder.publisherTv.setText(book.getPublisher());
            if (isOut == CommonUtil.NOT_OUT){
                holder.outState.setText(R.string.not_out);
                holder.outState.setTextColor(context.getResources().getColor(R.color.color_515151));
            }else if (isOut == CommonUtil.IS_OUT){
                holder.outState.setText(R.string.outed);
                holder.outState.setTextColor(context.getResources().getColor(R.color.color_00FF00));
            }

            String path = book.getImg();
            Picasso.with(context).load(path).into(holder.imgIv);
        }

        return view;
    }

    static class ViewHolder {
        TextView nameTv;
        TextView authorTv;
        TextView publisherTv;
        TextView outState;
        ImageView imgIv;
    }

    public void updateData(List<Book> bookList) {
        this.bookList = bookList;
//        AppUtil.printBookList(bookList);
        notifyDataSetChanged();
    }


}
