package com.example.ysq.rxlab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.model.NewsBean;

import java.util.List;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class Sample0Adapter extends RecyclerView.Adapter {

    Context mContext;

    List<NewsBean> mNewsBeen;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder) holder).textView.setText(mNewsBeen.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        if (mNewsBeen == null) return 0;
        else return mNewsBeen.size();
    }

    public void refresh(List<NewsBean> been) {
        mNewsBeen = been;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
