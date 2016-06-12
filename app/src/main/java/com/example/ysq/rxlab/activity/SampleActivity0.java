package com.example.ysq.rxlab.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
 * 时间：2016/6/8
 */

public class SampleActivity0 extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.rv)
    RecyclerView mRv;

    @Bind(R.id.pb)
    ProgressBar mPb;

    @Bind(R.id.swipe)
    SwipeRefreshLayout mSwipe;

    Sample0Adapter mAdapter;

    Subscription subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample0);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setItemAnimator(new DefaultItemAnimator());

        subscribe = getNews();

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (subscribe != null) {
                    subscribe.unsubscribe();
                }
                subscribe = getNews();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        subscribe.unsubscribe();
    }


    public Subscription getNews() {
        return Rt.baidu().getNews()//获取新闻
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
                        mSwipe.setRefreshing(false);
                        if (httpNewsBean.getErrNum() == 0) {
                            mRv.setAdapter(mAdapter = new Sample0Adapter(httpNewsBean.getRetData()));
                        } else {
                            Log.e(SampleActivity0.class.getSimpleName(), httpNewsBean.getErrMsg());
                        }
                    }
                }, new ErrorAction1(this));
    }

}
