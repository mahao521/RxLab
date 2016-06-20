package com.example.ysq.rxlab.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.models.CityBean;

import java.util.List;

/**
 * 作者：ysq
 * 时间：2016/6/6
 */

public class CityAddAdapter extends BaseAdapter {
    List<CityBean> mCityBeen;
    Context mContext;

    public CityAddAdapter(Context context, List<CityBean> mCityBeen) {
        mContext = context;
        this.mCityBeen = mCityBeen;
    }

    @Override
    public int getCount() {
        return mCityBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityViewHolder holder;
        if (convertView == null) {
            holder = new CityViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_city_add, parent, false);
            holder.province = (TextView) convertView.findViewById(R.id.province);
            holder.district = (TextView) convertView.findViewById(R.id.district);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (CityViewHolder) convertView.getTag();
        }
        holder.province.setText(mCityBeen.get(position).getProvince_cn());
        holder.district.setText(mCityBeen.get(position).getDistrict_cn());
        holder.name.setText(mCityBeen.get(position).getName_cn());
        return convertView;
    }

    class CityViewHolder {
        TextView province, district, name;
    }
}
