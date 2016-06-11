package com.peerless2012.qingniantuzhai.colorui.widget;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;
import com.peerless2012.qingniantuzhai.colorui.ThemeInfo;
import com.peerless2012.qingniantuzhai.colorui.util.ViewAttributeUtil;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/10 11:34
 * @Version V1.0
 * @Description :
 */
public class ColorToolBar extends Toolbar implements ColorUiInterface {

    private static final String TAG = "ColorToolBar";
    private int attr_background = -1;

    public ColorToolBar(Context context) {
        super(context);
    }

    public ColorToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = attrs.getAttributeName(i);
            Log.i(TAG, "ColorToolBar: "+attributeName);
        }
            Log.i(TAG, "ColorToolBar: -----------------------------------------------------------");
        TypedValue tv = new TypedValue();
        /*int identifier = context.getResources().getSystem().getIdentifier("background", "drawable", "android");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, android.support.v7.appcompat.R.styleable.Toolbar);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            typedArray.getValue(i,tv);
            Log.i(TAG, "ColorToolBar: "+tv.toString());
        }
        typedArray.recycle();*/
//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[]{
//                android.support.v7.appcompat.R.attr.toolbarStyle
//        });
//        TypedArray typedArray = context.obtainStyledAttributes(android.support.v7.appcompat.R.styleable.Toolbar);

        final TintTypedArray typedArray = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                android.support.v7.appcompat.R.styleable.Toolbar, 0, 0);
        int indexCount = typedArray.getIndexCount();
        int resourceId = typedArray.getResourceId(android.support.v7.appcompat.R.styleable.Toolbar_popupTheme, -1);
            Log.i(TAG, "ColorToolBar: "+(resourceId > 0 ? "获取到资源id ："+ resourceId : "未获取到资源id"));
        for (int i = 0; i < indexCount; i++) {
            typedArray.getValue(i,tv);
            Log.i(TAG, "ColorToolBar: "+tv.toString());
        }

        setTitleTextColor(getResources().getColor(R.color.main_textcolor_invert_normal));
    }

    public ColorToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                android.support.v7.appcompat.R.styleable.Toolbar, R.style.ToolBarLightStyle, 0);
        int indexCount = a.getIndexCount();
        TypedValue typeValue = new TypedValue();
        for (int i = 0; i < indexCount; i++) {
            a.getValue(i,typeValue);
            Log.i(TAG, "ColorToolBar: "+typeValue.toString());
        }
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setTheme(Resources.Theme themeId, ThemeInfo themeInfo) {
        TypedArray typedArray = themeId.obtainStyledAttributes(new int[]{
                android.support.v7.appcompat.R.attr.colorPrimary
        });
        int color = typedArray.getColor(0, Color.GREEN);
        setBackgroundColor(color);
        int toolbarStyle = themeInfo.getToolbarStyle();
        TypedArray popThemeArray = getContext().obtainStyledAttributes(toolbarStyle, android.support.v7.appcompat.R.styleable.Toolbar);
        int popThemeArrayResourceId = popThemeArray.getResourceId(android.support.v7.appcompat.R.styleable.Toolbar_popupTheme, R.style.ToolBarOverFlowLightStyle);
        setPopupTheme(popThemeArrayResourceId);
        getMenu().clear();
        inflateMenu(R.menu.activity_main);
        // TODO overflow主题还不能随着更改，待解决。
        popThemeArray.recycle();
        if (attr_background > 0){

//            setPopupTheme(a.getResourceId(android.support.v7.appcompat.R.styleable.Toolbar_popupTheme, 0));
//            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }
}
