package com.wapmadrid.miPerfil;

import java.util.ArrayList;

import com.google.android.gms.maps.SupportMapFragment;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItem;
import com.wapmadrid.data.Item;
import com.wapmadrid.modelos.Walker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MiPerfilActividadFragment extends Fragment{

	ProgressBar pgAmigoActividad;
	ListView lista;
	private Walker walker;

	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {

	        View v = inflater.inflate(R.layout.amigo_actividad_fragment, container, false);
	        pgAmigoActividad = (ProgressBar) v.findViewById(R.id.pgAmigoActividad);
	        lista = (ListView)(getActivity().findViewById(R.id.listaAmigoActividad));
	        return v;
	    }
	
	public void fill() {
		// TODO Auto-generated method stub
		
	}

	public void setWalker(Walker walker) {
		this.walker = walker;
	}
}
