package com.example.ysq.rxlab.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ysq.rxlab.R;

import butterknife.ButterKnife;


public class SampleFragment0 extends YSFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample0, container, false);
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
