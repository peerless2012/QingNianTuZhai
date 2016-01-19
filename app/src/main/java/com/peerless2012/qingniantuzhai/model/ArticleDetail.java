package com.peerless2012.qingniantuzhai.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/1/20.
 */
public class ArticleDetail implements Parcelable{

    public static final int TYPE_IMG = 1;
    public static final int TYPE_GIF = 2;
    public static final int TYPE_VIDEO = 3;

    public ArticleDetail() {
    }

    protected ArticleDetail(Parcel in) {
        img = in.readString();
        desc = in.readString();
        type = in.readInt();
    }

    @IntDef({TYPE_IMG,TYPE_GIF,TYPE_VIDEO})
   public @interface IArticleDetail{};

    @Expose
    @SerializedName("img")
    private String img;

    @Expose
    @SerializedName("desc")
    private String desc;

    @Expose
    @SerializedName("type")
    private int type;

    public String getImg() {
//        return img;
        return "http://www.qingniantuzhai.com/images/logo.gif";
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Nullable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public @IArticleDetail int getType() {
        if (getImg() != null){
            if (getImg().endsWith("gif")){
                return TYPE_GIF;
            }else if (getImg().endsWith("jpg") || getImg().endsWith("png")){
                return TYPE_IMG;
            }
            //TODO .......更加详细的类型
            return TYPE_VIDEO;

        }else {
            throw new IllegalArgumentException("Url can't be null!");
        }
    }

    public void setType(@IArticleDetail int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ArticleDetail{" +
                "img='" + img + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                '}';
    }

    public static final Creator<ArticleDetail> CREATOR = new Creator<ArticleDetail>() {
        @Override
        public ArticleDetail createFromParcel(Parcel in) {
            return new ArticleDetail(in);
        }

        @Override
        public ArticleDetail[] newArray(int size) {
            return new ArticleDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(desc);
        dest.writeInt(type);
    }
}
