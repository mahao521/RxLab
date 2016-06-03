package com.example.ysq.rxlab.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.adapter.Sample0Adapter;
import com.example.ysq.rxlab.model.HttpNewsBean;
import com.example.ysq.rxlab.net.ErrorAction1;
import com.example.ysq.rxlab.net.Rt;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class SampleFragment0 extends YSFragment {

    @Bind(R.id.rv)
    RecyclerView mRv;

    @Bind(R.id.pb)
    ProgressBar mPb;

    Sample0Adapter mAdapter;

    Subscription subscribe;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample0, container, false);
        ButterKnife.bind(this, view);


        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new Sample0Adapter());
        mRv.setItemAnimator(new DefaultItemAnimator());


        subscribe = Rt.baidu().getNews()//获取新闻
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<HttpNewsBean>() {
                    @Override
                    public void call(HttpNewsBean httpNewsBean) {
                        mPb.setVisibility(View.GONE);
                    }
                })
                .subscribe(new Action1<HttpNewsBean>() {
                    @Override
                    public void call(HttpNewsBean httpNewsBean) {
                        if (httpNewsBean.getErrNum() == 0) {
                            mAdapter.refresh(httpNewsBean.getRetData());
                        } else {
                            Log.e(SampleFragment0.class.getSimpleName(), httpNewsBean.getErrMsg());
                        }
                    }
                }, new ErrorAction1(getContext()));

        return view;
    }


    @Override
    public String invaliTitle() {
        return getString(R.string.sample_fragment0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        subscribe.unsubscribe();//取消订阅，防止内存泄漏
    }
}
