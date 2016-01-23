package com.peerless2012.qingniantuzhai;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.peerless2012.common.imgcache.ImageCacheManager;
import com.peerless2012.qingniantuzhai.utils.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2016/1/19.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initImageCacheManager();
    }

    /**
     * 初始化图片缓存管理器
     */
    private void initImageCacheManager() {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
            ImageLoader.getInstance().init(config);
        }


        StringBuffer sb = new StringBuffer();
        File externalCacheDir = getExternalFilesDir(null);
        if (externalCacheDir != null) {
            sb.append(externalCacheDir.getAbsolutePath()).append(File.separator).append("imgcache");
        }else {
            sb.append(getFilesDir().getAbsolutePath()).append(File.separator).append("imgcache");
        }
        String path = sb.toString();
        File diskCacheDir = FileUtils.createDir(path);
        ImageCacheManager.getInstance().init(getApplicationContext(), diskCacheDir);
    }
}
