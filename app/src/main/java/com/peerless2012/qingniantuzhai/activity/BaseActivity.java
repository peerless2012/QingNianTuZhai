package com.peerless2012.qingniantuzhai.activity;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.peerless2012.qingniantuzhai.R;
import com.peerless2012.qingniantuzhai.colorui.util.ColorUiUtil;
import com.peerless2012.qingniantuzhai.utils.SPUtils;
import java.io.File;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016/1/15 19:08
* @Version V1.0
* @Description: Activity的基类
*/
 abstract public class BaseActivity extends AppCompatActivity {
    protected String cacheDir;
    protected ViewGroup.LayoutParams contentViewParams;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        initActivity();
        initView();
        initListener();
        initData();
    }

    private void setTheme() {
        setTheme(SPUtils.getInstance(this).getTheme());
    }

    void changeTheme(View rootView, int theme){
        setTheme(theme);
        ColorUiUtil.changeTheme(rootView ,getTheme());
        changeStatusBar();
    }

    private void initActivity() {
        int contentLayoutRes = getContentLayout();
        if (contentLayoutRes > 0) {
            setContentView(contentLayoutRes);
        }else {
            View contentView = getContentView();
            if (contentView != null) {
                setContentView(contentView,contentViewParams);
            }else {
                throw new IllegalArgumentException("The content view layout res or view is null!");
            }
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (!isHome() && actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        File externalCache = getExternalCacheDir();
        if (externalCache != null) {
            cacheDir = externalCache.getAbsolutePath();
        }else {
            cacheDir = getCacheDir().getAbsolutePath();
        }
    }

    protected View getContentView(){
        return null;
    }


    /**
     * 是否是主页,如果不是主页,ToolBar应该是返回模式
     * @return true 如果是主页
     */
    protected boolean isHome(){
        return false;
    }

    /**
     * 获取Activity的布局文件id
     * @return 布局id
     */
    protected abstract int getContentLayout();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化监听回调
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    protected void setTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(Activity activity,int viewResId) {
        return (T)activity.findViewById(viewResId);
    }
    protected <T extends View> T getView(int viewResId) {
        return getView(this,viewResId);
    }
    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(View parent,int viewResId) {
        return (T)parent.findViewById(viewResId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 更改状态栏
            TypedArray typedArray = getTheme().obtainStyledAttributes(new int[]{android.R.attr.statusBarColor});
            int statusColor = typedArray.getColor(0, getResources().getColor(R.color.colorPrimary_dark));
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(statusColor);
        }
    }
}
