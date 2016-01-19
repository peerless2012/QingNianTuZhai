package com.peerless2012.common.imgcache;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class MemoryLruImageCache extends LruCache<String, Bitmap> implements ImageCache {

	private final String TAG = this.getClass().getSimpleName();

	/**
	 * 
	 * @param maxSize
	 *            单位：字节
	 */
	public MemoryLruImageCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	/**
	 * 从缓存中获取位图
	 */
	@Override
	public Bitmap getBitmap(String url) {
		Log.v(TAG, "Retrieved item from MemoryCache");
		return get(url);
	}

	/**
	 * 将位图设置到缓存中
	 * 
	 */
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		Log.v(TAG, "Added item to MemoryCache");
		put(url, bitmap);
	}

	/**
	 * 清除所有的图片缓存信息
	 * 
	 */
	public void clear() {
		evictAll();
	}
}
