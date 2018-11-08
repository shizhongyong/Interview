package com.shizy.interview.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shizy.interview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通用对话框，使用同AlertDialog
 * shizy 2018/11/08
 */
public class CommonDialog extends Dialog {

	@BindView(R.id.scrollView)
	protected ScrollView mScrollView;
	@BindView(R.id.tv_title)
	protected TextView mTitleView;
	@BindView(R.id.message)
	protected TextView mMessageView;

	@BindView(R.id.btn_right)
	protected Button mButtonPositive;
	@BindView(R.id.btn_left)
	protected Button mButtonNegative;

	private CharSequence mButtonPositiveText;
	private OnClickListener mPositiveButtonListener;

	private CharSequence mButtonNegativeText;
	private OnClickListener mNegativeButtonListener;

	private CharSequence mTitle;
	private CharSequence mMessage;
	private View mView;
	private int mViewLayoutResId;
	private View mCustomTitleView;

	public CommonDialog(@NonNull Context context) {
		this(context, R.style.CommonDialog);
	}

	public CommonDialog(@NonNull Context context, int themeResId) {
		super(context, themeResId);
	}

	protected CommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
		this(context, R.style.CommonDialog);
		setCancelable(cancelable);
		setOnCancelListener(cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_common);
		ButterKnife.bind(this);

