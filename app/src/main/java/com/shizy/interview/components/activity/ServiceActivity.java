package com.shizy.interview.components.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.shizy.interview.IAidlServiceInterface;
import com.shizy.interview.R;
import com.shizy.interview.components.service.AidlService;
import com.shizy.interview.components.service.LocalService;
import com.shizy.interview.components.service.MessengerService;

import java.util.Random;

public class ServiceActivity extends BaseActivity {

	private static final String TAG = ServiceActivity.class.getSimpleName();

	private ServiceConnection mBinderConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "mBinderConn: " + service);
			LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
			mLocalService = binder.getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mLocalService = null;
		}
	};

	private ServiceConnection mMessengerConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "mMessengerConn: " + service);
			mMessenger = new Messenger(service);
			try {
				mMessenger.send(Message.obtain());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mMessenger = null;
		}
	};

	private ServiceConnection mAidlConn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d(TAG, "mAidlConn: " + service);
			mAidlService = IAidlServiceInterface.Stub.asInterface(service);
			try {
				mAidlService.basicTypes(1, 2, true, 4, 5, "6");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mAidlService = null;
		}
	};

	private LocalService mLocalService;
	private Messenger mMessenger;
	private IAidlServiceInterface mAidlService;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service);
	}

	public void onClick(View view) {
		Intent intent1 = new Intent(this, LocalService.class);
		Intent intent2 = new Intent(this, MessengerService.class);
		Intent intent3 = new Intent(this, AidlService.class);
		switch (view.getId()) {
			case R.id.btn_start:
				startService(intent1);
				startService(intent2);
				startService(intent3);
				break;
			case R.id.btn_stop:
				stopService(intent1);
				stopService(intent2);
				stopService(intent3);
				break;
			case R.id.btn_bind:
				bindService(intent1, mBinderConn, BIND_AUTO_CREATE);
				bindService(intent2, mMessengerConn, BIND_AUTO_CREATE);
				bindService(intent3, mAidlConn, BIND_AUTO_CREATE);
				break;
			case R.id.btn_unbind:
				if (mLocalService != null) {
					unbindService(mBinderConn);
				}
				if (mMessenger != null) {
					unbindService(mMessengerConn);
				}
				if (mAidlService != null) {
					unbindService(mAidlConn);
				}
				break;
		}
	}

}
