package com.peerless2012.qingniantuzhai.colorui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;
import com.peerless2012.qingniantuzhai.colorui.ThemeInfo;
import com.peerless2012.qingniantuzhai.colorui.util.ViewAttributeUtil;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/10 23:19
 * @Version V1.0
 * @Description :
 */
public class ColorNavigationView extends NavigationView implements ColorUiInterface {

    private static final String TAG = "ColorNavigationView";
    private int attr_backgound = -1;

    public ColorNavigationView(Context context) {
        super(context);
    }

    public ColorNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        int attributeCount = attrs.getAttributeCount();
//        for (int i = 0; i < attributeCount; i++) {
//            String attributeName = attrs.getAttributeName(i);
//            String attributeValue = attrs.getAttributeValue(i);
//            if ("background".equals(attributeName)){
//                this.attr_backgound = Integer.parseInt(attributeValue.substring(1));
//            }
//            Log.i(TAG, "ColorNavigationView: Name = "+attributeName+"   ,Value = "+attributeValue);
//        }
        this.attr_backgound = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    public ColorNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_backgound = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId, ThemeInfo themeInfo) {
        ColorStateList colorStateList = getResources().getColorStateList(themeInfo.getMainTextColorRes());
        setItemTextColor(colorStateList);
        setItemIconTintList(colorStateList);
        if(attr_backgound != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_backgound);
        }
    }
}
