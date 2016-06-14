package com.peerless2012.qingniantuzhai.common.glidemodule;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.peerless2012.qingniantuzhai.App;
import java.io.InputStream;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/14 12:26
 * @Version V1.0
 * @Description :
 */
public class OkHttpGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        App app = (App) context.getApplicationContext();
        DiskCache.Factory factory = null;
        factory = new DiskLruCacheFactory(app.getAppCacheDir().getAbsolutePath(),"glide",App.DEFAULT_DISK_CACHE_SIZE);
        builder.setDiskCache(factory);
        builder.setMemoryCache(new LruResourceCache(App.DEFAULT_MEMORY_CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
