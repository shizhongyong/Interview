package com.shizy.interview.components.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shizy.interview.components.activity.anim.AnimationActivity;
import com.shizy.interview.components.activity.anim.CardFlipActivity;
import com.shizy.interview.components.activity.anim.CrossfadeActivity;
import com.shizy.interview.components.activity.anim.ZoomActivity;
import com.shizy.interview.components.activity.drag.ViewDragActivity;
import com.shizy.interview.components.activity.gesture.GestureBuilderActivity;
import com.shizy.interview.components.activity.launch_mode.LaunchModeActivity;
import com.shizy.interview.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

	private static final int RC_PERMISSION = 1;

	private static final Class[] CLASS_LIST = new Class[] {
			LaunchModeActivity.class,
			ServiceActivity.class,
			AsyncTaskActivity.class,
			ReceiverActivity.class,
			AnimationActivity.class,
			CrossfadeActivity.class,
			CardFlipActivity.class,
			ZoomActivity.class,
			BottomSheetActivity.class,
			ViewDragActivity.class,
			GestureBuilderActivity.class
	};

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		for (int i = 0; i < permissions.length; i++) {
			if (grantResults[i] != PackageManager.PERMISSION_GRANTED && PermissionUtil.isRuntime(this, permissions[i])) {

				return;
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CLASS_LIST));

		List<String> permissions = PermissionUtil.getPermissions(this);
		List<String> denied = new ArrayList<>();
		for (String permission : permissions) {
			if (!PermissionUtil.isGranted(this, permission)) {
				denied.add(permission);
			}
		}

		if (denied.size() > 0) {
			String[] deniedPermissions = new String[denied.size()];
			denied.toArray(deniedPermissions);
			ActivityCompat.requestPermissions(this, deniedPermissions, RC_PERMISSION);
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		startActivity(new Intent(this, CLASS_LIST[position]));
	}
}
