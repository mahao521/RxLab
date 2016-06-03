package com.example.ysq.rxlab.model;

import java.util.List;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class HttpNewsBean extends CatachableBean {
    List<NewsBean> retData;

    public List<NewsBean> getRetData() {
        return retData;
    }

    public void setRetData(List<NewsBean> retData) {
        this.retData = retData;
    }
}
