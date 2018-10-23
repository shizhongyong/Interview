package com.shizy.interview.recyclerview.extend;

import android.support.annotation.IntDef;
import android.support.annotation.IntRange;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * int前3位用于标记类型，后29位代表位置
 */
public class ViewTypeSpec {

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
