package com.shizy.interview.components.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessengerService extends BaseService {

	private static final String TAG = MessengerService.class.getSimpleName();

	/**
	 * 使用Messenger实现跨进程通信，比AIDL简单，Messenger会将请求排队，逐个调用。
	 */
	@SuppressLint("HandlerLeak")
	private final Messenger mMessenger = new Messenger(new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Log.d(TAG, "Messenger handleMessage");
		}
	});

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return mMessenger.getBinder();
	}

}
