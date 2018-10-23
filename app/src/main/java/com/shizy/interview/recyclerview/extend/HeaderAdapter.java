package com.shizy.interview.recyclerview.extend;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.shizy.interview.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderAdapter extends BaseExtendAdapter {

	private final List<View> mHeaders = new ArrayList<>();
	private final List<View> mFooters = new ArrayList<>();

	public HeaderAdapter(RecyclerView.Adapter<BaseViewHolder> adapter) {
		super(adapter);
	}

	@Override
	public int getItemCount() {
		return mAdapter.getItemCount() + mHeaders.size() + mFooters.size();
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
		return mAdapter.onCreateViewHolder(parent, viewType);
	}

	@Override
	public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
		if (holder instanceof FixedViewHolder) {
			((FixedViewHolder) holder).onBind();
			return;
		}
		mAdapter.onBindViewHolder(holder, position - mHeaders.size());
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

}
