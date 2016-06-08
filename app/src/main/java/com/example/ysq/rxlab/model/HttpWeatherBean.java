package com.example.ysq.rxlab.model;

/**
 * 作者：ysq
 * 时间：2016/6/8
 */

public class HttpWeatherBean extends CatachableBean {
    WeatherBean retData;

    public WeatherBean getRetData() {
        return retData;
    }

    public void setRetData(WeatherBean retData) {
        this.retData = retData;
    }
}
