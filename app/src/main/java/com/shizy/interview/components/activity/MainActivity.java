package com.shizy.interview.components.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shizy.interview.components.activity.launch_mode.LaunchModeActivity;

public class MainActivity extends ListActivity {

	private static final Class[] CLASS_LIST = new Class[] {
			LaunchModeActivity.class,
			ServiceActivity.class
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, CLASS_LIST));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		startActivity(new Intent(this, CLASS_LIST[position]));
	}
}
