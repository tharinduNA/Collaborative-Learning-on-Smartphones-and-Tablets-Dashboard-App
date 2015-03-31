package com.android.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServerService extends Service {
	
	private String TAG = "ServerService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//MultiThreadedServer server = new MultiThreadedServer();
		MultiThreads server = new MultiThreads();
		new Thread(server).start();
		Log.i(TAG , "sss");
		return START_STICKY;
	}

}
