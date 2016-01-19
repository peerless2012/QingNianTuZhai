package com.peerless2012.qingniantuzhai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/1/18.
 */
public class ArticleItem {

    @Expose
    @SerializedName("dateTime")
    private long dateTime;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("previewImgUrl")
    private String previewImgUrl;

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewImgUrl() {
        return previewImgUrl;
    }

    public void setPreviewImgUrl(String previewImgUrl) {
        this.previewImgUrl = previewImgUrl;
    }

    @Override
    public String toString() {
        return "ArticleItem{" +
                "dateTime=" + dateTime +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", previewImgUrl='" + previewImgUrl + '\'' +
                '}';
    }
}
