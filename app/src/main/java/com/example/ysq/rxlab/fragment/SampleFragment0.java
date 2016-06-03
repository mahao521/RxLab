package com.example.ysq.rxlab.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.adapter.MainAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class SampleFragment0 extends YSFragment {

    @Bind(R.id.rv)
    RecyclerView mRv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample0, container, false);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(new MainAdapter());
        mRv.setItemAnimator(new DefaultItemAnimator());
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public String invaliTitle() {
        return super.invaliTitle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
