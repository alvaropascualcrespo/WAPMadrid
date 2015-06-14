package com.wapmadrid.activities;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

public class MainActivity extends Activity {
	
	private Button btnEntrar;
	private EditText edtUsuario;
	private EditText edtContrasena;
	private TextView txtRegistrate;
	RequestQueue requestQueue;
	JSONObject respuesta = null;

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
				login();
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
	
	private void login(){
		String url = Helper.getLoginUrl();
		requestQueue = Volley.newRequestQueue(getApplicationContext());
		final String usuario = edtUsuario.getText().toString();
		final String contrasena = edtContrasena.getText().toString();
		
		Response.Listener<String> succeedListener = new Response.Listener<String>(){
	        @Override
	        public void onResponse(String response) {
	            // response
	            Log.e("Response", response);
	            try {
		            respuesta = new JSONObject(response);
		            String error = respuesta.getString("error");
		            if (error.equals("0")){
		            	String id = respuesta.getString("_id");
		            	String token = respuesta.getString("token");
		            	if (!(token.equals("0"))){
							DataManager dm = new DataManager(getApplicationContext());
							dm.login(id,token);
							Intent i = new Intent(getApplicationContext(), InicioActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
							startActivity(i);
		            	}
		            	else{
							muestraError();
		            	}
		            }
					else{
						String error_message = respuesta.getString("error_message");
						Toast.makeText(getApplicationContext(),error_message, Toast.LENGTH_SHORT).show();
					}
	            } catch(JSONException e) {
					muestraError();
	            }
	        }
	    };
	    
	    Response.ErrorListener errorListener = new Response.ErrorListener(){
	         @Override
	         public void onErrorResponse(VolleyError error){
				 muestraError();
	         }
	    };	
	    
		StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener){     
		    @Override
		    protected Map<String, String> getParams(){  
		    	HashMap<String, String> params = new HashMap<String, String>();
				params.put("username", usuario);
				params.put("password", contrasena);
				return params;  
		    }
		};
				
		requestQueue.add(request);	
		
	}

	private void muestraError(){
		String error_message = getResources().getString(R.string.connection_error);
		Toast.makeText(getApplicationContext(),error_message,Toast.LENGTH_SHORT).show();
	}
}
