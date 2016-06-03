package com.example.ysq.rxlab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.model.StockBean;

import java.util.List;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class Sample1Adapter extends RecyclerView.Adapter {

    Context mContext;

    List<StockBean> mStockBean;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_sample1, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(mStockBean.get(position).getName()))
            ((MyViewHolder) holder).name.setText(mStockBean.get(position).getName());
        else
            ((MyViewHolder) holder).name.setText(mStockBean.get(position).getCode());
        ((MyViewHolder) holder).price.setText(mStockBean.get(position).getPrice());
        ((MyViewHolder) holder).percent.setText(mStockBean.get(position).getPercent());

    }


    public void refresh(List<StockBean> stockBeen) {
        mStockBean = stockBeen;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if (mStockBean == null) return 0;
        else return mStockBean.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, percent;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            percent = (TextView) itemView.findViewById(R.id.percent);
        }
    }
}
