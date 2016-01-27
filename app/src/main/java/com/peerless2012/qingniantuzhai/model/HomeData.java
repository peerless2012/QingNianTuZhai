package com.peerless2012.qingniantuzhai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/1/30 0:38
 * @Version V1.0
 * @Description
 */
public class HomeData {
    @SerializedName("pageNum")
    @Expose
    private int pageNum = 1;

    @SerializedName("articleItems")
    @Expose
    private List<ArticleItem> articleItems;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<ArticleItem> getArticleItems() {
        return articleItems;
    }

    public void setArticleItems(List<ArticleItem> articleItems) {
        this.articleItems = articleItems;
    }
}
