package com.wapmadrid.rutas;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import com.wapmadrid.activities.InicioActivity;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrearRutaActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private boolean iniciado = false;
    Button finalizarRuta;
    LocationListener locationListener;
    private SupportMapFragment fragmentMapa;
    private GoogleMap mapa;
    LocationManager locationManager;
    private boolean canGetLocation = false;
    private ArrayList<HashMap<String, Double>> puntosRuta;
    private ArrayList<LatLng> points;
    private HashMap<String, String> dataToSend;
    private ArrayList<JSONObject> coordinates;
    private EditText etNombreRuta;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitan_crear_ruta);
        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.action_bar_negro);
        fragmentMapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapNuevaRuta);
        etNombreRuta = (EditText) findViewById(R.id.etNombreRuta);
        fragmentMapa.getMapAsync(this);
        dataToSend = new HashMap<>();
        puntosRuta = new ArrayList<>();
        coordinates = new ArrayList<>();
        actionBar.setIcon(R.drawable.action_bar_negro);
        locationListener = this;

        points = new ArrayList<>();

        finalizarRuta = (Button) findViewById(R.id.btnTerminarRuta);
        if (!iniciado)
            finalizarRuta.setText("Iniciar Ruta");
        finalizarRuta.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!iniciado) {
                    finalizarRuta.setText("Finalizar Ruta");
                    updateLocation(mapa.getMyLocation());
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 50, locationListener);
                    iniciado = true;
                } else {
                    finalizarRuta.setText("Iniciar Ruta");
                    locationManager.removeUpdates(locationListener);
                    String nombreRuta = etNombreRuta.getText().toString();
                    if (!nombreRuta.equals("")) {
                        dataToSend.put("name", nombreRuta);
                        dataToSend.put("distance", String.valueOf(getDistance()));
                        fillCoordinates();
                        iniciado = false;
                    }
                }
            }
        });

    }

    public void fillCoordinates() {

        for (int i = 0; i < puntosRuta.size(); i++) {
            coordinates.add(new JSONObject(puntosRuta.get(i)));
        }
        JSONArray jarrayCoordinates = new JSONArray(coordinates);
        dataToSend.put("coordinates","{\"coordinates\": "+ jarrayCoordinates.toString() + "}");
        sendData();

    }

    private void sendData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DataManager dm = new DataManager(this);
        final String[] cred = dm.getCred();
        String url = Helper.getCreateRouteUrl(cred[0]);

        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject respuesta = new JSONObject(response);
                    String error = respuesta.getString("error");
                    if (error.equals("0")) {
                        Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
                    } else {
                        String error_message = respuesta.getString("error_message");
                        setErrorMsg(error_message);
                    }
                } catch (JSONException e) {
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                dataToSend.put("token", cred[1]);
                return dataToSend;
            }
        };

        requestQueue.add(request);

    }

    private void setErrorMsg(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(string)
                .setTitle("Error al crear la ruta")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        updateLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    protected void updateLocation(Location location) {
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(latitude, longitude), 15));

            HashMap<String, Double> aux = new HashMap<>();
            aux.put("_lat", latitude);
            aux.put("_long", longitude);
            puntosRuta.add(aux);
            points.add(new LatLng(latitude, longitude));
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(15);
            polylineOptions.color(getResources().getColor(R.color.orange_wap));
            polylineOptions.geodesic(true);
            Polyline poly = mapa.addPolyline(polylineOptions);
            poly.setPoints(points);
        } catch (Exception e) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Intent i = new Intent(this, InicioActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
        return true;
    }

    public Location getLocation() {
        Location location = null;
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                this.canGetLocation = true;

                // if GPS Enabled get lat/long using GPS Services

                if (isGPSEnabled) {
                  /*  locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 6000, 50, this);*/

                    Log.d("GPS Enabled", "GPS Enabled");

                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        updateGPSCoordinates(location);
                    }
                }

                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (location == null) {
                       /* locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                6000, 50, this);*/

                        Log.d("Network", "Network");

                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            updateGPSCoordinates(location);
                        }
                    }

                }
            }
        } catch (Exception e) {
            // e.printStackTrace();
            Log.e("Error : Location",
                    "Impossible to connect to LocationManager", e);
        }

        return location;
    }

    @Override
    public void onBackPressed() {
        locationManager.removeUpdates(locationListener);
        super.onBackPressed();
    }

    public void updateGPSCoordinates(Location location) {
        if (location != null) {
            location.getLatitude();
            location.getLongitude();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        Location location = null;
        mapa.setMyLocationEnabled(true);
        if (mapa.getMyLocation() == null)
            location = getLocation();
        else
            location = mapa.getMyLocation();
        if (location == null)
            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 100);
        else
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode){
            if (100 == requestCode){
                if (mapa.getMyLocation() != null){
                    Location location = null;
                    if (mapa.getMyLocation() == null)
                        location = getLocation();
                    else
                        location = mapa.getMyLocation();
                    mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
                }
            }
        }
    }

    public float getDistance() {
        float dist = 0;
        for (int i = 0; i < points.size() - 1; i++){
            Location l1=new Location("One");
            l1.setLatitude(points.get(i).latitude);
            l1.setLongitude(points.get(i).longitude);

            Location l2=new Location("Two");
            l2.setLatitude(points.get(i + 1).latitude);
            l2.setLongitude(points.get(i + 1).longitude);

            dist += l1.distanceTo(l2);
        }
        return dist;
    }
}
