package com.peerless2012.qingniantuzhai;

import android.app.Application;
import android.os.Build;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.peerless2012.common.imgcache.ImageCacheManager;
import com.peerless2012.qingniantuzhai.utils.FileUtils;
import java.io.File;

/**
 * Created by Administrator on 2016/1/19.
 */
public class App extends Application{

    private File cacheDir;

    private File fileDir;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initImageCacheManager();
    }

    private void init() {
        Logger
                .init("qntz")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logTool(new AndroidLogTool()); // custom log tool, optional

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            File[] caches = getExternalCacheDirs();
            if (caches.length > 1)
                cacheDir = caches[1];
            else
                cacheDir = caches[0];
        }else {
            cacheDir = getExternalCacheDir();
        }

    }

    public File getAppCacheDir(){
        return cacheDir;
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
