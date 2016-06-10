package com.peerless2012.qingniantuzhai.colorui;

import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.colorui.util.AttrTag;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/10 20:57
 * @Version V1.0
 * @Description :
 */
public class ThemeInfo {

    @AttrTag(tag = "MainBg",value = R.attr.MainBg)
    private int backgroundRes;

    @AttrTag(tag = "MainBgInvert",value = R.attr.MainBgInvert)
    private int backgroundInvertRes;

    @AttrTag(tag = "SecondBg",value = R.attr.SecondBg)
    private int secondBackgroundRes;

    // 跟上面一致
    @AttrTag(tag = "SecondBgInvert",value = R.attr.SecondBg)
    private int secondBackgroundInvertRes;

    @AttrTag(tag = "MainTextColor",value = R.attr.MainTextColor)
    private int mainTextColorRes;

    @AttrTag(tag = "MainTextColorInvert",value = R.attr.MainTextColorInvert)
    private int mainTextColorInvertRes;

    @AttrTag(tag = "SecondTextColor",value = R.attr.SecondTextColor)
    private int secondTextColorRes;

    // 跟上面一致
    @AttrTag(tag = "SecondTextColorInvert",value = R.attr.SecondTextColor)
    private int secondTextColorInvertRes;

    public int getBackgroundRes() {
        return backgroundRes;
    }

    public void setBackgroundRes(int backgroundRes) {
        this.backgroundRes = backgroundRes;
    }

    public int getBackgroundInvertRes() {
        return backgroundInvertRes;
    }

    public void setBackgroundInvertRes(int backgroundInvertRes) {
        this.backgroundInvertRes = backgroundInvertRes;
    }

    public int getSecondBackgroundRes() {
        return secondBackgroundRes;
    }

    public void setSecondBackgroundRes(int secondBackgroundRes) {
        this.secondBackgroundRes = secondBackgroundRes;
    }

    public int getSecondBackgroundInvertRes() {
        return secondBackgroundInvertRes;
    }

    public void setSecondBackgroundInvertRes(int secondBackgroundInvertRes) {
        this.secondBackgroundInvertRes = secondBackgroundInvertRes;
    }

    public int getMainTextColorRes() {
        return mainTextColorRes;
    }

    public void setMainTextColorRes(int mainTextColorRes) {
        this.mainTextColorRes = mainTextColorRes;
    }

    public int getMainTextColorInvertRes() {
        return mainTextColorInvertRes;
    }

    public void setMainTextColorInvertRes(int mainTextColorInvertRes) {
        this.mainTextColorInvertRes = mainTextColorInvertRes;
    }

    public int getSecondTextColorRes() {
        return secondTextColorRes;
    }

    public void setSecondTextColorRes(int secondTextColorRes) {
        this.secondTextColorRes = secondTextColorRes;
    }

    public int getSecondTextColorInvertRes() {
        return secondTextColorInvertRes;
    }

    public void setSecondTextColorInvertRes(int secondTextColorInvertRes) {
        this.secondTextColorInvertRes = secondTextColorInvertRes;
    }

    @Override
    public String toString() {
        return "ThemeInfo{" +
                "backgroundRes=" + backgroundRes +
                ", backgroundInvertRes=" + backgroundInvertRes +
                ", secondBackgroundRes=" + secondBackgroundRes +
                ", secondBackgroundInvertRes=" + secondBackgroundInvertRes +
                ", mainTextColorRes=" + mainTextColorRes +
                ", mainTextColorInvertRes=" + mainTextColorInvertRes +
                ", secondTextColorRes=" + secondTextColorRes +
                ", secondTextColorInvertRes=" + secondTextColorInvertRes +
                '}';
    }
}
