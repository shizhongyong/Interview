package com.shizy.interview.recyclerview;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderAndFooterAdapter extends RecyclerView.Adapter<BaseViewHolder> {

	private final List<View> mHeaders = new ArrayList<>();
	private final List<View> mFooters = new ArrayList<>();

	private final RecyclerView.Adapter<BaseViewHolder> mAdapter;

	public HeaderAndFooterAdapter(RecyclerView.Adapter<BaseViewHolder> adapter) {
		if (adapter == null) {
			throw new IllegalArgumentException("adapter can not be null!");
		}
		mAdapter = adapter;
	}

	@Override
	public int getItemViewType(int position) {
		final int headerCount = mHeaders.size();
		final int itemCount = mAdapter.getItemCount();
		if (position < headerCount) {
			return ViewTypeSpec.makeItemViewTypeSpec(position, ViewTypeSpec.HEADER);
		} else if (position >= headerCount + itemCount) {
			return ViewTypeSpec.makeItemViewTypeSpec(position - headerCount - itemCount, ViewTypeSpec.FOOTER);
		}
		return mAdapter.getItemViewType(position - headerCount);
	}

	@NonNull
	@Override
	public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		final int type = ViewTypeSpec.getType(viewType);
		final int value = ViewTypeSpec.getValue(viewType);
		if (type == ViewTypeSpec.HEADER) {
			return new FixedViewHolder(mHeaders.get(value));
		} else if (type == ViewTypeSpec.FOOTER) {
			return new FixedViewHolder(mFooters.get(value));
		}
		return mAdapter.onCreateViewHolder(parent, type);
	}

	@Override
	public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
		if (holder instanceof FixedViewHolder) {
			((FixedViewHolder) holder).onBind();
		} else {
			mAdapter.onBindViewHolder(holder, position - mHeaders.size());
		}
	}

	@Override
	public int getItemCount() {
		return mAdapter.getItemCount() + mHeaders.size() + mFooters.size();
	}

	@Override
	public void setHasStableIds(boolean hasStableIds) {
		super.setHasStableIds(hasStableIds);
		mAdapter.setHasStableIds(hasStableIds);
	}

	@Override
	public long getItemId(int position) {
		final int realPosition = position - mHeaders.size();
		if (realPosition >= 0 && realPosition < mAdapter.getItemCount()) {
			return mAdapter.getItemId(realPosition);
		}
		return super.getItemId(position);
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
		mAdapter.registerAdapterDataObserver(observer);
	}

	@Override
	public void unregisterAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
		mAdapter.unregisterAdapterDataObserver(observer);
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		mAdapter.onAttachedToRecyclerView(recyclerView);
	}

	@Override
	public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
		mAdapter.onDetachedFromRecyclerView(recyclerView);
	}

	public void addHeaderView(View... views) {
		if (views == null || views.length == 0) {
			return;
		}
		for (View view : views) {
			mHeaders.add(0, view);
		}
		notifyDataSetChanged();
	}

	public void removeHeaderView(View... views) {
		if (views == null || views.length == 0) {
			return;
		}
		for (View view : views) {
			mHeaders.remove(view);
		}
		notifyDataSetChanged();
	}

	public void addFooterView(View... views) {
		if (views == null || views.length == 0) {
			return;
		}
		mFooters.addAll(Arrays.asList(views));
		notifyDataSetChanged();
	}

	public void removeFooterView(View... views) {
		if (views == null || views.length == 0) {
			return;
		}
		for (View view : views) {
			mFooters.remove(view);
		}
		notifyDataSetChanged();
	}

	private static class FixedViewHolder extends BaseViewHolder {
		private FixedViewHolder(View itemView) {
			super(itemView);
			setIsRecyclable(false);
		}

		private void onBind() {
		}
	}

	private static class ViewTypeSpec {

		private static final int TYPE_SHIFT = 29;
		private static final int TYPE_MASK = 0x7 << TYPE_SHIFT;

		static final int UNSPECIFIED = 0 << TYPE_SHIFT;
		static final int HEADER = 1 << TYPE_SHIFT;
		static final int EMPTY = 2 << TYPE_SHIFT;
		static final int FOOTER = 3 << TYPE_SHIFT;
		static final int LOADMORE = 4 << TYPE_SHIFT;

		@IntDef({UNSPECIFIED, HEADER, FOOTER, EMPTY, LOADMORE})
		@Retention(RetentionPolicy.SOURCE)
		public @interface ViewTypeSpecMode {
		}

		public static int makeItemViewTypeSpec(@IntRange(from = 0, to = (1 << TYPE_SHIFT) - 1) int value,
											   @ViewTypeSpec.ViewTypeSpecMode int type) {
			return (value & ~TYPE_MASK) | (type & TYPE_MASK);
		}

		@ViewTypeSpec.ViewTypeSpecMode
		public static int getType(int viewType) {
			return (viewType & TYPE_MASK);
		}

		public static int getValue(int viewType) {
			return (viewType & ~TYPE_MASK);
		}
	}

}
