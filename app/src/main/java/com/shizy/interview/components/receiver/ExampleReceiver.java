package com.shizy.interview.components.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExampleReceiver extends BroadcastReceiver {

	private String TAG = ExampleReceiver.class.getSimpleName();

	public ExampleReceiver() {
		super();
	}

	public ExampleReceiver(String tag) {
		super();
		TAG = tag;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive");
		if (isOrderedBroadcast()) {
			abortBroadcast();
		}
	}

}
