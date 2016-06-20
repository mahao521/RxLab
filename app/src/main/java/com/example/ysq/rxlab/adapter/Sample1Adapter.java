package com.example.ysq.rxlab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.models.WeatherBean;

import java.util.List;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class Sample1Adapter extends RecyclerView.Adapter {

    Context mContext;

    List<WeatherBean> mWeatherBeen;


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
        ((MyViewHolder) holder).name.setText(mWeatherBeen.get(position).getCity());
        ((MyViewHolder) holder).weather.setText(mWeatherBeen.get(position).getWeather());
    }


    public void refresh(List<WeatherBean> weatherBeen) {
        mWeatherBeen = weatherBeen;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWeatherBeen == null) return 0;
        else return mWeatherBeen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, weather;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            weather = (TextView) itemView.findViewById(R.id.weather);
        }
    }
}
