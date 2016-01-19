package com.peerless2012.common.imgcache;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestManager {

	private static RequestQueue instance;

	private RequestManager() {
	}

	public static RequestQueue getInstance(Context context) {
		if (instance == null) {
			synchronized (RequestQueue.class) {
				if (instance == null) {
					instance = Volley.newRequestQueue(context);
				}
			}
		}
		return instance;
	}
}
