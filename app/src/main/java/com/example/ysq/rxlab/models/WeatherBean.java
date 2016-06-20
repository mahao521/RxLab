package com.example.ysq.rxlab.models;

/**
 * 作者：ysq
 * 时间：2016/6/8
 */

public class WeatherBean {
    String city;
    String cityid;
    WeatherTodayBean today;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public WeatherTodayBean getToday() {
        return today;
    }

    public void setToday(WeatherTodayBean today) {
        this.today = today;
    }
}
