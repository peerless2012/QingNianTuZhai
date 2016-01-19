package com.peerless2012.common.imgcache;

import android.graphics.Bitmap;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapImageCache implements ImageCache {

	private DiskLruImageCache mDiskImageCache;
	private MemoryLruImageCache mMemoryImageCache;

	public BitmapImageCache(DiskLruImageCache diskImageCache) {
		this.mDiskImageCache = diskImageCache;
	}

	public BitmapImageCache(MemoryLruImageCache memoryImageCache) {
		this.mMemoryImageCache = memoryImageCache;
	}

	public BitmapImageCache(DiskLruImageCache diskImageCache, MemoryLruImageCache memoryImageCache) {
		this.mDiskImageCache = diskImageCache;
		this.mMemoryImageCache = memoryImageCache;
	}

	@Override
	public Bitmap getBitmap(String url) {
		url = createKey(url);
		Bitmap bitmap = null;
		if (mMemoryImageCache != null) {
			bitmap = mMemoryImageCache.getBitmap(url);
		}

		if (mDiskImageCache != null && bitmap == null) {
			bitmap = mDiskImageCache.getBitmap(url);
			if (bitmap != null && mMemoryImageCache != null) {// 将其放入内存缓存中，下次直接从内存缓存中取
				mMemoryImageCache.putBitmap(url, bitmap);
			}
		}

		return bitmap;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		url = createKey(url);
		if (mMemoryImageCache != null) {
			mMemoryImageCache.putBitmap(url, bitmap);
		}
		if (mDiskImageCache != null) {
			mDiskImageCache.putBitmap(url, bitmap);
		}
	}

	/**
	 * 清除所有缓存
	 */
	public void clear() {
		if (mMemoryImageCache != null) {
			mMemoryImageCache.clear();
		}
		if (mDiskImageCache != null) {
			mDiskImageCache.clear();
		}
	}

	/**
	 * 清除内存缓存
	 */
	public void clearMemoryCache() {
		if (mMemoryImageCache != null) {
			mMemoryImageCache.clear();
		}
	}

	/**
	 * 清除磁盘缓存
	 */
	public void clearDiskCache() {
		if (mDiskImageCache != null) {
			mDiskImageCache.clear();
		}
	}

	/**
	 * Creates a unique cache key based on a url value
	 * 
	 * @param url
	 *            url to be used in key creation
	 * @return cache key value
	 */
	private String createKey(String url) {
		return String.valueOf(url.hashCode());
	}

}
