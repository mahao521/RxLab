package com.example.ysq.rxlab.net;

import com.example.ysq.rxlab.model.HttpNewsBean;
import com.example.ysq.rxlab.net.RxJavaCallAdapterFactory.RxJavaCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Retrofit+Rxjava网络框架
 * 作者：杨水强
 * 时间：2016/5/27
 */
public class Rt {
    private static final String BAIDU_KEY = "87338389e13643b783b9443cfa349f99";

    private static final String SERVER_URL = "http://apis.baidu.com";
    private static final String SERVER_URL1 = "http://hq.sinajs.cn";

    private static Rt mRt;

    BaiduService mBaiduService;
    SinaStockService mSinaStockServer;


    private Rt() {
        mBaiduService = new Retrofit.Builder().baseUrl(SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BaiduService.class);

        mSinaStockServer = new Retrofit.Builder().baseUrl(SERVER_URL1)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build().create(SinaStockService.class);

    }


    /**
     * @return 获取API接口
     */
    public static BaiduService baidu() {
        if (mRt == null) {
            synchronized (Rt.class) {
                if (mRt == null) {
                    mRt = new Rt();
                }
            }
        }
        return mRt.mBaiduService;
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

    public interface BaiduService {

        /**
         * @return 最新新闻
         */
        @Headers("apikey:" + BAIDU_KEY)
        @GET("/songshuxiansheng/news/news")
        Observable<HttpNewsBean> getNews();
    }

}
