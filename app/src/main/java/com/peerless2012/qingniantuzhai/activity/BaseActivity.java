package com.peerless2012.qingniantuzhai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.peerless2012.qingniantuzhai.R;
import java.io.File;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016/1/15 19:08
* @Version V1.0
* @Description: Activity的基类
*/
 abstract public class BaseActivity extends ThemeAppcompatActivity {
    protected String cacheDir;
    protected ViewGroup.LayoutParams contentViewParams;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity();
        initView();
        initListener();
        initData();
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
        if (toolbar != null){
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (!isHome() && actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
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
}
