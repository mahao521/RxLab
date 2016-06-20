package com.example.ysq.rxlab.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ysq.rxlab.models.WeatherBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * 作者：ysq
 * 时间：2016/6/20
 */

public class DWeather {
    private DbHelper mHelper;

    public DWeather(Context context) {
        mHelper = new DbHelper(context);
    }

    public Observable<List<WeatherBean>> getWeathers() {
        return Observable.create(new Observable.OnSubscribe<List<WeatherBean>>() {
            @Override
            public void call(Subscriber<? super List<WeatherBean>> subscriber) {
                List<WeatherBean> weatherBeen = new ArrayList<>();
                DbHelper.DB_LOCK.readLock().lock();
                SQLiteDatabase readableDatabase = mHelper.getReadableDatabase();
                Cursor cursor = readableDatabase.query("Weather", new String[]{"city", "citycode", "weather"}, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    WeatherBean bean = new WeatherBean();
                    bean.setCity(cursor.getString(0));
                    bean.setCitycode(cursor.getString(1));
                    bean.setWeather(cursor.getString(2));
                    weatherBeen.add(bean);
                }
                cursor.close();
                readableDatabase.close();
                DbHelper.DB_LOCK.readLock().unlock();
                subscriber.onNext(weatherBeen);
                subscriber.onCompleted();
            }
        });
    }

    public void addWeather(final WeatherBean weatherBean) {
        DbHelper.DB_LOCK.writeLock().lock();
        SQLiteDatabase writableDatabase = mHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", weatherBean.getCity());
        contentValues.put("citycode", weatherBean.getCitycode());
        contentValues.put("weather", weatherBean.getWeather());
        writableDatabase.insert("Weather", null, contentValues);
        writableDatabase.endTransaction();
        writableDatabase.close();
        DbHelper.DB_LOCK.writeLock().unlock();

    }
}
