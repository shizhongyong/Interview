package com.shizy.interview.components.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public abstract class BaseActivity extends AppCompatActivity {

	private final String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getClass().getSimpleName());
		Log.d(TAG, "Window: " + getWindow());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.d(TAG, "onNewIntent");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(TAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(TAG, "onRestoreInstanceState");
	}

}
