package com.shizy.interview.components.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shizy.interview.IAidlServiceInterface;

public class AidlService extends Service {

	private static final String TAG = AidlService.class.getSimpleName();

	public class AidlBinder extends IAidlServiceInterface.Stub {
		@Override
		public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
			Log.d(TAG, "basicTypes");
		}
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind");
		return new AidlBinder();
	}

}
