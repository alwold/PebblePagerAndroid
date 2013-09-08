package com.pebblepager;

import java.util.UUID;

import android.content.Context;
import android.media.Ringtone;
import android.util.Log;

import com.getpebble.android.kit.PebbleKit.PebbleDataReceiver;
import com.getpebble.android.kit.util.PebbleDictionary;

public class PagerDataReceiver extends PebbleDataReceiver {
	private Ringtone ringtone;

	protected PagerDataReceiver(UUID subscribedUuid, Ringtone ringtone) {
		super(subscribedUuid);
		this.ringtone = ringtone;
	}

	@Override
	public void receiveData(Context context, int transactionId,
			PebbleDictionary data) {
		Log.d("PagerDataReceiver", "Got some data");
		if (ringtone.isPlaying()) {
			ringtone.stop();
		} else {
			ringtone.play();
		}
	}
	
	public void setRingtone(Ringtone ringtone) {
		this.ringtone = ringtone;
	}
}
