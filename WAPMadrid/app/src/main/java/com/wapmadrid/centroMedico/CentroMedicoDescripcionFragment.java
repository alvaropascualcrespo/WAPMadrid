package com.wapmadrid.centroMedico;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wapmadrid.activities.CMSListActivity;
import com.wapmadrid.R;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

public class CentroMedicoDescripcionFragment extends Fragment implements OnMapClickListener, OnMapReadyCallback {

	LatLng position;
	private ProgressBar pgCMDescripcion;
	
	private NetworkImageView imageSanidad;
	private TextView textPoblacion;
	private TextView textCP;
	private GoogleMap mapa;
	private TextView textNombreCentro;
	private TextView textDireccionCentro;
	private TextView textHorario;
	private TextView textTelefono;
	
	private RequestQueue requestQueue;
    private JSONObject respuesta = null;
    private ImageLoader imageLoader;
	private SupportMapFragment fragmentMapa;
	private TextView textEmail;
	private String direccion;
	private String nombre;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_centro_medico_descripcion, container, false);
        pgCMDescripcion = (ProgressBar) v.findViewById(R.id.pgCMDescripcion);
        textNombreCentro = (TextView)v.findViewById(R.id.textNombreCentro);
        textDireccionCentro = (TextView)v.findViewById(R.id.textDireccionCentro);
        textHorario = (TextView)v.findViewById(R.id.textHorario);
        textTelefono = (TextView)v.findViewById(R.id.textTelefono);
		textEmail = (TextView)v.findViewById(R.id.textEmail);
        imageSanidad = (NetworkImageView)v.findViewById(R.id.imageSanidad);

		direccion = "";
		nombre = "";

		fragmentMapa = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapCMS);
		fragmentMapa.getMapAsync(this);

        return v;
    }
	
	public void fill() {
		final DataManager dm = new DataManager(getActivity().getApplicationContext());
		String[] cred = dm.getCred();
		pgCMDescripcion.setVisibility(View.VISIBLE);
		requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext()); 
		String url = Helper.getCMSUrl(cred[0]);
		final String token = cred[1];
		
		Response.Listener<String> succeedListener = new Response.Listener<String>() 
	    {
	        @Override
	        public void onResponse(String response) {
	            // response
	        	Log.e("Response", response);
	            try {
	            	pgCMDescripcion.setVisibility(View.GONE);
		            respuesta = new JSONObject(response);
		            String error = respuesta.getString("error");

		            if (error.equals("0")) {
						JSONArray cmsArray = respuesta.getJSONArray("cms");
						if (cmsArray.length() != 0) {
							JSONObject cms = cmsArray.getJSONObject(0);
							String imagen = cms.getString("image");
							nombre = cms.getString("name");
							direccion = cms.getString("address");
							String telefono = cms.getString("telephone");
							String horario = cms.getString("openingHours");
							String email = cms.getString("email");


							textNombreCentro.setText(nombre);
							textHorario.setText(horario);
							textTelefono.setText(telefono);
							textEmail.setText(email);

							Linkify.addLinks(textTelefono, Linkify.ALL);
							Linkify.addLinks(textEmail, Linkify.ALL);

							RequestQueue requestQueueImagen = Volley.newRequestQueue(getActivity().getApplicationContext());
							imageLoader = new ImageLoader(requestQueueImagen, new BitmapLRUCache());
							imageSanidad.setImageUrl(imagen, imageLoader);

							if (mapa != null){
								Geocoder coder = new Geocoder(getActivity());
								List<Address> address;
								address = coder.getFromLocationName(direccion,5);
								Address location = address.get(0);
								mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
										new LatLng(location.getLatitude(), location.getLongitude()), 15));
								mapa.addMarker(new MarkerOptions()
										.position(new LatLng(location.getLatitude(), location.getLongitude()))
										.title(nombre)
										.snippet(direccion));
							}
						} else {
							Intent i = new Intent(getActivity().getApplicationContext(), CMSListActivity.class);
							getActivity().startActivityForResult(i, Constants.RESULT_SELECTED_CMS);
						}

		            }
		            else{
						String error_message = respuesta.getString("error_message");
						setErrorMsg(error_message);
					}
	            } catch(JSONException e) {} catch (IOException e) {
					e.printStackTrace();
				}
			}
	    };
	    Response.ErrorListener errorListener = new Response.ErrorListener() 
	    {
	         @Override
	         public void onErrorResponse(VolleyError error) {
	             // error
	        	 pgCMDescripcion.setVisibility(View.GONE);
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
	
	public void moveCamera(View view) {
        mapa.moveCamera(CameraUpdateFactory.newLatLng(position));
	}

	public void animateCamera(View view) {
		if (mapa.getMyLocation() != null)
			mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng( mapa.getMyLocation().getLatitude(), mapa.getMyLocation().getLongitude()), 15));
	}
	
	public void addMarker(View view) {
	   mapa.addMarker(new MarkerOptions().position(
	        new LatLng(mapa.getCameraPosition().target.latitude,
	   mapa.getCameraPosition().target.longitude)));
	}
	
	@Override
	public void onMapClick(LatLng puntoPulsado) {
	   mapa.addMarker(new MarkerOptions().position(puntoPulsado).
	      icon(BitmapDescriptorFactory
	         .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
	}

	public void setTabs() {
		// TODO Auto-generated method stub
		
	}
	
	private void setErrorMsg(String string){
		pgCMDescripcion.setVisibility(View.GONE);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(string)
		        .setTitle("Error cargando CMS")
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

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mapa = googleMap;
		Geocoder coder = new Geocoder(getActivity());
		List<Address> address;

		try {

			if (!direccion.equals(""))
				address = coder.getFromLocationName(direccion,5);
			else
				return;
			Address location = address.get(0);
			mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(location.getLatitude(), location.getLongitude()), 15));
			Marker marker = mapa.addMarker(new MarkerOptions()
					.position(new LatLng(location.getLatitude(), location.getLongitude()))
					.title(nombre)
					.snippet(direccion));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
