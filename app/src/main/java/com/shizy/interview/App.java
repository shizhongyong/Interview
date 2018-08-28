package com.shizy.interview;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class App extends Application {

	private static final String TAG = App.class.getSimpleName();

	private ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
			Log.d(TAG, "onActivityCreated: " + activity.getLocalClassName());
		}

		@Override
		public void onActivityStarted(Activity activity) {
			Log.d(TAG, "onActivityStarted: " + activity.getLocalClassName());
		}

		@Override
		public void onActivityResumed(Activity activity) {
			Log.d(TAG, "onActivityResumed: " + activity.getLocalClassName());
		}

		@Override
		public void onActivityPaused(Activity activity) {
			Log.d(TAG, "onActivityPaused: " + activity.getLocalClassName());
		}

		@Override
		public void onActivityStopped(Activity activity) {
			Log.d(TAG, "onActivityStopped: " + activity.getLocalClassName());
		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
			Log.d(TAG, "onActivitySaveInstanceState: " + activity.getLocalClassName());
		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			Log.d(TAG, "onActivityDestroyed: " + activity.getLocalClassName());
		}
	};


	@Override
	public void onCreate() {
		super.onCreate();
		registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
	}
}
