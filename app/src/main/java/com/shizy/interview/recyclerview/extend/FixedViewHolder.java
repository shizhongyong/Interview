package com.shizy.interview.recyclerview.extend;

import android.view.View;

import com.shizy.interview.recyclerview.BaseViewHolder;


public class FixedViewHolder extends BaseViewHolder {

	public FixedViewHolder(View itemView) {
		super(itemView);
		setIsRecyclable(false);
	}

	protected void onBind() {

	}

}
