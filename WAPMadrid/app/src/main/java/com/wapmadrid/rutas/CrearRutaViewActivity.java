package com.wapmadrid.rutas;

import android.app.ActionBar;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wapmadrid.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CrearRutaViewActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
    private boolean iniciado = false;
    Button finalizarRuta;
    LocationListener locationListener;
    private SupportMapFragment fragmentMapa;
    private GoogleMap mapa;
    LocationManager locationManager;
    private boolean canGetLocation = false;
    private ArrayList<HashMap<String,Double>> puntosRuta;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitan_crear_ruta);
        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        fragmentMapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapNuevaRuta);
        fragmentMapa.getMapAsync(this);

        puntosRuta = new ArrayList<>();

        actionBar.setIcon(R.drawable.action_bar_negro);
        locationListener = this;

        finalizarRuta = (Button) findViewById(R.id.btnTerminarRuta);
        if (!iniciado)
            finalizarRuta.setText("Iniciar Ruta");
        finalizarRuta.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Mandar Datos al Servidor
                if (!iniciado) {
                    finalizarRuta.setText("Iniciar Ruta");
                    updateLocation(mapa.getMyLocation());
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 50, locationListener);
                } else {
                    finalizarRuta.setText("Finalizar Ruta");
                }
            }
        });

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

            HashMap<String,Double> aux = new HashMap<>();
            aux.put("latitude", latitude);
            aux.put("longitude", longitude);
            puntosRuta.add(aux);
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.geodesic(true);
            for (int i = 0; i < puntosRuta.size(); i++){
                aux = puntosRuta.get(i);
                polylineOptions.add(new LatLng(aux.get("latitude"),aux.get("longitude")));
            }
            mapa.addPolyline(polylineOptions);

            String msg = "Latitude: " + latitude + " --- Longitude: " + longitude;
            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        }
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
                // location service disabled
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
        if (mapa.getMyLocation() == null )
            location = getLocation();
        else
            location = mapa.getMyLocation();
        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude()), 15));

    }
}
