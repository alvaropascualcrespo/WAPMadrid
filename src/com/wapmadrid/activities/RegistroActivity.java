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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

public class RegistroActivity extends Activity {

	private Button btnEnviarEstado;
	private TextView gotoInicio;
	private ProgressBar pgRegistro;
	private EditText etRegistroNombre;
	private EditText etRegistroApellidos;
	private EditText etRegistroFechaNacimiento;
	private RadioButton rdMujer;
	private RadioButton rdHombre;
	private EditText etUsuario;
	private EditText etRegistroContrasena;
	private EditText etRegistroContrasena2;
	
	private String nombre;
	private String apellidos;
	private String fechaNacimiento;
	private String usuario;
	private String contrasena1;
	private String contrasena2;
	private boolean genero;
	
	private RequestQueue requestQueue;
    private JSONObject respuesta = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		btnEnviarEstado = (Button)findViewById(R.id.btnEnviarEstado);
		gotoInicio = (TextView)findViewById(R.id.gotoInicio);
		pgRegistro = (ProgressBar)findViewById(R.id.pgRegistro);
		etRegistroNombre = (EditText)findViewById(R.id.etRegistroNombre);
		etRegistroApellidos = (EditText)findViewById(R.id.etRegistroApellidos);
		etRegistroFechaNacimiento = (EditText)findViewById(R.id.etRegistroFechaNacimiento);
		etUsuario = (EditText)findViewById(R.id.etUsuario);
		etRegistroContrasena = (EditText)findViewById(R.id.etRegistroContrasena);
		etRegistroContrasena2 = (EditText)findViewById(R.id.etRegistroContrasena2);
		rdMujer = (RadioButton)findViewById(R.id.rdMujer);
		rdHombre = (RadioButton)findViewById(R.id.rdHombre);
		
		//OnClicks
		
		btnEnviarEstado.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					pgRegistro.setVisibility(View.VISIBLE);
					btnEnviarEstado.setEnabled(false);
					nombre = etRegistroNombre.getText().toString();
					apellidos = etRegistroApellidos.getText().toString();
					fechaNacimiento = etRegistroFechaNacimiento.getText().toString();
					usuario = etUsuario.getText().toString();
					contrasena1 = etRegistroContrasena.getText().toString();
					contrasena2 = etRegistroContrasena2.getText().toString();
					if (rdMujer.isChecked()){
						genero = false;
					}else if(rdHombre.isChecked()){
						genero = true;
					}
					
					if (checkFields()){
						//DataManager dm = new DataManager(getApplicationContext());
						//dm.setUser(usuario,"",nombre, apellidos, fechaNacimiento, genero);
						registerCall();
					}else{
						
					}
				}
			});
			
		gotoInicio.setOnClickListener(new OnClickListener() {
				
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
	
private boolean checkFields() {
		
		if(nombre.isEmpty()){
			setErrorMsg("Rellena tu nombre por favor");
			return false;
		}else if(apellidos.isEmpty()){
			setErrorMsg("Rellena tus apellidos por favor");
			return false;	
		}else if(fechaNacimiento.isEmpty()){
			setErrorMsg("Rellena tu fecha de nacimiento por favor");
			return false;
		}//else if(genero.isEmpty()){
			//setErrorMsg("Indica tu sexo por favor");
			//return false;
		//}
		else if(usuario.isEmpty()){
			setErrorMsg("Rellena tu usuario por favor");
			return false;
		}else if(contrasena1.isEmpty()){
			setErrorMsg("Introduce una contraseña por favor");
			return false;
		}else if(contrasena2.isEmpty()){
			setErrorMsg("Repite la contraseña por favor");
			return false;
		}else if(contrasena1.length() < 6){
			setErrorMsg("Por favor introduce una contraseña de al menos 6 letras y/o números.");
			return false;
		}else if(!contrasena1.equals(contrasena2)){
			setErrorMsg("Las contraseñas no coinciden.");
			return false;
		} 
		return true;
	}
	
	
	private void registerCall() {
		
		//final DataManager dm = new DataManager(getApplicationContext());
		
		requestQueue = Volley.newRequestQueue(getApplicationContext()); 
		String url = Helper.getRegistroUrl();
		
		Response.Listener<String> succeedListener = new Response.Listener<String>() 
	    {
	        @Override
	        public void onResponse(String response) {
	            // response
	        	Log.e("Response", response);
	            try {
	            	pgRegistro.setVisibility(View.GONE);
	            	btnEnviarEstado.setEnabled(true);
		            respuesta = new JSONObject(response);
					String token = respuesta.getString("token");
					String idProfile = respuesta.getString("_id");
					String error = respuesta.getString("error");
					if ((error.equals("0")) || (!(token.equals("0")))){
						//dm.login(usuario,idProfile,token,"-1");
						Intent i = new Intent(RegistroActivity.this, InicioActivity.class);
						i.putExtra(InicioActivity.USUARIO, usuario);
						i.putExtra(InicioActivity.ID, idProfile);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(i);
					}else{
						String error_message = respuesta.getString("error_message");
						setErrorMsg(error_message);
					}
	            } catch(JSONException e) {}
	        }
	    };
	    Response.ErrorListener errorListener = new Response.ErrorListener() 
	    {
	         @Override
	         public void onErrorResponse(VolleyError error) {
	             // error
	        	 pgRegistro.setVisibility(View.GONE);
	        	 btnEnviarEstado.setEnabled(true);
	             Log.e("Error.Response", error.toString());
	       }
	    };
		
		StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener) 
		{     
			    @Override
			    protected Map<String, String> getParams() 
			    {  
			    	HashMap<String, String> params = new HashMap<String, String>();
					params.put("username", usuario);
					params.put("password", contrasena1);
					params.put("firstname", nombre);
					params.put("lastname", apellidos);
					params.put("birthDate", fechaNacimiento);
					params.put("sex", new Boolean(genero).toString());
					return params;
			    }
		};
		
		requestQueue.add(request);
		
	}
	
	private void setErrorMsg(String string){
		pgRegistro.setVisibility(View.GONE);
		btnEnviarEstado.setEnabled(true);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(string)
		        .setTitle("Error en el registro")
		        .setCancelable(false)
		        .setNeutralButton("Aceptar",
		                new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int id) {
		                        dialog.cancel();
		                    }
		                });
		AlertDialog alert = builder.create();
		alert.show();
		//register_error.setText(string);
	}
	
}
