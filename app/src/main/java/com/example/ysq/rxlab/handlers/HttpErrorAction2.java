package com.example.ysq.rxlab.handlers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.net.UnknownHostException;

import rx.functions.Action1;

/**
 * 作者：ysq
 * 时间：2016/6/16
 */

public abstract class HttpErrorAction2 implements Action1<Throwable> {
    Context mContext;

    public HttpErrorAction2(Context context) {
        this.mContext = context;
    }

    @Override
    public void call(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            Toast.makeText(mContext, "联网失败，请检查网络", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(HttpErrorAction1.class.getSimpleName(), throwable.getMessage());
        }
        afterCall();
    }

    public abstract void afterCall();
}

