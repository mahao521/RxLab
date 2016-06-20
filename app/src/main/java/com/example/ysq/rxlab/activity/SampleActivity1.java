package com.example.ysq.rxlab.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ysq.rxlab.R;
import com.example.ysq.rxlab.adapter.Sample1Adapter;
import com.example.ysq.rxlab.handlers.HttpErrorAction1;
import com.example.ysq.rxlab.models.CityBean;
import com.example.ysq.rxlab.models.HttpCitysBean;
import com.example.ysq.rxlab.models.WeatherBean;
import com.example.ysq.rxlab.network.Rt;
import com.example.ysq.rxlab.sqlite.DWeather;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：ysq
 * 时间：2016/6/8
 */

public class SampleActivity1 extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.rv)
    RecyclerView mRv;

    Sample1Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample1);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter = new Sample1Adapter());
        mRv.setItemAnimator(new DefaultItemAnimator());

        DWeather dWeather = new DWeather(this);
        dWeather.getWeathers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<WeatherBean>>() {
                    @Override
                    public void call(List<WeatherBean> weatherBeen) {
                        mAdapter.refresh(weatherBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(SampleActivity1.class.getSimpleName(), throwable.getMessage());
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                new MaterialDialog.Builder(this)
                        .title("城市名")
                        .input("请输入城市或者省份", null, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                showAddCity(input.toString());
                            }
                        })
                        .positiveText(getResources().getString(R.string.dialog_ok))
                        .negativeText(getResources().getString(R.string.dialog_cancel))
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAddCity(@NonNull String cityName) {
        Rt.baidu().getCitys(cityName).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<HttpCitysBean>() {
                    @Override
                    public void call(HttpCitysBean httpCitysBean) {
                        List<String> been = new ArrayList<>();
                        for (CityBean cityBean : httpCitysBean.getRetData()) {
                            been.add(cityBean.getName_cn());
                        }
                        httpCitysBean.setCollection(been);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HttpCitysBean>() {
                    @Override
                    public void call(final HttpCitysBean httpCitysBean) {
                        if (httpCitysBean.getErrNum() == 0) {
                            new MaterialDialog.Builder(SampleActivity1.this)
                                    .title("请选择一个城市")
                                    .items(httpCitysBean.getCollection())
                                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                                        @Override
                                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                            addCity(httpCitysBean.getRetData().get(which));
                                            return false;
                                        }
                                    })
                                    .positiveText(getResources().getString(R.string.dialog_ok))
                                    .negativeText(getResources().getString(R.string.dialog_cancel))
                                    .show();
                        } else {
                            Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "查询城市不存在", Snackbar.LENGTH_LONG).show();
                            Log.e(SampleActivity1.class.getSimpleName(), httpCitysBean.getErrMsg() + ",code:" + httpCitysBean.getErrNum());
                        }
                    }
                }, new HttpErrorAction1(SampleActivity1.this));
    }

    private void addCity(CityBean cityBean) {
        Observable.just(cityBean)
                .observeOn(Schedulers.io())
                .map(new Func1<CityBean, WeatherBean>() {
                    @Override
                    public WeatherBean call(CityBean cityBean) {
                        WeatherBean bean = new WeatherBean();
                        bean.setCitycode(cityBean.getArea_id() + "");
                        return bean;
                    }
                })
                .doOnNext(new Action1<WeatherBean>() {
                    @Override
                    public void call(WeatherBean weatherBean) {
                        new DWeather(SampleActivity1.this).addWeather(weatherBean);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherBean>() {
                    @Override
                    public void call(WeatherBean weatherBean) {
                        mAdapter.add(weatherBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(SampleActivity1.class.getSimpleName(), throwable.getMessage());
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
