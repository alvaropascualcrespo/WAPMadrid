package com.wapmadrid.activities;

import java.util.Timer;
import java.util.TimerTask;

import com.wapmadrid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

	private static final long DELAY = 2000;
    private Timer splashTimer;
    
    Activity act;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getActionBar().hide();
		act = this;
        splashTimer = new Timer();
        
        splashTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
            	Intent i = new Intent(SplashActivity.this, MainActivity.class);
            	startActivity(i);                
            }
         }, DELAY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
