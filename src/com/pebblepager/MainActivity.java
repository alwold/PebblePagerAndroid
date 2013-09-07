package com.pebblepager;

import java.util.UUID;

import com.getpebble.android.kit.PebbleKit;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		PebbleKit.registerReceivedDataHandler(this, new PagerDataReceiver(UUID.fromString("389d91c5-f84c-4fc1-a1a0-baa8ea1b436f"), this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
