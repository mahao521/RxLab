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
import com.example.ysq.rxlab.adapter.MainAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment extends YSFragment {
    @Bind(R.id.rv)
    RecyclerView mRv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(new MainAdapter());
        mRv.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
