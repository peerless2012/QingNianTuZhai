package com.peerless2012.qingniantuzhai.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * author peerless2012
 * 2016/1/23 21:56
 */
public class BaseFragment extends Fragment {
    protected String cacheDir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File externalCache = getContext().getExternalCacheDir();
        if (externalCache != null) {
            cacheDir = externalCache.getAbsolutePath();
        }else {
            cacheDir = getContext().getCacheDir().getAbsolutePath();
        }
    }
}
