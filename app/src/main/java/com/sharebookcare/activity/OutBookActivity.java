package com.sharebookcare.activity;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharebookcare.R;
import com.sharebookcare.bean.Book;
import com.sharebookcare.common.MyToast;
import com.sharebookcare.common.UserState;
import com.sharebookcare.contract.OutBookContract;
import com.sharebookcare.presenter.OutBookPresenter;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;


public class OutBookActivity extends BaseActivity implements View.OnClickListener,OutBookContract.View {

    private static final String TAG = OutBookActivity.class.getSimpleName();

    private EditText nameEt;
    private EditText publishEt;
//    private ImageButton uploadIv;
    private ImageView imgIv;
    private TextView imgTv;
    private EditText authorEt;
    private AVLoadingIndicatorView loadingView;

    AlertDialog dialog ;
    private Button uploadBtn;

    private OutBookContract.Presenter presenter;

    private static final int CROP_PHOTO = 1;
    private static final int REQUEST_CODE_PICK_IMAGE = 2;

    private EditText summaryEt;
    @Override
    protected void initListener() {
        uploadBtn.setOnClickListener(this);
        imgTv.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        setTitle(R.string.out_book_card_btn);
        presenter = new OutBookPresenter(this);
        dialog = createDialog();
    }

    @Override
    protected void initView() {
        summaryEt = findViewById(R.id.editSms);
        nameEt = findViewById(R.id.name_et);
        publishEt = findViewById(R.id.publish_et);
//        uploadIv = findViewById(R.id.upload_iv);
        imgIv = findViewById(R.id.img_iv);
        imgTv = findViewById(R.id.img_tv);
        authorEt = findViewById(R.id.author_et);
        uploadBtn = findViewById(R.id.upload_btn);
        loadingView = findViewById(R.id.loading);
        loadingView.hide();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_outbook;
    }

    @Override
    public void onClick(android.view.View view) {
        if (view.getId() == uploadBtn.getId()){
            String checkoutResult = checkoutBook();
            if (TextUtils.isEmpty(checkoutResult)){
                Book book = createBook();
                presenter.upload(book,choosePath);
                loadingView.show();
            }else{
                MyToast.showToast(OutBookActivity.this,checkoutResult);
            }
        }else if (view.getId() == imgTv.getId()){
            Log.i(TAG,"take or choose photo");
            if (dialog != null && !dialog.isShowing()){
                dialog.show();
            }
        }
    }

    private AlertDialog createDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_camera,null);
        Button takePhotoBtn = view.findViewById(R.id.takePhoto_btn);
        Button choosePhotoBtn = view.findViewById(R.id.choosePhoto_btn);

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });

        choosePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });


        builder.setView(view);
        return builder.create();
    }

    private void choosePhoto() {
        /**
         * 最后一个参数是文件夹的名称，可以随便起
         */
        File file=new File(Environment.getExternalStorageDirectory(),"bookphoto");
        if(!file.exists()){
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output=new File(file,System.currentTimeMillis()+".jpg");
        Log.i(TAG,"output:" + output.getAbsolutePath());

        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         * 将文件转化为uri
         */
        imageUri = Uri.fromFile(output);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra("return-data",true);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

        if (dialog != null){
            dialog.dismiss();
        }
    }

    File output ; //
    Uri imageUri;
    String choosePath = null;
    private void takePhoto() {
        /**
         * 最后一个参数是文件夹的名称，可以随便起
         */
        File file=new File(Environment.getExternalStorageDirectory(),"bookphoto");
        if(!file.exists()){
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output=new File(file,System.currentTimeMillis()+".jpg");
        Log.i(TAG,"output:" + output.getAbsolutePath());
        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         * 将文件转化为uri
         */
        imageUri = Uri.fromFile(output);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);

        if (dialog != null){
            dialog.dismiss();
        }
    }

    private Book createBook() {
        String name = nameEt.getText().toString();
        String publish = publishEt.getText().toString();
        String author = authorEt.getText().toString();
        String sumarry = summaryEt.getText().toString();

        Book book = new Book();
        book.setOwner(UserState.getUser());
        book.setTitle(name);
        book.setPublisher(publish);
        book.setAuthor(author);
        book.setOut(0);
        book.setSummary(sumarry);
        return book;
    }

    private String checkoutBook() {
        StringBuilder builder = new StringBuilder();
        String name = nameEt.getText().toString();
        String publish = publishEt.getText().toString();
        String author = authorEt.getText().toString();
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
    public void uploadSuccess() {
        Toast.makeText(this,R.string.upload_success,Toast.LENGTH_LONG).show();
        loadingView.hide();
        finish();
    }

    @Override
    public void uploadfailure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
        loadingView.hide();
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (res==RESULT_OK) {
                    try {
                        /**
                         * 该uri就是照片文件夹对应的uri
                         */
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imgIv.setImageBitmap(bit);
                        choosePath = output.getAbsolutePath();
                    } catch (Exception e) {
                        Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.i("tag", "失败");
                }

                break;
            /**
             * 从相册中选取图片的请求标志
             */

            case REQUEST_CODE_PICK_IMAGE:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        imgIv.setImageBitmap(bit);
                        choosePath = getFilePath(data);
                        Log.i(TAG,"choosePath:" + choosePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("tag",e.getMessage());
                        Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Log.i("liang", "失败");
                }

                break;

            default:
                break;
        }
    }

    private String getFilePath(Intent data) {
        Uri uri = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        try {
            Cursor cursor = managedQuery(uri, filePathColumn, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            return path;
        }catch (Exception e){
            Log.e(TAG,e.toString());
            return null;
        }
    }
}