		setupView();
	}

	@OnClick({R.id.btn_left, R.id.btn_right})
	protected void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_left:
				if (mNegativeButtonListener != null) {
					mNegativeButtonListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
				}
				break;
			case R.id.btn_right:
				if (mPositiveButtonListener != null) {
					mPositiveButtonListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
				}
				break;
		}
		dismiss();
	}

	public void setTitle(CharSequence title) {
		mTitle = title;
		if (mTitleView != null) {
			mTitleView.setText(title);
		}
	}

	public void setCustomTitle(View customTitleView) {
		mCustomTitleView = customTitleView;
	}

	public void setMessage(CharSequence message) {
		mMessage = message;
		if (mMessageView != null) {
			mMessageView.setText(message);
		}
	}

	/**
	 * Set the view resource to display in the dialog.
	 */
	public void setView(int layoutResId) {
		mView = null;
		mViewLayoutResId = layoutResId;
	}

	/**
	 * Set the view to display in the dialog.
	 */
	public void setView(View view) {
		mView = view;
		mViewLayoutResId = 0;
	}

	public void setButton(int whichButton, CharSequence text, OnClickListener listener) {
		switch (whichButton) {
			case DialogInterface.BUTTON_POSITIVE:
				mButtonPositiveText = text;
				mPositiveButtonListener = listener;
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				mButtonNegativeText = text;
				mNegativeButtonListener = listener;
				break;
			default:
				throw new IllegalArgumentException("Button does not exist");
		}
	}

	public Button getButton(int whichButton) {
		switch (whichButton) {
			case DialogInterface.BUTTON_POSITIVE:
				return mButtonPositive;
			case DialogInterface.BUTTON_NEGATIVE:
				return mButtonNegative;
			default:
				return null;
		}
	}

	@SuppressWarnings({"UnusedDeclaration"})
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return mScrollView != null && mScrollView.executeKeyEvent(event);
	}

	@SuppressWarnings({"UnusedDeclaration"})
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mScrollView != null && mScrollView.executeKeyEvent(event);
	}

	private void setupView() {
		setupPanel();

		final LinearLayout topPanel = findViewById(R.id.topPanel);
		final boolean hasTitle = setupTitle(topPanel);

		final boolean hasButtons = setupButtons();
		final View buttonPanel = findViewById(R.id.buttonPanel);
		if (!hasButtons) {
			buttonPanel.setVisibility(View.GONE);
		}

		final FrameLayout customPanel = findViewById(R.id.customPanel);
		final View customView;
		if (mView != null) {
			customView = mView;
		} else if (mViewLayoutResId != 0) {
			final LayoutInflater inflater = LayoutInflater.from(getContext());
			customView = inflater.inflate(mViewLayoutResId, customPanel, false);
		} else {
			customView = null;
		}

		final boolean hasCustomView = customView != null;
		if (!hasCustomView || !canTextInput(customView)) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
					WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		}

		if (hasCustomView) {
			final FrameLayout custom = findViewById(R.id.custom);
			custom.addView(customView, new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		} else {
			customPanel.setVisibility(View.GONE);
		}
	}

	private void setupPanel() {
		final LinearLayout parentPanel = findViewById(R.id.parentPanel);
		final Point point = new Point();
		getWindow().getWindowManager().getDefaultDisplay().getSize(point);

		ViewGroup.LayoutParams params = parentPanel.getLayoutParams();
		params.width = (int) (Math.min(point.x, point.y) * 0.75);
		parentPanel.setLayoutParams(params);

		final LinearLayout contentPanel = findViewById(R.id.contentPanel);
		setupContent(contentPanel);
	}

	private boolean setupTitle(LinearLayout topPanel) {
		boolean hasTitle = true;

		if (mCustomTitleView != null) {
			// Add the custom title view directly to the topPanel layout
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			topPanel.addView(mCustomTitleView, 0, lp);

			mTitleView.setVisibility(View.GONE);
		} else {
			final boolean hasTextTitle = !TextUtils.isEmpty(mTitle);
			if (hasTextTitle) {
				// Display the title if a title is supplied, else hide it.
				mTitleView.setText(mTitle);
			} else {
				mTitleView.setVisibility(View.GONE);
				topPanel.setVisibility(View.GONE);
				hasTitle = false;
			}
		}
		return hasTitle;
	}

	private void setupContent(LinearLayout contentPanel) {
		mScrollView.setFocusable(false);

		// Special case for users that only want to display a String
		if (mMessageView == null) {
			return;
		}

		if (mMessage != null) {
			mMessageView.setText(mMessage);
		} else {
			mMessageView.setVisibility(View.GONE);
			mScrollView.removeView(mMessageView);

			contentPanel.setVisibility(View.GONE);
		}
	}

	private boolean setupButtons() {
		int BIT_BUTTON_POSITIVE = 1;
		int BIT_BUTTON_NEGATIVE = 2;

		int whichButtons = 0;

		if (TextUtils.isEmpty(mButtonPositiveText)) {
			mButtonPositive.setVisibility(View.GONE);
		} else {
			mButtonPositive.setText(mButtonPositiveText);
			mButtonPositive.setVisibility(View.VISIBLE);
			whichButtons = whichButtons | BIT_BUTTON_POSITIVE;
		}

		if (TextUtils.isEmpty(mButtonNegativeText)) {
			mButtonNegative.setVisibility(View.GONE);
		} else {
			mButtonNegative.setText(mButtonNegativeText);
			mButtonNegative.setVisibility(View.VISIBLE);

			whichButtons = whichButtons | BIT_BUTTON_NEGATIVE;
		}

		return whichButtons != 0;
	}

	static boolean canTextInput(View v) {
		if (v.onCheckIsTextEditor()) {
			return true;
		}

		if (!(v instanceof ViewGroup)) {
			return false;
		}

		ViewGroup vg = (ViewGroup) v;
		int i = vg.getChildCount();
		while (i > 0) {
			i--;
			v = vg.getChildAt(i);
			if (canTextInput(v)) {
				return true;
			}
		}

		return false;
	}

	private static class Params {

		private Context mContext;
		private CharSequence mTitle;
		private View mCustomTitleView;

		private CharSequence mMessage;

		private CharSequence mPositiveButtonText;
		private OnClickListener mPositiveButtonListener;

		private CharSequence mNegativeButtonText;
		private OnClickListener mNegativeButtonListener;

		private boolean mCancelable;
		private OnCancelListener mOnCancelListener;

		private OnDismissListener mOnDismissListener;
		private OnKeyListener mOnKeyListener;

		private View mView = null;
		private int mViewLayoutResId;

		public Params(Context context) {
			mContext = context;
			mCancelable = true;
		}

		private void apply(CommonDialog dialog) {
			if (mCustomTitleView != null) {
				dialog.setCustomTitle(mCustomTitleView);
			} else {
				if (mTitle != null) {
					dialog.setTitle(mTitle);
				}
			}
			if (mMessage != null) {
				dialog.setMessage(mMessage);
			}
			if (mPositiveButtonText != null) {
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, mPositiveButtonText,
						mPositiveButtonListener);
			}
			if (mNegativeButtonText != null) {
				dialog.setButton(DialogInterface.BUTTON_NEGATIVE, mNegativeButtonText,
						mNegativeButtonListener);
			}
			if (mView != null) {
				dialog.setView(mView);
			} else if (mViewLayoutResId != 0) {
				dialog.setView(mViewLayoutResId);
			}
		}

	}

	public static class Builder {

		private final Params P;

		public Builder(Context context) {
			P = new Params(context);
		}

		public Context getContext() {
			return P.mContext;
		}

		public CommonDialog.Builder setTitle(CharSequence title) {
			P.mTitle = title;
			return this;
		}

		public CommonDialog.Builder setCustomTitle(View customTitleView) {
			P.mCustomTitleView = customTitleView;
			return this;
		}

		public CommonDialog.Builder setMessage(@StringRes int messageId) {
			P.mMessage = P.mContext.getText(messageId);
			return this;
		}

		public CommonDialog.Builder setMessage(CharSequence message) {
			P.mMessage = message;
			return this;
		}

		public CommonDialog.Builder setPositiveButton(@StringRes int textId, final OnClickListener listener) {
			P.mPositiveButtonText = P.mContext.getText(textId);
			P.mPositiveButtonListener = listener;
			return this;
		}

		public CommonDialog.Builder setPositiveButton(CharSequence text, final OnClickListener listener) {
			P.mPositiveButtonText = text;
			P.mPositiveButtonListener = listener;
			return this;
		}

		public CommonDialog.Builder setNegativeButton(@StringRes int textId, final OnClickListener listener) {
			P.mNegativeButtonText = P.mContext.getText(textId);
			P.mNegativeButtonListener = listener;
			return this;
		}

		public CommonDialog.Builder setNegativeButton(CharSequence text, final OnClickListener listener) {
			P.mNegativeButtonText = text;
			P.mNegativeButtonListener = listener;
			return this;
		}

		public CommonDialog.Builder setCancelable(boolean cancelable) {
			P.mCancelable = cancelable;
			return this;
		}

		public CommonDialog.Builder setOnCancelListener(OnCancelListener onCancelListener) {
			P.mOnCancelListener = onCancelListener;
			return this;
		}

		public CommonDialog.Builder setOnDismissListener(OnDismissListener onDismissListener) {
			P.mOnDismissListener = onDismissListener;
			return this;
		}

		public CommonDialog.Builder setOnKeyListener(OnKeyListener onKeyListener) {
			P.mOnKeyListener = onKeyListener;
			return this;
		}

		public CommonDialog.Builder setView(int layoutResId) {
			P.mView = null;
			P.mViewLayoutResId = layoutResId;
			return this;
		}

		public CommonDialog.Builder setView(View view) {
			P.mView = view;
			P.mViewLayoutResId = 0;
			return this;
		}

		public CommonDialog create() {
			// Context has already been wrapped with the appropriate theme.
			final CommonDialog dialog = new CommonDialog(P.mContext);
			P.apply(dialog);
			dialog.setCancelable(P.mCancelable);
			if (P.mCancelable) {
				dialog.setCanceledOnTouchOutside(true);
			}
			dialog.setOnCancelListener(P.mOnCancelListener);
			dialog.setOnDismissListener(P.mOnDismissListener);
			if (P.mOnKeyListener != null) {
				dialog.setOnKeyListener(P.mOnKeyListener);
			}
			return dialog;
		}

		public CommonDialog show() {
			final CommonDialog dialog = create();
			dialog.show();
			return dialog;
		}
	}


}
