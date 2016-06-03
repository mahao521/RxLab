package com.example.ysq.rxlab.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.adapter.Sample1Adapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class SampleFragment1 extends YSFragment {

    @Bind(R.id.rv)
    RecyclerView mRv;

    Sample1Adapter mAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample1, container, false);
        ButterKnife.bind(this, view);

        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new Sample1Adapter());
        mRv.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public int invaliMenu() {
        return R.menu.sample1;
    }

    @Override
    public String invaliTitle() {
        return super.invaliTitle();
    }

    @Override
    public void invaliOptionSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
