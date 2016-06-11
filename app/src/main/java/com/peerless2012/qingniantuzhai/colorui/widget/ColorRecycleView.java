package com.peerless2012.qingniantuzhai.colorui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;
import com.peerless2012.qingniantuzhai.colorui.ThemeInfo;
import com.peerless2012.qingniantuzhai.colorui.util.ColorUiUtil;
import com.peerless2012.qingniantuzhai.colorui.util.ViewAttributeUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/14 10:14
 * @Version V1.0
 * @Description : 切换主题的RecycleView
 */
public class ColorRecycleView extends RecyclerView implements ColorUiInterface {

    private ThemeInfo newThemeInfo = null;

    private int themeCount;

    public static final String TAG = "ColorRecycleView";

    private int attr_background = -1;

    public ColorRecycleView(Context context) {
        super(context);
    }

    public ColorRecycleView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
        addOnChildAttachStateChangeListener(new OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                Log.i(TAG, "onChildViewAttachedToWindow: ");
                Object tag = view.getTag(R.layout.activity_home);
                int hash = tag == null ? -1 : (int)tag;
                if (newThemeInfo != null){
                    if (hash != themeCount ){
                        ColorUiUtil.doChange(view,context.getTheme(),newThemeInfo);
                        view.setTag(R.layout.activity_home,themeCount);
                    }
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Log.i(TAG, "onChildViewDetachedFromWindow: ");

            }
        });
    }

    public ColorRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId, ThemeInfo themeInfo) {
        themeCount ++;
        newThemeInfo = themeInfo;
        if(attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
        }
    }

}
