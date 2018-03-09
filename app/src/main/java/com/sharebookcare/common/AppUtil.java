package com.sharebookcare.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.sharebookcare.bean.Book;
import com.sharebookcare.bean.BookTask;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AppUtil {
    private static final String TAG = AppUtil.class.getSimpleName();
    public static void  startActivity(Context packageContext, Class<?> cls){
        Intent intent = new Intent(packageContext,cls);
        try{
            packageContext.startActivity(intent);
        }catch (Exception e){
            Log.i(TAG,e.toString());
        }
    }

    //test
    public static void printBookList(List<Book> bookList){
        for (Book book : bookList){
            if (book != null){
                Log.i(TAG,book.getImg());
            }
        }
    }

    //test
    public static void printBookTaskList(List<BookTask> bookList){
        for (BookTask book : bookList){
            if (book != null){
                Log.i(TAG,book.toString());
            }
        }
    }

    public static void startActivity(Context packageContext,Class<?> cls,Bundle bundle){
        Intent intent = new Intent(packageContext,cls);
        intent.putExtras(bundle);
        try{
            packageContext.startActivity(intent);
        }catch (Exception e){
            Log.i(TAG,e.toString());
        }
    }

    public static void loadImg(Context context,String url,ImageView iv){

        Picasso.with(context).load(url).into(iv);
    }

}
