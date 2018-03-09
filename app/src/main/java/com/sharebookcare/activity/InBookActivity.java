package com.sharebookcare.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.bean.BookTask;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.InBookContract;
import com.sharebookcare.presenter.InBookPresenter;


public class InBookActivity extends BaseActivity implements View.OnClickListener,InBookContract.View {
    private EditText nameEt;
    private EditText publishEt;
    private EditText authorEt;
    private Button checkoutBtn;
    private InBookContract.Presenter presenter;

    @Override
    protected void initListener() {
        checkoutBtn.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        setTitle(R.string.in_book_card_btn);
        presenter = new InBookPresenter(this);
    }

    @Override
    protected void initView() {
        nameEt = findViewById(R.id.name_et);
        publishEt = findViewById(R.id.publish_et);
        authorEt = findViewById(R.id.author_et);
        checkoutBtn = findViewById(R.id.checkout_btn);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_inbook;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == checkoutBtn.getId()){
            String name = nameEt.getText().toString();
            String publish = publishEt.getText().toString();
            String author = authorEt.getText().toString();
            String result = checkoutBook(name,publish,author);
            if (TextUtils.isEmpty(result)){
                BookTask bookTask = new BookTask();
                bookTask.setName(name);
                bookTask.setPublish(publish);
                bookTask.setAuthor(author);
                bookTask.setSender(UserState.getUser());
                bookTask.setReceived(0);
                presenter.checkout(bookTask);
            }else{
                Toast.makeText(InBookActivity.this,result,Toast.LENGTH_LONG).show();
            }
        }
    }

    private String checkoutBook(String name,String publish,String author) {
        StringBuilder builder = new StringBuilder();

        if (TextUtils.isEmpty(name)){
            builder.append("请输入书名");
        }
        if (TextUtils.isEmpty(publish)){
            if (builder.length() > 0){
                builder.append("\n");
            }
            builder.append("请输入出版社");
        }
        if (TextUtils.isEmpty(author)){
            if (builder.length() > 0){
                builder.append("\n");
            }
            builder.append("请输入作者");
        }

        return builder.toString();
    }

    @Override
    public void checkoutSuccess() {
        Toast.makeText(this,R.string.checkout_success,Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void checkoutFailure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
