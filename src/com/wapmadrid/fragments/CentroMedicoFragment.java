package com.wapmadrid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wapmadrid.R;

public class CentroMedicoFragment extends Fragment implements OnMapClickListener{

	private GoogleMap mapa;
	LatLng position;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_centro_medico, container, false);
        
        mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapCentroMedico)).getMap();
        
        return v;
    }
	
	public void fill() {
		// TODO Auto-generated method stub
		
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

}
