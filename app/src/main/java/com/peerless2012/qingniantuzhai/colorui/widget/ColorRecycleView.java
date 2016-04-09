package com.peerless2012.qingniantuzhai.colorui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.peerless2012.qingniantuzhai.colorui.ColorUiInterface;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/5/14 10:14
 * @Version V1.0
 * @Description : 切换主题的RecycleView
 */
public class ColorRecycleView extends RecyclerView implements ColorUiInterface {

    public ColorRecycleView(Context context) {
        super(context);
    }

    public ColorRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {

    }
}
