package com.shizy.interview.components.activity.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shizy.interview.R;
import com.shizy.interview.components.activity.BaseActivity;

public class CardFlipActivity extends BaseActivity {

	private boolean mShowingBack = false;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.fragment_container, new CardFrontFragment())
					.commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		getWindow().getDecorView().postDelayed(new Runnable() {
			@Override
			public void run() {
				flipCard();
			}
		}, 1000);
	}

	private void flipCard() {
		if (mShowingBack) {
			getFragmentManager().popBackStack();
			return;
		}

		// Flip to the back.

		mShowingBack = true;

		// Create and commit a new fragment transaction that adds the fragment for
		// the back of the card, uses custom animations, and is part of the fragment
		// manager's back stack.

		getSupportFragmentManager()
				.beginTransaction()

				// Replace the default fragment animations with animator resources
				// representing rotations when switching to the back of the card, as
				// well as animator resources representing rotations when flipping
				// back to the front (e.g. when the system Back button is pressed).
				.setCustomAnimations(
						R.animator.card_flip_right_in,
						R.animator.card_flip_right_out,
						R.animator.card_flip_left_in,
						R.animator.card_flip_left_out)

				// Replace any fragments currently in the container view with a
				// fragment representing the next page (indicated by the
				// just-incremented currentPage variable).
				.replace(R.id.fragment_container, new CardBackFragment())

				// Add this transaction to the back stack, allowing users to press
				// Back to get to the front of the card.
				.addToBackStack(null)

				// Commit the transaction.
				.commit();
	}

	/**
	 * A fragment representing the front of the card.
	 */
	public static class CardFrontFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_card_front, container, false);
		}
	}

	/**
	 * A fragment representing the back of the card.
	 */
	public static class CardBackFragment extends Fragment {
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			return inflater.inflate(R.layout.fragment_card_back, container, false);
		}
	}

}
