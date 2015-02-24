package com.wapmadrid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wapmadrid.R;

public class MainActivity extends Activity {
	
	private Button btnEntrar;
	private EditText edtUsuario;
	private EditText edtContrasena;
	private TextView txtRegistrate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnEntrar = (Button)findViewById(R.id.buttonEntrar);
		edtUsuario = (EditText)findViewById(R.id.editUsuario);
		edtContrasena = (EditText)findViewById(R.id.editContrasena);
		txtRegistrate = (TextView)findViewById(R.id.textRegistrate);
		
		//OnClicks
		
		btnEntrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//TODO Recoger datos, comprobarlos y pasar datos a nueva actividad
				Intent i = new Intent(MainActivity.this, InicioActivity.class);
            	startActivity(i);
			}
		});
		
		txtRegistrate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, RegistroActivity.class);
            	startActivity(i);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
