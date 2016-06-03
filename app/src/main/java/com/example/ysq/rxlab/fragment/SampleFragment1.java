package com.example.ysq.rxlab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.adapter.Sample1Adapter;
import com.example.ysq.rxlab.model.StockBean;
import com.example.ysq.rxlab.net.Rt;
import com.example.ysq.rxlab.sqlite.DStockList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class SampleFragment1 extends YSFragment {

    @Bind(R.id.rv)
    RecyclerView mRv;

    Sample1Adapter mAdapter;

    List<StockBean> mStockBeen;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample1, container, false);
        ButterKnife.bind(this, view);

        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new Sample1Adapter());
        mRv.setItemAnimator(new DefaultItemAnimator());

        Observable.concat(Observable.create(new Observable.OnSubscribe<List<StockBean>>() {
                    @Override
                    public void call(Subscriber<? super List<StockBean>> subscriber) {
                        if (mStockBeen != null)
                            subscriber.onNext(mStockBeen);
                        else
                            subscriber.onCompleted();
                    }
                }), new DStockList(getContext()).getStockList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Action1<List<StockBean>>() {
                            @Override
                            public void call(List<StockBean> stockBeen) {
                                mAdapter.refresh(stockBeen);
                            }
                        })
        )
                .observeOn(Schedulers.io())
                .map(new Func1<List<StockBean>, List<StockBean>>() {
                    @Override
                    public List<StockBean> call(List<StockBean> stockBeen) {
                        return new ArrayList<>(stockBeen);
                    }
                })
                .filter(new Func1<List<StockBean>, Boolean>() {
                    @Override
                    public Boolean call(List<StockBean> stockBeen) {
                        if (stockBeen == null || stockBeen.size() == 0)
                            return false;
                        else
                            return true;
                    }
                })
                .map(new Func1<List<StockBean>, String>() {
                    @Override
                    public String call(List<StockBean> stockBeen) {
                        StringBuilder builder = new StringBuilder();
                        for (StockBean stockBean : stockBeen) {
                            builder.append(stockBean.getCode());
                            builder.append(",");
                        }
                        builder.substring(0, builder.length() - 2);
                        return builder.toString();
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String string) {
                        return Rt.sina().getStockInfo(string);
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String string) {

                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String string) {
                        Log.i(SampleFragment1.class.getSimpleName(), string);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(SampleFragment1.class.getSimpleName(), "e:" + throwable.getMessage());
                    }
                });

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
                new MaterialDialog.Builder(getContext())
                        .title("添加股票代码")
                        .input("沪股以sh开头，深股以sz开头", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                Observable.just(input.toString())
                                        .map(new Func1<String, StockBean>() {
                                            @Override
                                            public StockBean call(String s) {
                                                StockBean stockBean = new StockBean();
                                                stockBean.setCode(s);
                                                return stockBean;
                                            }
                                        })
                                        .doOnNext(new Action1<StockBean>() {
                                            @Override
                                            public void call(StockBean stockBean) {
                                                mStockBeen.add(0, stockBean);
                                                mAdapter.notifyItemInserted(0);
                                            }
                                        })
                                        .subscribeOn(Schedulers.io())
                                        .subscribe(new Action1<StockBean>() {
                                            @Override
                                            public void call(StockBean stockBean) {
                                                new DStockList(getContext()).setStockList(mStockBeen);
                                            }
                                        });

                            }
                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
