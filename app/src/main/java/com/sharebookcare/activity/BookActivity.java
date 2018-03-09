package com.sharebookcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.adapter.BookAdapter;
import com.sharebookcare.bean.Book;
import com.sharebookcare.contract.AllBookContract;
import com.sharebookcare.presenter.AllBookPresenter;

import java.util.ArrayList;
import java.util.List;


public class BookActivity extends BaseActivity implements AllBookContract.View {

    private static final String TAG = BookActivity.class.getSimpleName();

    private ListView bookRv;
    private BookAdapter adapter;
    private AllBookContract.Presenter presenter;
    private ArrayList<Book> books;
    private String title;

    public static final String BOOK_KEY = "booklist";
    public static final String TITLE = "title";

    @Override
    protected void initListener() {
        bookRv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = adapter.getItem(i);
                Intent intent = new Intent(BookActivity.this,DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book",book);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void setData() {
//        setTitle(R.string.all_book);
//        presenter = new AllBookPresenter(this);
        getIntentData();
        adapter = new BookAdapter(this);
        bookRv.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        bookRv = findViewById(R.id.books_rv);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_allbook;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        presenter.getBooks();

        updateBooks(books);
        setTitle(title);
    }

    @Override
    public void updateBooks(List<Book> bookList) {
        if (bookList != null) {
            Log.i(TAG, "updateBooks:" + bookList.size());
        }
        adapter.updateData(bookList);
    }

    @Override
    public void queryFailure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }



    private void getIntentData(){
        Intent intent = getIntent();
        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                books = (ArrayList<Book>)bundle.getSerializable(BOOK_KEY);
                title = bundle.getString(TITLE);
            }
        }
    }
}
