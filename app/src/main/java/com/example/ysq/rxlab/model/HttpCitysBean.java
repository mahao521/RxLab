package com.example.ysq.rxlab.model;

import java.util.List;

/**
 * 作者：ysq
 * 时间：2016/6/6
 */

public class HttpCitysBean extends CatachableBean {
    List<CityBean> retData;

    public List<CityBean> getRetData() {
        return retData;
    }

    public void setRetData(List<CityBean> retData) {
        this.retData = retData;
    }
}
