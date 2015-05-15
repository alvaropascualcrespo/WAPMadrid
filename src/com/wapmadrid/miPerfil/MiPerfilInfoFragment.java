package com.wapmadrid.miPerfil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MiPerfilInfoFragment extends Fragment{

    private ProgressBar pgAmigoView;
    private RequestQueue requestQueue;
	private TextView txtCiudad;
	private TextView txtSobreMi;
	private TextView txtNombreyApellidos;
	private Button btnMessage;
	private NetworkImageView imgAmigo;
	private ImageLoader imageLoader;
	private Button btnAceptar;
	private Button btnRechazar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.amigo_info_fragment, container, false);
        pgAmigoView = (ProgressBar)v.findViewById(R.id.pgAmigoView);
        txtCiudad = (TextView)v.findViewById(R.id.txtCiudad);
        txtSobreMi = (TextView)v.findViewById(R.id.txtSobreMi);
        txtNombreyApellidos = (TextView)v.findViewById(R.id.txtNombreyApellidos);
        btnMessage = (Button)v.findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        btnAceptar = (Button)v.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        btnRechazar = (Button)v.findViewById(R.id.btnRechazar);
        btnRechazar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        imgAmigo = (NetworkImageView)v.findViewById(R.id.imgAmigo);
        return v;
    }
	
	public void fill() {
		final DataManager dm = new DataManager(getActivity().getApplicationContext());
		String[] cred = dm.getCred();
		
		pgAmigoView.setVisibility(View.VISIBLE);
		requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
		String url = Helper.getReadUrl(cred[0]);
		final String token = cred[1];
		
		
		Response.Listener<String> succeedListener = new Response.Listener<String>() 
	    {
	        @Override
	        public void onResponse(String response) {
	            // response
	        	Log.e("Response", response);
	            try {
	            	JSONObject root = new JSONObject(response);
	            	String error = root.getString("error");
	            	if (error.equals("0")){
	            		RequestQueue requestQueueImagen = Volley.newRequestQueue(getActivity().getApplicationContext());
		                imageLoader = new ImageLoader(requestQueueImagen, new BitmapLRUCache());
		            	imgAmigo.setImageUrl(root.getString("profileImage"), imageLoader);
		            	txtCiudad.setText(root.getString("city"));	
		            	txtSobreMi.setText(root.getString("about"));
		            	String nombre = root.getString("firstName");
		            	String apellidos = root.getString("lastName");
		            	txtNombreyApellidos.setText(nombre + " " + apellidos);
	            	}
	            	else {
	            		//TODO
	            	}
	            	pgAmigoView.setVisibility(View.GONE);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	    };
	    Response.ErrorListener errorListener = new Response.ErrorListener() 
	    {
	         @Override
	         public void onErrorResponse(VolleyError error) {
	             // error
	             Log.e("Error.Response", error.toString());
	       }
	    };
		
	    StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener) 
		{     
			    @Override
			    protected Map<String, String> getParams() 
			    {  
			    	HashMap<String, String> params = new HashMap<String, String>();
					params.put("token", token);
					return params;
			    }
		}; 
		
		requestQueue.add(request);
		
	}

}
