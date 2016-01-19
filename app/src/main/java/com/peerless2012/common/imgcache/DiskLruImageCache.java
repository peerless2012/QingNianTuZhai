package com.peerless2012.common.imgcache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.peerless2012.common.imgcache.disk.DiskLruCache;
import com.peerless2012.qingniantuzhai.BuildConfig;

/**
 * Implementation of DiskLruCache by Jake Wharton modified from
 * http://stackoverflow
 * .com/questions/10185898/using-disklrucache-in-android-4-0-
 * does-not-provide-for-opencache-method
 */
public class DiskLruImageCache implements ImageCache {

	private DiskLruCache mDiskCache;
	private CompressFormat mCompressFormat = CompressFormat.PNG;
	private static int IO_BUFFER_SIZE = 1024;
	private int mCompressQuality = 100;
	private static final int APP_VERSION = 1;
	private static final int VALUE_COUNT = 1;

	public DiskLruImageCache(Context context, File diskCacheDir, int diskCacheSize, CompressFormat compressFormat, int quality) {
		try {
			mDiskCache = DiskLruCache.open(diskCacheDir, APP_VERSION, VALUE_COUNT, diskCacheSize);
			mCompressFormat = compressFormat;
			mCompressQuality = quality;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean writeBitmapToFile(Bitmap bitmap, DiskLruCache.Editor editor) throws IOException, FileNotFoundException {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(editor.newOutputStream(0), IO_BUFFER_SIZE);
			return bitmap.compress(mCompressFormat, mCompressQuality, out);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	@Override
	public void putBitmap(String key, Bitmap data) {
		DiskLruCache.Editor editor = null;
		try {
			editor = mDiskCache.edit(key);
			if (editor == null) {
				return;
			}

			if (writeBitmapToFile(data, editor)) {
				mDiskCache.flush();
				editor.commit();
				if (BuildConfig.DEBUG) {
					Log.d("cache_test_DISK_", "image put on disk cache " + key);
				}
			} else {
				editor.abort();
				if (BuildConfig.DEBUG) {
					Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
				}
			}
		} catch (IOException e) {
			if (BuildConfig.DEBUG) {
				Log.d("cache_test_DISK_", "ERROR on: image put on disk cache " + key);
			}
			try {
				if (editor != null) {
					editor.abort();
				}
			} catch (IOException ignored) {
			}
		}

	}

	@Override
	public Bitmap getBitmap(String key) {

		Bitmap bitmap = null;
		DiskLruCache.Snapshot snapshot = null;
		try {
			snapshot = mDiskCache.get(key);
			if (snapshot == null) {
				return null;
			}
			final InputStream in = snapshot.getInputStream(0);
			if (in != null) {
				final BufferedInputStream buffIn = new BufferedInputStream(in, IO_BUFFER_SIZE);
				bitmap = BitmapFactory.decodeStream(buffIn);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (snapshot != null) {
				snapshot.close();
			}
		}

		if (BuildConfig.DEBUG) {
			Log.d("cache_test_DISK_", bitmap == null ? "" : "image read from disk " + key);
		}

		return bitmap;

	}

	public boolean containsKey(String key) {

		boolean contained = false;
		DiskLruCache.Snapshot snapshot = null;
		try {
			snapshot = mDiskCache.get(key);
			contained = snapshot != null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (snapshot != null) {
				snapshot.close();
			}
		}

		return contained;

	}

	public void clearCache() {
		if (BuildConfig.DEBUG) {
			Log.d("cache_test_DISK_", "disk cache CLEARED");
		}
		try {
			mDiskCache.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File getCacheFolder() {
		return mDiskCache.getDirectory();
	}

	public void clear() {
		clearCache();
	}
}
