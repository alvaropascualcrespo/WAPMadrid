package com.wapmadrid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.wapmadrid.R;

public class RegistroActivity extends Activity {

	private Button btnEnviar;
	private TextView txtLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		btnEnviar = (Button)findViewById(R.id.btnEnviarEstado);
		txtLogin = (TextView)findViewById(R.id.gotoInicio);
		
		//OnClicks
		
			btnEnviar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//TODO Recoger datos, comprobarlos, pasar datos a BD e iniciar nueva actividad
					Intent i = new Intent(RegistroActivity.this, InicioActivity.class);
	            	startActivity(i);
				}
			});
			
			txtLogin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(RegistroActivity.this, MainActivity.class);
	            	startActivity(i);
					
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
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
