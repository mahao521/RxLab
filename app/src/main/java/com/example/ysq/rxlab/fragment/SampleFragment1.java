package com.example.ysq.rxlab.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.adapter.CityAddAdapter;
import com.example.ysq.rxlab.adapter.Sample1Adapter;
import com.example.ysq.rxlab.model.CityBean;
import com.example.ysq.rxlab.model.HttpCitysBean;
import com.example.ysq.rxlab.net.ErrorAction1;
import com.example.ysq.rxlab.net.Rt;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class SampleFragment1 extends Fragment {

    @Bind(R.id.rv)
    RecyclerView mRv;

    Sample1Adapter mAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample1, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter = new Sample1Adapter());
        mRv.setItemAnimator(new DefaultItemAnimator());


        Rt.baidu().getCitys("北京").subscribeOn(Schedulers.io())
                .subscribe(new Action1<HttpCitysBean>() {
                    @Override
                    public void call(HttpCitysBean httpCitysBean) {
                        if (httpCitysBean.getErrNum() == 0) {
                            for (CityBean city : httpCitysBean.getRetData())
                                Log.i(SampleFragment1.class.getSimpleName(), city.getName_cn());
                        } else {
                            Log.e(SampleFragment1.class.getSimpleName(), httpCitysBean.getErrMsg());
                        }
                    }
                }, new ErrorAction1(getContext()));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sample1, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            new MaterialDialog.Builder(getContext())
                    .title("城市名")
                    .input("请输入城市或者省份", null, false, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            addCity(input.toString());
                        }
                    })
                    .positiveText(getResources().getString(R.string.dialog_ok))
                    .negativeText(getResources().getString(R.string.dialog_cancel))
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addCity(@NonNull String cityName) {
        Rt.baidu().getCitys(cityName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpCitysBean>() {
                    @Override
                    public void call(HttpCitysBean httpCitysBean) {
                        if (httpCitysBean.getErrNum() == 0) {
                            new MaterialDialog.Builder(getContext())
                                    .title("请选择一个城市")
                                    .adapter(new CityAddAdapter(getContext(), httpCitysBean.getRetData()), new MaterialDialog.ListCallback() {
                                        @Override
                                        public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                                        }
                                    })
                                    .positiveText(getResources().getString(R.string.dialog_ok))
                                    .negativeText(getResources().getString(R.string.dialog_cancel))
                                    .show();
                        } else {
                            Log.e(SampleFragment1.class.getSimpleName(), httpCitysBean.getErrMsg());
                        }
                    }
                }, new ErrorAction1(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
