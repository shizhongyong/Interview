package com.shizy.interview.components.activity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.shizy.interview.R;

public class BottomSheetActivity extends BaseActivity {

	private static final String TAG = BottomSheetActivity.class.getSimpleName();

	private ViewGroup mTitleLayout;
	private ViewGroup mBottomLayout;

	private int mBottomHeight;
	private int mActionBarSize;
	private int mPeekHeight;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bottom_sheet);

		mTitleLayout = findViewById(R.id.layout_title);
		mBottomLayout = findViewById(R.id.layout_bottom);

		mActionBarSize = getActionBarSize();

		findViewById(R.id.btn_dialog).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showBottomSheetDialog();
			}
		});

		BottomSheetBehavior behavior = BottomSheetBehavior.from(mBottomLayout);
		behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				Log.e(TAG, "shizy---newState: " + newState);
				setBottomLayoutHeight();
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {
				Log.e(TAG, "shizy---slideOffset:" + slideOffset);


				float surplusHeight = ((mBottomHeight - mPeekHeight) * (1 - slideOffset));
				surplusHeight = Math.min(mActionBarSize, surplusHeight);

				Log.e(TAG, "shizy---surplusHeight:" + surplusHeight);

				mTitleLayout.setTranslationY(-surplusHeight);

			}
		});

		Log.e(TAG, "shizy---getState: " + behavior.getState());
		Log.e(TAG, "shizy---getPeekHeight: " + behavior.getPeekHeight());
		Log.e(TAG, "shizy---getSkipCollapsed: " + behavior.getSkipCollapsed());

		mPeekHeight = behavior.getPeekHeight();
		mTitleLayout.setTranslationY(-mActionBarSize);
	}

	private void setBottomLayoutHeight() {
		if (mBottomHeight <= 0) {
			final int height = mBottomLayout.getHeight();
			mBottomHeight = height - mActionBarSize;
			ViewGroup.LayoutParams params = mBottomLayout.getLayoutParams();
			params.height = mBottomHeight;
			mBottomLayout.setLayoutParams(params);
		}
	}

	private void showBottomSheetDialog() {
		BottomSheetDialog dialog = new BottomSheetDialog(this);
		dialog.setContentView(R.layout.activity_zoom);
		dialog.show();
	}

	private int getActionBarSize() {
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
		return (int) typedValue.getDimension(getResources().getDisplayMetrics());
	}

}
