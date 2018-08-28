package com.shizy.interview.components.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.shizy.interview.R;

public class AsyncTaskActivity extends BaseActivity {

	private static final String TAG = AsyncTaskActivity.class.getSimpleName();

	private TextView mProgressTv;
	private InternalAsyncTask mTask;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_task);

		mProgressTv = findViewById(R.id.tv_progress);

		mTask = new InternalAsyncTask();
		mTask.execute("Start");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mTask != null && !mTask.isCancelled()) {
			mTask.cancel(true);
		}
	}

	private class InternalAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... strings) {
			int progress = 0;
			do {
				if (isCancelled()) {
					return "Interrupted";
				}
				Log.d(TAG, String.valueOf(progress));
				publishProgress(progress + "%");
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				progress++;
			} while (progress < 100);

			return "Completed!";
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			if (values.length > 0) {
				mProgressTv.setText(values[0]);
			}
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (isCancelled()) {
				return;
			}
			mProgressTv.setText(result);
		}
	}

}
