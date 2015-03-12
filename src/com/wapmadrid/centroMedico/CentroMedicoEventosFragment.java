package com.wapmadrid.centroMedico;

import com.google.android.gms.maps.SupportMapFragment;
import com.wapmadrid.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class CentroMedicoEventosFragment extends Fragment{

	ListView lista;
	ProgressBar pgCMEventos;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_centro_medico_eventos, container, false);
        lista = (ListView)(getActivity().findViewById(R.id.listaCentroMedicoEventos));
        pgCMEventos = (ProgressBar) v.findViewById(R.id.pgCMEventos);
        return v;
    }
	
	public void fill() {
		// TODO Auto-generated method stub
		
	}

}
