package com.wapmadrid.grupo;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.wapmadrid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class GrupoCaracteristicasFragment extends Fragment{

	private GoogleMap mapa;
	LatLng position;
	private ProgressBar pgCMDescripcion;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_grupo_caracterisitcas, container, false);
        pgCMDescripcion = (ProgressBar) v.findViewById(R.id.pgCMDescripcion);
        try{
        	mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapCentroMedico)).getMap();
        } catch(Exception e){}
        return v;
    }

}
