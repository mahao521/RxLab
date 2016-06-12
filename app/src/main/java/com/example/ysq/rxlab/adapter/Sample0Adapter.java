package com.example.ysq.rxlab.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.activity.WebActivity;
import com.example.ysq.rxlab.model.NewsBean;
import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class Sample0Adapter extends RecyclerView.Adapter {

    Context mContext;

    List<NewsBean> mNewsBeen;

    public Sample0Adapter(List<NewsBean> newsBeen) {
        this.mNewsBeen = newsBeen;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_sample0, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).textView.setText(mNewsBeen.get(position).getTitle());
        if (TextUtils.isEmpty(mNewsBeen.get(position ).getImage_url()))
            mNewsBeen.get(position).setImage_url(null);
        Picasso.with(mContext).load(mNewsBeen.get(position).getImage_url()).placeholder(R.drawable.img_news_default).into(((MyViewHolder) holder).imageView);
        RxView.clicks(((MyViewHolder) holder).view).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Intent intent = new Intent(mContext, WebActivity.class);
                        intent.putExtra(WebActivity.URL, mNewsBeen.get(holder.getAdapterPosition()).getUrl());
                        mContext.startActivity(intent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(Sample0Adapter.class.getSimpleName(), throwable.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (mNewsBeen == null) return 0;
        else return mNewsBeen.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            textView = (TextView) itemView.findViewById(R.id.tv);
            view = itemView;
        }
    }
}
