package com.example.ysq.rxlab.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.activity.IScrew;

/**
 * 作者：杨水强
 * 时间：2016/5/27
 */

public class YSFragment extends Fragment {

    IScrew mContext;

    private String mTitle = getClass().getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (IScrew) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        mContext.invalidateActionBar(mTitle, R.menu.fragment_main);
    }


    public void setTitle(String title) {
        this.mTitle = title;
    }
}
