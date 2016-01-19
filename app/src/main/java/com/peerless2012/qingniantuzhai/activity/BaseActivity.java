package com.peerless2012.qingniantuzhai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.peerless2012.qingniantuzhai.R;

/**
 * Created by Administrator on 2016/1/19.
 */
 abstract public class BaseActivity extends AppCompatActivity {

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
        setSupportActionBar(toolbar);
    }

    protected View getContentView(){
        return null;
    }

    protected abstract int getContentLayout();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();


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

}
