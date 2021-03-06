package com.peerless2012.qingniantuzhai.colorui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;
import com.peerless2012.qingniantuzhai.colorui.ThemeInfo;
import com.peerless2012.qingniantuzhai.colorui.util.ViewAttributeUtil;

/**
 * Created by chengli on 15/6/8.
 */
public abstract class ColorViewGroup extends ViewGroup implements ColorUiInterface {

    private int attr_background = -1;

    public ColorViewGroup(Context context) {
        super(context);
    }

    public ColorViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public ColorViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId, ThemeInfo themeInfo) {
        ViewAttributeUtil.applyBackgroundDrawable(this, themeId , attr_background);
    }
}
