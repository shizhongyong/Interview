package com.shizy.interview.components.activity.drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.shizy.interview.R;

public class ViewDragLayout extends RelativeLayout {

	private static final String TAG = ViewDragHelper.class.getSimpleName();

	private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
		@Override
		public boolean tryCaptureView(@NonNull View child, int pointerId) {
			Log.e(TAG, "tryCaptureView: ");
			if (child.getId() == R.id.button) {
				return true;
			}
			return false;
		}

		@Override
		public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
			Log.e(TAG, "onViewCaptured: ");
			super.onViewCaptured(capturedChild, activePointerId);
		}

		@Override
		public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
			Log.e(TAG, "onViewReleased: ");
			super.onViewReleased(releasedChild, xvel, yvel);
			final int viewY = (int) releasedChild.getY();
			int finalY = 0;
			if (viewY < (mTopY + mMiddleY) / 2) {
				finalY = mTopY;
			} else if (viewY < (mMiddleY + mBottomY) / 2) {
				finalY = mMiddleY;
			} else {
				finalY = mBottomY;
			}
			mViewDragHelper.settleCapturedViewAt(0, finalY);
			invalidate();
		}

		@Override
		public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
			Log.e(TAG, "onViewPositionChanged: ");
			super.onViewPositionChanged(changedView, left, top, dx, dy);
		}

		@Override
		public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
			Log.e(TAG, "clampViewPositionVertical: " + top + "  |  " + dy);
//			return super.clampViewPositionVertical(child, top, dy);
			final int ret = Math.max(mTopY, Math.min(top, mBottomY));
			return ret;
		}
	};

	private ViewDragHelper mViewDragHelper;

	private int mBottomY = 0;
	private int mMiddleY = 0;
	private int mTopY = 0;
	private int mBottomHeight = 0;

	private View mButton;

	public ViewDragLayout(Context context) {
		this(context, null);
	}

	public ViewDragLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mViewDragHelper = ViewDragHelper.create(this, mCallback);
	}

	@Override
	public void computeScroll() {
		if (mViewDragHelper != null && mViewDragHelper.continueSettling(true)) {
			invalidate();
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mBottomHeight > 0) {
			return;
		}
		mBottomHeight = mButton.getHeight();
		mBottomY = getHeight() - mBottomHeight;
		mMiddleY = getHeight() - 5 * mBottomHeight;
		mTopY = 0;
//		mButton.scrollTo(0, mMiddleY);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mButton = findViewById(R.id.button);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (mViewDragHelper.shouldInterceptTouchEvent(ev)) {
			return true;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mViewDragHelper.processTouchEvent(event);
		return true;
	}
}
