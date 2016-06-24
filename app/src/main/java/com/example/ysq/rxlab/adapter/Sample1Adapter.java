package com.example.ysq.rxlab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
        if (!TextUtils.isEmpty(mWeatherBeen.get(position).getCity()))
            ((MyViewHolder) holder).name.setText(mWeatherBeen.get(position).getCity());
        else
            ((MyViewHolder) holder).name.setText(mWeatherBeen.get(position).getCitycode());
        if (!TextUtils.isEmpty(mWeatherBeen.get(position).getWeather()))
            ((MyViewHolder) holder).weather.setText(mWeatherBeen.get(position).getWeather());
        else
            ((MyViewHolder) holder).weather.setText("--");
    }


    public void refresh(List<WeatherBean> weatherBeen) {
        mWeatherBeen = weatherBeen;
        notifyDataSetChanged();
    }


    public void add(WeatherBean weatherBeen) {
        mWeatherBeen.add(0, weatherBeen);
        notifyItemInserted(0);
    }

    public void update(WeatherBean weatherBean) {
        for (WeatherBean bean : mWeatherBeen) {
            if (bean.getCitycode().equals(weatherBean.getCitycode())){
                bean = weatherBean;
                notifyItemChanged(mWeatherBeen.indexOf(bean));
                break;
            }
        }
    }

    public List<WeatherBean> getWeathers() {
        return mWeatherBeen;
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
