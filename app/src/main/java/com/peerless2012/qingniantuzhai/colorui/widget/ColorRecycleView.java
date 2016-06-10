package com.peerless2012.qingniantuzhai.colorui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;
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

    private int attr_background = -1;

    public ColorRecycleView(Context context) {
        super(context);
    }

    public ColorRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attr_background = ViewAttributeUtil.getBackgroundAttibute(attrs);
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
    public void setTheme(Resources.Theme themeId) {
        if(attr_background != -1) {
            try {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background);
            Field localField = null;
                localField = RecyclerView.class
                        .getDeclaredField("mRecycler");

            localField.setAccessible(true);
            Method localMethod = Class.forName(
                    "android.support.v7.widget.RecyclerView$Recycler")
                    .getDeclaredMethod("clear", new Class[0]);
            localMethod.setAccessible(true);
            localMethod.invoke(localField.get(this), new Object[0]);
            Log.e("", "### 清空RecyclerView的Recycer ");
            this.invalidate();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            try {
//                Field localField = RecyclerView.class.getDeclaredField("mChildHelper");
//                localField.setAccessible(true);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
        }
    }

}
