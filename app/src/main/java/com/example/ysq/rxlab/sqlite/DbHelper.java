package com.example.ysq.rxlab.sqlite;


import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 如果对写入速度有要求，可以对每个表写一个Helper
 * <p>
 * <p>作者：ysq
 * <p>
 * <br>时间：2016/1/29
 */
public class DbHelper extends SQLiteOpenHelper {

    static {
        DB_LOCK = new ReentrantReadWriteLock();
    }

    private static final String DB_NAME = "rxlab.db3";

    public static final ReadWriteLock DB_LOCK;

    private static final int DB_VERSION = 1;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Weather(city TEXT,citycode TEXT,weather TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

