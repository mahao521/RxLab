package com.example.ysq.rxlab.net;

import com.example.ysq.rxlab.net.RxJavaCallAdapterFactory.RxJavaCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit+Rxjava网络框架
 * 作者：杨水强
 * 时间：2016/5/27
 */
public class Rt {
    private static Rt mRt;


    private static String SERVER_URL = "http://hq.sinajs.cn";
    SinaStockService mSinaStockServer;


    private Rt() {
        mSinaStockServer = new Retrofit.Builder().baseUrl(SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(SinaStockService.class);

    }

    /**
     * @return 获取新浪股票 HTTP接口
     */
    public static SinaStockService sina() {
        if (mRt == null) {
            synchronized (Rt.class) {
                if (mRt == null) {
                    mRt = new Rt();
                }
            }
        }
        return mRt.mSinaStockServer;
    }


    public interface SinaStockService {
        /**
         * @return 获取最新登录信息
         */
        @GET("/list={list}")
        Observable<String> getStockInfo(@Path("list") String list);
    }

}
