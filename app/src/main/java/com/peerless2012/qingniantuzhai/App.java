package com.peerless2012.qingniantuzhai;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import java.io.File;

/**
* @Author peerless2012
* @Email peerless2012@126.com
* @DateTime 2016/6/1 11:27
* @Version V1.0
* @Description: 全局Application类
*/
public class App extends Application{

    public static final int DEFAULT_DISK_CACHE_SIZE = 200 * 1024 * 1024;

    public static final int DEFAULT_MEMORY_CACHE_SIZE;// 单位：字节，默认内存缓存的大小，分配给应用的总内存的1/8

    static {
        DEFAULT_MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8);
    }

    private File cacheDir;

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
    }

    public File getAppCacheDir(){
        return cacheDir;
    }

    /**
     * 初始化图片缓存管理器
     */
    private void initImageCacheManager() {
        File[] externalCacheDirs = ContextCompat.getExternalCacheDirs(this);
        if (externalCacheDirs == null || externalCacheDirs.length == 0){
            cacheDir = getCacheDir();
        }else if (externalCacheDirs.length == 2){
            cacheDir = externalCacheDirs[1];
        }else {
            cacheDir = externalCacheDirs[0];
        }
    }
}
