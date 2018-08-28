package com.shizy.interview.components.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.shizy.interview.R;
import com.shizy.interview.components.receiver.ExampleReceiver;

public class ReceiverActivity extends BaseActivity {

	private static final String TAG = ReceiverActivity.class.getSimpleName();
	private static final String ACTION = "com.shizy.interview.RECEIVER";

	private BroadcastReceiver mReceiver;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recevier);

		mReceiver = new ExampleReceiver(TAG);
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION);
		registerReceiver(mReceiver, filter);

		// 应用内的广播可以使用LocalBroadcastManager
//		LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	/**
	 * 动态注册要先于静态注册收到广播
	 *
	 * @param view
	 */
	public void sendNormal(View view) {
		Intent intent = new Intent(ACTION);
		intent.setPackage(getPackageName());// 必须加这一句，否则静态注册的隐式广播接收器无法收到广播。
		sendBroadcast(intent);
//		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	/**
	 * 动态注册要先于静态注册收到广播
	 *
	 * @param view
	 */
	public void sendOrdered(View view) {
		Intent intent = new Intent(ACTION);
		intent.setPackage(getPackageName());// 必须加这一句，否则静态注册的隐式广播接收器无法收到广播。
		sendOrderedBroadcast(intent, null);
	}

}
