package com.edu.nbl.xinwxm.biz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ydy on 2017/10/16.
 */

public class NewsDbHelper extends SQLiteOpenHelper{
    public NewsDbHelper(Context context) {
        super(context,"savanews.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table news (id integer primary key autoincrement,nid integer,stamp text,icon text,title text,summary text,link text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
