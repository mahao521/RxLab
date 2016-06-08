package com.example.ysq.rxlab.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.activity.SampleActivity0;
import com.example.ysq.rxlab.activity.SampleActivity1;
import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class MainAdapter extends RecyclerView.Adapter {


    Context mContext;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_main, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                ((MyViewHolder) holder).textView.setText("基本Retrofit+Rxjava网络框架");
                RxView.clicks(((MyViewHolder) holder).textView)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)//防抖动
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                mContext.startActivity(new Intent(mContext, SampleActivity0.class));
                            }
                        });
                break;

            case 1:
                ((MyViewHolder) holder).textView.setText("Retrofit+Rxjava+sqlite");
                RxView.clicks(((MyViewHolder) holder).textView)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                mContext.startActivity(new Intent(mContext, SampleActivity1.class));
                            }
                        });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }


}
