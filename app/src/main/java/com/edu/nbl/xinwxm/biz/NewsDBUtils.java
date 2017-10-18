package com.edu.nbl.xinwxm.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.edu.nbl.xinwxm.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydy on 2017/9/4.
 */

public class NewsDBUtils {

    /**
     *
     * @param context
     * @param news
     * @return
     */
    public  static boolean insert(Context context,News news) {
        //先查询有没有插入
        NewsDbHelper mHelper = new NewsDbHelper(context);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from news where nid=" + news.nid, null);
        if (mCursor.moveToNext()) {//说明有数据吧，已经查过了
            return false;
        }
        //插入操作
        ContentValues mContentValues = new ContentValues();
        mContentValues.put("nid", news.nid);
        mContentValues.put("stamp", news.stamp);
        mContentValues.put("icon", news.icon);
        mContentValues.put("title", news.title);
        mContentValues.put("summary", news.summary);
        mContentValues.put("link", news.link);
        //返回值代表成功插入,
        long mNews = db.insert("news", null, mContentValues);
        if (mNews != -1) {
            return true;
        } else {
            return false;
        }
    }
    public static List<News> query(Context context){
        List<News> mNewsList=new ArrayList<>();
        NewsDbHelper mHelper = new NewsDbHelper(context);
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from news where length(title)<11 Order By stamp asc " , null);
        //遍历
        while (mCursor.moveToNext()){
            int mNid = mCursor.getInt(mCursor.getColumnIndex("nid"));
            String mStamp = mCursor.getString(mCursor.getColumnIndex("stamp"));
            String mIcon = mCursor.getString(mCursor.getColumnIndex("icon"));
            String mTitle = mCursor.getString(mCursor.getColumnIndex("title"));
            String mSummary = mCursor.getString(mCursor.getColumnIndex("summary"));
            String mLink = mCursor.getString(mCursor.getColumnIndex("link"));
            News mNews = new News(mSummary, mIcon, mStamp, mTitle, mLink, mNid);
            mNewsList.add(mNews);
        }
        return  mNewsList;
    }
    public static boolean deleteAll(Context context){
        NewsDbHelper mHelper = new NewsDbHelper(context);
        SQLiteDatabase db=mHelper.getReadableDatabase();
        int mDelete = db.delete("news", null, null);
        if (mDelete!=-1){
            return true;
        }else {
            return false;
        }
    }
    public static boolean deleteOne(Context context,News news){
        NewsDbHelper mHelper = new NewsDbHelper(context);
        SQLiteDatabase db=mHelper.getReadableDatabase();
        int mDelete = db.delete("news", "nid=?", new String[]{news.nid + ""});//返回值代表成功删除数据
        if (mDelete!=-1){
            return true;
        }else {
            return false;
        }
    }
}
