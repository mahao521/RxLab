package com.example.ysq.rxlab.model;

import java.util.Collection;
import java.util.List;

/**
 * 作者：ysq
 * 时间：2016/6/6
 */

public class HttpCitysBean extends CatachableBean {
    List<CityBean> retData;
    Collection<String> collection;

    public List<CityBean> getRetData() {
        return retData;
    }

    public void setRetData(List<CityBean> retData) {
        this.retData = retData;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public void setCollection(Collection<String> collection) {
        this.collection = collection;
    }
}
