package com.example.ysq.rxlab.models;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：ysq
 * 时间：2016/6/3
 */

public class NewsBean extends CatachableBean {

    String title;
    String url;
    @SerializedName("abstract")
    String content;
    String image_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
