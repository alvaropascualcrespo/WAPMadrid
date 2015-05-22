package com.wapmadrid.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

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
import com.wapmadrid.data.Item;
import com.wapmadrid.data.ItemGrupo;
import com.wapmadrid.data.ItemRuta;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RouteActivity extends FragmentActivity  implements OnMapReadyCallback {

    private SupportMapFragment fragmentMapa;
    private TextView tvName;
    private String routeID;
    private ItemRuta item;
    OnMapReadyCallback onMapReadyCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        fragmentMapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapRutaActivity);
        tvName = (TextView) findViewById(R.id.tvName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        routeID = getIntent().getStringExtra("ROUTE_ID");
        onMapReadyCallback = this;

        fill();
      //  fragmentMapa.getMapAsync(this);
    }

    public void fill() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final DataManager dm = new DataManager(this);
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
                        item = new ItemRuta(ruta.getString("_id"),ruta.getString("name"),ruta.getJSONArray("coordinates"));
                        fragmentMapa.getMapAsync(onMapReadyCallback);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.e("Error.Response", error.toString());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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



}
