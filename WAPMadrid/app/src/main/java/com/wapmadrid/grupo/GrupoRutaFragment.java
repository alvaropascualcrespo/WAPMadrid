package com.wapmadrid.grupo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.data.ItemRuta;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrupoRutaFragment extends Fragment implements OnMapReadyCallback {

	private SupportMapFragment fragmentMapa;
	private String routeID;
	private String routeDistance;
	private TextView tvName;
	private TextView tvDistance;
	private ItemRuta item;
	OnMapReadyCallback onMapReadyCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.activity_route, container, false);
		fragmentMapa = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapRutaActivity);
		tvName = (TextView) v.findViewById(R.id.tvName);
		tvDistance = (TextView) v.findViewById(R.id.tvDistance);

		routeID = getArguments().getString("ROUTE_ID");

		onMapReadyCallback = this;

		fill();
		return v;
}

	public void fill() {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
		final DataManager dm = new DataManager(getActivity().getApplicationContext());
		final String[] cred = dm.getCred();
		String url = Helper.getReadRouteUrl(cred[0]);


		Response.Listener<String> succeedListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// response
				Log.e("Response", response);
				try {
					JSONObject root = new JSONObject(response);
					String error = root.getString("error");
					if (error.equals("0")) {
						JSONObject ruta = root.getJSONObject("route");
						tvName.setText(ruta.getString("name"));
						routeDistance = ruta.getString("distance");
						tvDistance.setText(routeDistance + " km");
						item = new ItemRuta(ruta.getString("_id"),ruta.getString("name"),ruta.getJSONArray("coordinates"));
						fragmentMapa.getMapAsync(onMapReadyCallback);
					}
				} catch (Exception e) {
					String error_message = getResources().getString(R.string.connection_error);
					Toast.makeText(getActivity().getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
				}
			}
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				String error_message = getResources().getString(R.string.connection_error);
				Toast.makeText(getActivity().getApplicationContext(),error_message,Toast.LENGTH_SHORT).show();
			}
		};

		StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener) {
			@Override
			protected Map<String, String> getParams() {
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("token", cred[1]);
				params.put("routeID", routeID);
				return params;
			}
		};
		requestQueue.add(request);
	}





	@Override
	public void onMapReady(GoogleMap googleMap) {
		PolylineOptions polylineOptions = new PolylineOptions();
		polylineOptions.width(13);
		polylineOptions.color(getResources().getColor(R.color.orange_wap));
		polylineOptions.geodesic(true);
		Polyline poly = googleMap.addPolyline(polylineOptions);
		poly.setPoints(item.getPoints());
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(item.getPoints().get(1), 15));
		googleMap.getUiSettings().setZoomControlsEnabled(true);
		googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {

			}
		});
	}


	public String getRouteDistance() {
		return routeDistance;
	}
}
