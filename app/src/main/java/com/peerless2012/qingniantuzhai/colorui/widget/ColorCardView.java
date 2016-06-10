package com.peerless2012.qingniantuzhai.colorui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;
import com.peerless2012.qingniantuzhai.colorui.ThemeInfo;
import com.peerless2012.qingniantuzhai.colorui.util.ViewAttributeUtil;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/9 21:29
 * @Version V1.0
 * @Description :
 */
public class ColorCardView extends CardView implements ColorUiInterface {

    private int attr_background = -1;

    public ColorCardView(Context context) {
        super(context);
    }

    public ColorCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public ColorCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId, ThemeInfo themeInfo) {
        if(attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }
}