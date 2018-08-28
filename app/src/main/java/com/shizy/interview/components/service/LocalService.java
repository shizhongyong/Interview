package com.shizy.interview.components.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LocalService extends BaseService {

	private static final String TAG = LocalService.class.getSimpleName();

	/**
	 * 不需要跨进程通信，直接继承Binder即可
	 */
	public class LocalBinder extends Binder {

		public LocalService getService() {
			return LocalService.this;
		}

	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return new LocalBinder();
	}

}
