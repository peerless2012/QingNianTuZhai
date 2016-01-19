package com.peerless2012.common.imgcache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class ImageCacheManager {
	private static final CacheType DEFAULT_CACHE_TYPE = CacheType.ALL;// 默认为内存和磁盘缓存
	private static final int DEFAULT_MEMORY_CACHE_SIZE;// 单位：字节，默认内存缓存的大小，分配给应用的总内存的1/8
	private static final int DEFAULT_DISK_CACHE_SIZE = 50 * 1024 * 1024;// 单位：字节，默认磁盘缓存的大小，为50M

	private static final CompressFormat DEFAULT_COMPRESS_FORMAT = CompressFormat.PNG;
	private static final int DEFAULT_COMPRESS_QUALITY = 100;

	private File diskCacheDir;// 磁盘缓存的路径

	static {
		DEFAULT_MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 8);
	}

	/**
	 * 缓存的类型
	 */
	public enum CacheType {
		DISK, MEMORY, ALL
	}

	private static ImageCacheManager mInstance;

	/**
	 * Volley image loader
	 */
	private ImageLoader mImageLoader;

	/**
	 * Image cache implementation
	 */
	private BitmapImageCache mImageCache;

	/**
	 * 返回图片缓存器实例
	 * 
	 * @return
	 */
	public static ImageCacheManager getInstance() {
		if (mInstance == null)
			synchronized (ImageCacheManager.class) {
				if (mInstance == null) {
					mInstance = new ImageCacheManager();
				}
			}
		return mInstance;
	}

	public void init(Context context, File diskCacheDir) {
//		this.diskCacheDir = diskCacheDir;
		init(context, CacheType.MEMORY);
	}

	private void init(Context context, CacheType cacheType) {
		switch (cacheType) {
		case DISK:
			DiskLruImageCache diskLruImageCache = new DiskLruImageCache(context, diskCacheDir, DEFAULT_DISK_CACHE_SIZE, DEFAULT_COMPRESS_FORMAT, DEFAULT_COMPRESS_QUALITY);
			mImageCache = new BitmapImageCache(diskLruImageCache);
			break;
		case MEMORY:
			MemoryLruImageCache memoryLruImageCache = new MemoryLruImageCache(DEFAULT_MEMORY_CACHE_SIZE);
			mImageCache = new BitmapImageCache(memoryLruImageCache);
			break;
		default:
			DiskLruImageCache diskImageCache = new DiskLruImageCache(context, diskCacheDir, DEFAULT_DISK_CACHE_SIZE, DEFAULT_COMPRESS_FORMAT, DEFAULT_COMPRESS_QUALITY);
			MemoryLruImageCache memoryImageCache = new MemoryLruImageCache(DEFAULT_MEMORY_CACHE_SIZE);
			mImageCache = new BitmapImageCache(diskImageCache, memoryImageCache);
			break;
		}
		mImageLoader = new ImageLoader(RequestManager.getInstance(context), mImageCache);
	}

	public Bitmap getBitmap(String url) {
		try {
			if(TextUtils.isEmpty(url)) {
				return null;
			}
			return mImageCache.getBitmap(url);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}

	public void putBitmap(String url, Bitmap bitmap) {
		try {
			if(bitmap == null) {
				return;
			}
			mImageCache.putBitmap(url, bitmap);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Disk Cache Not initialized");
		}
	}

	/**
	 * 获取图片
	 * 
	 * @param url
	 * @param listener
	 */
	public void getImage(String url, ImageListener listener) {
		mImageLoader.get(url, listener);
	}

	/**
	 * 获取图片
	 * 
	 * @param url
	 * @param listener
	 * @param maxWidth
	 * @param maxHeight
	 */
	public void getImage(String url, ImageListener listener, int maxWidth, int maxHeight) {
		mImageLoader.get(url, listener, maxWidth, maxHeight);
	}

	/**
	 * @return instance of the image loader
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	public void clear() {
		if (mImageCache != null) {
			mImageCache.clear();
		}
	}

	/**
	 * 清除内存缓存
	 */
	public void clearMemoryCache() {
		if (mImageCache != null) {
			mImageCache.clearMemoryCache();
		}
	}

	/**
	 * 清除磁盘缓存
	 */
	public void clearDiskCache() {
		if (mImageCache != null) {
			mImageCache.clearDiskCache();
		}
	}

}
