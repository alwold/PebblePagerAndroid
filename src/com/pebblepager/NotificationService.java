package com.pebblepager;

import java.util.UUID;

import com.getpebble.android.kit.PebbleKit;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class NotificationService extends Service {
	private PagerDataReceiver receiver;
	private Ringtone ringtone;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		ringtone = RingtoneManager.getRingtone(this, (Uri) intent.getExtras().get("ringtone"));
		Log.d("NotificationService", "started");
		receiver = new PagerDataReceiver(UUID.fromString("389d91c5-f84c-4fc1-a1a0-baa8ea1b436f"), ringtone);
		PebbleKit.registerReceivedDataHandler(this, receiver);
		
		return START_REDELIVER_INTENT;
	}

}
