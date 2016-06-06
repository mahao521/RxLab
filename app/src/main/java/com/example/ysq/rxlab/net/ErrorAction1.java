package com.example.ysq.rxlab.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.ysq.rxlab.net.RxJavaCallAdapterFactory.HttpException;

import java.net.UnknownHostException;

import rx.functions.Action1;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class ErrorAction1 implements Action1<Throwable> {
    Context mContext;

    public ErrorAction1(Context context) {
        this.mContext = context;
    }

    @Override
    public void call(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            Toast.makeText(mContext, "联网失败，请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            ((HttpException) throwable).getMessage();
            Log.i(ErrorAction1.class.getSimpleName(), throwable.getMessage());
        }
    }
}
