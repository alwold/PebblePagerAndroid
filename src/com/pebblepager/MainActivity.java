package com.pebblepager;

import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.getpebble.android.kit.PebbleKit;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PebbleKit.registerReceivedDataHandler(this, new PagerDataReceiver(UUID.fromString("389d91c5-f84c-4fc1-a1a0-baa8ea1b436f"), this));
		
		findViewById(R.id.ringtone_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(RingtoneManager.ACTION_RINGTONE_PICKER), 0);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("MainActivity", "picked "+data.getExtras().get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
