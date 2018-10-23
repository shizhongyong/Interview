package com.shizy.interview.recyclerview.extend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.shizy.interview.recyclerview.BaseViewHolder;

public class BaseExtendAdapter extends RecyclerView.Adapter<BaseViewHolder> {

	protected final RecyclerView.Adapter<BaseViewHolder> mAdapter;

	public BaseExtendAdapter(RecyclerView.Adapter<BaseViewHolder> adapter) {
		if (adapter == null) {
			throw new IllegalArgumentException("adapter can not be null!");
		}
		mAdapter = adapter;
	}

	@Override
	public int getItemViewType(int position) {
		return mAdapter.getItemViewType(position);
	}

	@NonNull
	@Override
	public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return mAdapter.onCreateViewHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
		mAdapter.onBindViewHolder(holder, position);
	}

	@Override
	public int getItemCount() {
		return mAdapter.getItemCount();
	}

	@Override
	public void setHasStableIds(boolean hasStableIds) {
		mAdapter.setHasStableIds(hasStableIds);
	}

	@Override
	public long getItemId(int position) {
		return mAdapter.getItemId(position);
	}

	@Override
	public void onViewRecycled(@NonNull BaseViewHolder holder) {
		if (holder instanceof FixedViewHolder) {
			return;
		}
		mAdapter.onViewRecycled(holder);
	}

	@Override
	public boolean onFailedToRecycleView(@NonNull BaseViewHolder holder) {
		if (holder instanceof FixedViewHolder) {
			return super.onFailedToRecycleView(holder);
		}
		return mAdapter.onFailedToRecycleView(holder);
	}

	@Override
	public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
		if (holder instanceof FixedViewHolder) {
			ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
			if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
				((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
			}
			return;
		}
		mAdapter.onViewAttachedToWindow(holder);
	}

	@Override
	public void onViewDetachedFromWindow(@NonNull BaseViewHolder holder) {
		if (holder instanceof FixedViewHolder) {
			return;
		}
		mAdapter.onViewDetachedFromWindow(holder);
	}

	@Override
	public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
		super.registerAdapterDataObserver(observer);
		mAdapter.registerAdapterDataObserver(observer);
	}

	@Override
	public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
		super.unregisterAdapterDataObserver(observer);
		mAdapter.unregisterAdapterDataObserver(observer);
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//		final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//		if (layoutManager instanceof GridLayoutManager) {
//			final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
//			gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//				@Override
//				public int getSpanSize(int position) {
//					if (isFixed(position)) {
//						return gridManager.getSpanCount();
//					}
//					return 1;
//				}
//			});
//		}
		mAdapter.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
		mAdapter.onDetachedFromRecyclerView(recyclerView);
	}

}
