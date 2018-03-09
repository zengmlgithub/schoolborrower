package com.sharebookcare.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sharebookcare.R;
import com.sharebookcare.activity.DetailActivity;
import com.sharebookcare.adapter.BookAdapter;
import com.sharebookcare.bean.Book;
import com.sharebookcare.common.MyToast;
import com.sharebookcare.contract.AllBookContract;
import com.sharebookcare.presenter.AllBookPresenter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;


public class AllBookFagment extends BaseFragment implements AllBookContract.View{

    private static final String TAG = AllBookFagment.class.getSimpleName();

    private ListView bookRv;
    private BookAdapter adapter;
    private AllBookContract.Presenter presenter;

    private TextView emptyView;

    private AVLoadingIndicatorView loadingView;
    @Override
    protected void initListener() {
        bookRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = adapter.getItem(i);
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book",book);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new AllBookPresenter(this);
        adapter = new BookAdapter(getActivity());
        bookRv.setAdapter(adapter);
//        bookRv.setEmptyView(emptyView);
    }

    @Override
    protected void initView(View view) {
        bookRv = view.findViewById(R.id.books_rv);
        emptyView = view.findViewById(R.id.empty_tv);
        loadingView = view.findViewById(R.id.loading);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_allbook;
    }



    @Override
    public void onResume() {
        super.onResume();
        presenter.getBooks();
        loadingView.show();
    }

    @Override
    public void updateBooks(List<Book> bookList) {
        if (bookList != null) {
            Log.i(TAG, "updateBooks:" + bookList.size());
            adapter.updateData(bookList);
        }else{
            emptyView.setVisibility(View.VISIBLE);
        }
        loadingView.hide();
    }

    @Override
    public void queryFailure(String msg) {
        MyToast.showToast(getActivity(),msg);
    }

}
