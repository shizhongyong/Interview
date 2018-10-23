package com.shizy.interview.recyclerview.extend;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shizy.interview.R;
import com.shizy.interview.recyclerview.BaseViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LoadMoreAdapter extends BaseExtendAdapter {

	public interface LoadMoreListener {

		void startLoadMore();

	}

	private static final int STATE_IDLE = 0;
	private static final int STATE_LOADING = 1;
	private static final int STATE_ERROR = 2;
	private static final int STATE_OVER = 3;

	@IntDef({STATE_IDLE, STATE_LOADING, STATE_ERROR, STATE_OVER})
	@Retention(RetentionPolicy.SOURCE)
	public @interface LoadState {
	}

	private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
			if (!enable) {
				return;
			}
			if (newState == RecyclerView.SCROLL_STATE_IDLE) {
				if (mLoadState == STATE_IDLE && findLastVisibleItemPosition() + 1 == getItemCount()) {
					startLoad();
				}
			}
		}
	};

	private boolean enable = true;
	@LoadState
	private int mLoadState = STATE_IDLE;

	private RecyclerView.LayoutManager mLayoutManager;
	private LoadMoreListener mLoadMoreListener;

	public LoadMoreAdapter(RecyclerView.Adapter<BaseViewHolder> adapter) {
		super(adapter);
	}

	@Override
	public int getItemCount() {
		int itemCount = mAdapter.getItemCount();
		if (enable) {
			itemCount++;
		}
		return itemCount;
	}

	@Override
	public int getItemViewType(int position) {
		if (enable && position == mAdapter.getItemCount()) {
			return ViewTypeSpec.makeItemViewTypeSpec(0, ViewTypeSpec.LOADMORE);
		}
		return mAdapter.getItemViewType(position);
	}

	@NonNull
	@Override
	public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		final int type = ViewTypeSpec.getType(viewType);
		if (type == ViewTypeSpec.LOADMORE) {
			return new LoadMoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_load_more, parent, false));
		}
		return mAdapter.onCreateViewHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
		if (holder instanceof LoadMoreViewHolder) {
			((LoadMoreViewHolder) holder).setLoadState(mLoadState);
			return;
		}
		mAdapter.onBindViewHolder(holder, position);
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		recyclerView.addOnScrollListener(mOnScrollListener);
		mAdapter.onAttachedToRecyclerView(recyclerView);
		mLayoutManager = recyclerView.getLayoutManager();
	}

	@Override
	public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
		recyclerView.removeOnScrollListener(mOnScrollListener);
		mAdapter.onDetachedFromRecyclerView(recyclerView);
		mLayoutManager = null;
	}

	private int findLastVisibleItemPosition() {
		if (mLayoutManager instanceof LinearLayoutManager) {
			return ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
		}
		// TODO support other layout manager
		return -1;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
		notifyDataSetChanged();
	}

	private void setLoadState(@LoadState int state) {
		mLoadState = state;
		notifyDataSetChanged();
	}

	private void startLoad() {
		setLoadState(STATE_LOADING);
		if (mLoadMoreListener != null) {
			mLoadMoreListener.startLoadMore();
		}
	}

	/**
	 * 加载完成后调用
	 *
	 * @param isOver
	 */
	public void loadFinished(boolean isOver) {
		setLoadState(isOver ? STATE_OVER : STATE_IDLE);
	}

	/**
	 * 加载完成后调用
	 */
	public void loadError() {
		setLoadState(STATE_ERROR);
	}

	public void setLoadMoreListener(LoadMoreListener listener) {
		mLoadMoreListener = listener;
	}

	private class LoadMoreViewHolder extends FixedViewHolder {

		private View mLoadingView;
		private View mErrorView;
		private View mOverView;

		public LoadMoreViewHolder(View itemView) {
			super(itemView);
			mLoadingView = itemView.findViewById(R.id.view_loading);
			mErrorView = itemView.findViewById(R.id.view_error);
			mOverView = itemView.findViewById(R.id.view_over);

			mErrorView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoad();
				}
			});
		}

		public void setLoadState(@LoadState int state) {
			View stateView = null;
			switch (state) {
				case STATE_ERROR:
					stateView = mErrorView;
					break;
				case STATE_OVER:
					stateView = mOverView;
					break;
				case STATE_IDLE:
				case STATE_LOADING:
				default:
					stateView = mLoadingView;
					break;
			}

			final View[] views = {mLoadingView, mErrorView, mOverView};
			for (View view : views) {
				view.setVisibility(view == stateView ? View.VISIBLE : View.GONE);
			}
		}
	}

}
