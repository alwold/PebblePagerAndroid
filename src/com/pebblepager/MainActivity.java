package com.pebblepager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String PEBBLE_LAUNCH_COMPONENT = "com.getpebble.android";
	private static final String PEBBLE_LAUNCH_ACTIVITY = "com.getpebble.android.ui.UpdateActivity";
	private Uri ringtoneUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String ringtoneString = getSharedPreferences("PebblePager",  MODE_PRIVATE).getString("ringtone", null);
		Ringtone ringtone;
		if (ringtoneString != null) {
			ringtoneUri = Uri.parse(ringtoneString);
			ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
		} else {
			ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
			ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
		}
		Intent intent = new Intent(this, NotificationService.class);
		intent.putExtra("ringtone", ringtoneUri);
		startService(intent);
		((TextView) findViewById(R.id.ringtone_button)).setText("Change Ringtone: " + ringtone.getTitle(this));
		findViewById(R.id.ringtone_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, ringtoneUri);
				startActivityForResult(intent, 0);
			}
		});
		findViewById(R.id.install_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				installApp();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			ringtoneUri = (Uri) data.getExtras().get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
			Editor e = getSharedPreferences("PebblePager", MODE_PRIVATE).edit();
			e.putString("ringtone", ringtoneUri.toString());
			e.commit();
			Ringtone ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
			Intent intent = new Intent(this, NotificationService.class);
			intent.putExtra("ringtone", ringtoneUri);
			startService(intent);
			((TextView) findViewById(R.id.ringtone_button)).setText("Change Ringtone: "+ringtone.getTitle(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void installApp() {
		 Log.d("MainActivity", "copy_watchface_from_assets()");
		    InputStream input = null;
		    OutputStream output = null;
		    try {
		        input = getAssets().open("pebble_pager.pbw");
		        File dest = new File(getExternalCacheDir(), "pebble_pager.pbw");
		        // delete existing file
		        dest.delete();
		        output = new FileOutputStream(dest);
		        Log.d("MainActivity", "storing pbw: " + dest);
		        
		        // copy asset to file
		        byte[] buffer = new byte[2056];
		        int length;
		        while ((length = input.read(buffer))>0){
		            output.write(buffer, 0, length);
		        }
		        output.flush();
		        
		        // launch pebble update activity
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setData(Uri.fromFile(dest));
		        intent.setClassName(PEBBLE_LAUNCH_COMPONENT, PEBBLE_LAUNCH_ACTIVITY);
		        Log.d("MainActivity", "launch pebble to load pbw");
		        try {
		            startActivity(intent);
		        } catch (ActivityNotFoundException e) {
			    	Log.e("MainActivity", "Exception", e);
		        }
		    } catch (IOException e) {
		    	Log.e("MainActivity", "Exception", e);
		    } finally {
		        if (input != null) {
		            try {
		                input.close();
		            } catch (IOException e) {
				    	Log.e("MainActivity", "Exception", e);
		            }
		        }
		        if (output != null) {
		            try {
		                output.close();
		            } catch (IOException e) {
				    	Log.e("MainActivity", "Exception", e);
		            }
		        }
		    }
	}

}
