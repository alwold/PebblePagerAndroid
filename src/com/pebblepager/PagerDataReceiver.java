package com.pebblepager;

import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.util.Log;

import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;
import com.getpebble.android.kit.util.PebbleDictionary;

public class PagerDataReceiver extends PebbleDataReceiver {
	private Activity activity;
	private Ringtone ringtone;

	protected PagerDataReceiver(UUID subscribedUuid, Activity activity) {
		super(subscribedUuid);
		this.activity = activity;
	}

	@Override
	public void receiveData(Context context, int transactionId,
			PebbleDictionary data) {
		Log.d("PagerDataReceiver", "Got some data");
		if (ringtone == null) {
			ringtone = RingtoneManager.getRingtone(activity, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
		}
		if (ringtone.isPlaying()) {
			ringtone.stop();
		} else {
			ringtone.play();
		}
	}

}
