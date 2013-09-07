package com.pebblepager;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.getpebble.android.kit.PebbleKit;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PebbleKit.registerReceivedDataHandler(this, new PagerDataReceiver(UUID.fromString("389d91c5-f84c-4fc1-a1a0-baa8ea1b436f"), this));
		String ringtoneString = getSharedPreferences("PebblePager",  MODE_PRIVATE).getString("ringtone", null);
		Ringtone ringtone = null;
		if (ringtoneString != null) {
			Uri uri = Uri.parse(ringtoneString);
			ringtone = RingtoneManager.getRingtone(this, uri);
		}
		if (ringtone != null) {
			((TextView) findViewById(R.id.ringtone_label)).setText(ringtone.getTitle(this));
		}
		findViewById(R.id.ringtone_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				startActivityForResult(intent, 0);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri uri = (Uri) data.getExtras().get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
		Editor e = getSharedPreferences("PebblePager", MODE_PRIVATE).edit();
		e.putString("ringtone", uri.toString());
		e.commit();
		Ringtone ringtone = RingtoneManager.getRingtone(this, uri);
		((TextView) findViewById(R.id.ringtone_label)).setText(ringtone.getTitle(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
