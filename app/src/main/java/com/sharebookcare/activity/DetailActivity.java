package com.sharebookcare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.bean.Book;
import com.sharebookcare.common.AppUtil;
import com.sharebookcare.common.CommonUtil;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.DetailContract;
import com.sharebookcare.presenter.DetailPresenter;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.BmobUser;


public class DetailActivity extends BaseActivity implements View.OnClickListener,DetailContract.View {

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String BOOK_KEY = "book";

    private DetailPresenter presenter;
    private ImageView imgIv;
    private TextView nameTv;
    private TextView publishTv;
    private TextView outStateTv;
    private TextView summaryTv;
    private Button borrowBtn;
    private TextView authorTv;

    private LinearLayout borrowll;
    private Book book;
    @Override
    protected void initListener() {
        borrowBtn.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        Log.i(TAG,"setData~~~~");
        setTitle(R.string.book_detail);
        presenter = new DetailPresenter(this);
        initBook();
    }

    private void initBook() {
        Log.i(TAG,"initBook~~~~");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            book = (Book) bundle.getSerializable(BOOK_KEY);
            if (book != null) {
                Log.i(TAG,book.toString());
                Picasso.with(DetailActivity.this).load(book.getImg()).into(imgIv);
                nameTv.setText(book.getTitle());
                publishTv.setText(book.getPublisher());
                setOutState(book.getOut(),outStateTv);
                summaryTv.setText(book.getSummary());
                setBorrowState(book.getOut(),borrowll);
                authorTv.setText(book.getAuthor());
            }
        }
    }

    private void setBorrowState(Integer out, LinearLayout borrowll) {
        Log.i(TAG,"setBoolowState~~~~~");
        if (out == CommonUtil.IS_OUT){
            borrowll.setVisibility(View.GONE);
        }else if (out == CommonUtil.NOT_OUT){
            borrowll.setVisibility(View.VISIBLE);
        }else{
            borrowll.setVisibility(View.GONE);
        }
    }

    private void setOutState(Integer out, TextView outStateTv) {
        Log.i(TAG,"setOutState~~~~~");
        if (out == CommonUtil.IS_OUT){
            outStateTv.setText(R.string.outed);
            outStateTv.setTextColor(getResources().getColor(R.color.color_00FF00));
        }else if (out == CommonUtil.NOT_OUT){
            outStateTv.setText(R.string.not_out);
        }else{
            outStateTv.setText(R.string.unknown_state);
        }
    }

    @Override
    protected void initView() {
        Log.i(TAG,"initView~~~~~");
        imgIv = findViewById(R.id.img);
        nameTv = findViewById(R.id.name_tv);
        publishTv = findViewById(R.id.publish_tv);
        outStateTv = findViewById(R.id.outstate_tv);
        summaryTv = findViewById(R.id.summary_tv);
        borrowBtn = findViewById(R.id.borrow_btn);
        borrowll = findViewById(R.id.borrow_ll);
        authorTv = findViewById(R.id.author_tv);
    }

    @Override
    protected int getLayout() {
        Log.i(TAG,"getLayout~~~~");
        return R.layout.activity_detail;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == borrowBtn.getId()){
            boolean logined = UserState.isLogined();
            if (logined == true){
//                BmobUser user= UserState.getUser();
//                book.setOwner(user);
                presenter.borrow(book);
            }else{
                Toast.makeText(DetailActivity.this,R.string.user_not_login,Toast.LENGTH_LONG).show();
                AppUtil.startActivity(DetailActivity.this,LoginActivity.class);
            }
        }
    }

    @Override
    public void borrowSuccess() {
        Toast.makeText(this,R.string.out_success,Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void borrowFailure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
