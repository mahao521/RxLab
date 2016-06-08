package com.example.ysq.rxlab.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.ysq.rxlab.adapter.CityAddAdapter;
import com.example.ysq.rxlab.adapter.Sample1Adapter;
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
                            new MaterialDialog.Builder(SampleActivity1.this)
                                    .title("请选择一个城市")
                                    .adapter(new CityAddAdapter(SampleActivity1.this, httpCitysBean.getRetData()), new MaterialDialog.ListCallback() {
                                        @Override
                                        public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {

                                        }
                                    })
                                    .positiveText(getResources().getString(R.string.dialog_ok))
                                    .negativeText(getResources().getString(R.string.dialog_cancel))
                                    .show();
                        } else {
                            Log.e(SampleActivity1.class.getSimpleName(), httpCitysBean.getErrMsg());
                        }
                    }
                }, new ErrorAction1(SampleActivity1.this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
