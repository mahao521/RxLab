package com.example.ysq.rxlab.network;

import com.example.ysq.rxlab.models.HttpCitysBean;
import com.example.ysq.rxlab.models.HttpNewsBean;
import com.example.ysq.rxlab.models.HttpWeatherBean;
import com.example.ysq.rxlab.network.RxJavaCallAdapterFactory.RxJavaCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit+Rxjava网络框架
 * 作者：ysq
 * 时间：2016/5/27
 */
public class Rt {
    private static final String BAIDU_KEY = "87338389e13643b783b9443cfa349f99";

    private static final String SERVER_URL = "http://apis.baidu.com";

    private static Rt mRt;

    BaiduService mBaiduService;


    private Rt() {
        mBaiduService = new Retrofit.Builder().baseUrl(SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())   获取string 类型结果
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(BaiduService.class);
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


    public interface BaiduService {

        /**
         * @return 最新新闻 <a href="http://apistore.baidu.com/apiworks/servicedetail/1570.html">查看说明
         */
        @Headers("apikey:" + BAIDU_KEY)
        @GET("/songshuxiansheng/news/news")
        Observable<HttpNewsBean> getNews();

        /**
         * @param cityname 查询地址
         * @return 可用城市 <a href="http://apistore.baidu.com/apiworks/servicedetail/112.html">查看说明
         */
        @Headers("apikey:" + BAIDU_KEY)
        @GET("/apistore/weatherservice/citylist")
        Observable<HttpCitysBean> getCitys(@Query("cityname") String cityname);


        /**
         * @param cityid 查询地址ID
         * @return 查询天气 <a href="http://apistore.baidu.com/apiworks/servicedetail/112.html">查看说明
         */
        @Headers("apikey:" + BAIDU_KEY)
        @GET("/apistore/weatherservice/cityid")
        Observable<HttpWeatherBean> getWeather(@Query("cityid") String cityid);

    }

}
