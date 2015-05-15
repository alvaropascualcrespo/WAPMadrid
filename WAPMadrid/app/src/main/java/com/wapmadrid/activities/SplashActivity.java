package com.wapmadrid.activities;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.wapmadrid.R;
import com.wapmadrid.utilities.DataManager;

public class SplashActivity extends Activity {

	private static final long DELAY = 2000;
    private Timer splashTimer;
    
    Activity act;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		act = this;
        splashTimer = new Timer();
        
        final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
		splashImageView.setImageResource(R.drawable.logo_animado);
		
		final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getDrawable();
		
		
		splashImageView.post(new Runnable(){
	            @Override
	            public void run() {
	                frameAnimation.start();                
	            }            
	        });
        
        splashTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
				DataManager dm = new DataManager(getApplicationContext());
				if (dm.checkLogin()){
					String[] cred = dm.getCred();
					Intent i = new Intent(getApplicationContext(), InicioActivity.class);
					i.putExtra(InicioActivity.ID, cred[0]);
					i.putExtra(InicioActivity.TOKEN, cred[1]);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
				}else {
					Intent i = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(i);
				}
            }
         }, DELAY);
	}


}
