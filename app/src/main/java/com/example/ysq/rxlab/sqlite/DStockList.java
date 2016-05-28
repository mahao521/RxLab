package com.example.ysq.rxlab.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ysq.rxlab.model.StockBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;


public class DStockList {
    private final DbHelper dbHelper;

    public DStockList(Context context) {
        dbHelper = new DbHelper(context);
    }

    public Observable<List<StockBean>> getStockList() {
        return Observable.create(new Observable.OnSubscribe<List<StockBean>>() {
            @Override
            public void call(Subscriber<? super List<StockBean>> subscriber) {
                List<StockBean> stockBean = new ArrayList<>();
                synchronized (dbHelper) {
                    SQLiteDatabase dataBase = null;
                    try {
                        dataBase = dbHelper.getReadableDatabase();
                        Cursor cursor = dataBase.query("DStockList", new String[]{"code", "name", "price", "percent"}, null, null, null, null, null);
                        while (cursor.moveToNext()) {
                            StockBean stock = new StockBean();
                            stock.setCode(cursor.getString(0));
                            stock.setName(cursor.getString(1));
                            stock.setPrice(cursor.getString(2));
                            stock.setPercent(cursor.getString(3));
                            stockBean.add(stock);
                        }
                        cursor.close();
                    } catch (Exception e) {
                        Log.e(DStockList.class.getSimpleName(), e.getMessage());
                    } finally {
                        if (dataBase != null)
                            dataBase.close();
                    }
                }
                subscriber.onNext(stockBean);
            }
        });
    }

}
