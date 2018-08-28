package com.shizy.interview.components.activity.launch_mode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shizy.interview.R;
import com.shizy.interview.components.activity.BaseActivity;

public class LaunchModeActivity extends BaseActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch_mode);
	}

	public void standard(View view) {
		startActivity(new Intent(this, StandardActivity.class));
	}

	public void singleTop(View view) {
		startActivity(new Intent(this, SingleTopActivity.class));
	}

	public void singleTask(View view) {
		startActivity(new Intent(this, SingleTaskActivity.class));
	}

	public void singleInstance(View view) {
		startActivity(new Intent(this, SingleInstanceActivity.class));
	}
}
