package com.shizy.interview.components.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class ExampleProvider extends ContentProvider {

	// Creates a UriMatcher object.
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		/*
		 * The calls to addURI() go here, for all of the content URI patterns that the provider
		 * should recognize. For this snippet, only the calls for table 3 are shown.
		 */

		/*
		 * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
		 * in the path
		 */
		sUriMatcher.addURI("com.shizy.interview.provider", "table3", 1);

		/*
		 * Sets the code for a single row to 2. In this case, the "#" wildcard is
		 * used. "content://com.example.app.provider/table3/3" matches, but
		 * "content://com.example.app.provider/table3 doesn't.
		 */
		sUriMatcher.addURI("com.shizy.interview.provider", "table3/#", 2);
	}

	@Override
	public boolean onCreate() {
		return false;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		switch (sUriMatcher.match(uri)) {
			// If the incoming URI was for all of table3
			case 1:
				if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
				break;
			// If the incoming URI was for a single row
			case 2:
				/*
				 * Because this URI was for a single row, the _ID value part is
				 * present. Get the last path segment from the URI; this is the _ID value.
				 * Then, append the value to the WHERE clause for the query
				 */
				selection = selection + "_ID = " + uri.getLastPathSegment();
				break;
			default:
				// If the URI is not recognized, you should do some error handling here.\
				break;
		}
		return null;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}
}
