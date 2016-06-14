package com.peerless2012.qingniantuzhai.activity;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.colorui.ThemeInfo;
import com.peerless2012.qingniantuzhai.colorui.util.ColorUiUtil;
import com.peerless2012.qingniantuzhai.utils.SPUtils;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/11 13:08
 * @Version V1.0
 * @Description :
 */
public class ThemeCompatActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setupTheme();
        super.onCreate(savedInstanceState);
    }


    private void setupTheme() {
        setTheme(SPUtils.getInstance(this).getTheme());
    }

    ThemeInfo changeTheme(int theme){
        setTheme(theme);
        TypedArray typedArray = getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        Drawable drawable = typedArray.getDrawable(0);
        getWindow().setBackgroundDrawable(drawable);
        typedArray.recycle();
        return ColorUiUtil.changeTheme(getWindow().getDecorView() ,getTheme());
    }

    void setUpStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            TypedArray typedArray = getTheme().obtainStyledAttributes(new int[]{android.support.v7.appcompat.R.attr.colorPrimaryDark});
            int color = typedArray.getColor(0, getResources().getColor(R.color.colorPrimary_dark));
            typedArray.recycle();

            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
        }
    }
}
