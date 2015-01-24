package com.wapmadrid.fragments;

import java.util.ArrayList;

import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemRutas;
import com.wapmadrid.data.ItemRutasList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class RutasListFragment extends Fragment {

	ArrayList<ItemRutasList> arraydir;
	AdapterItemRutas adapter;
    private ProgressBar pgLocalsList;
    private ListView list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rutas_list, container, false);
        list = (ListView) v.findViewById(R.id.listaRutas);
		pgLocalsList = (ProgressBar) v.findViewById(R.id.pgRutasList);
        return v;
    }
	
	public void fill() {
		// TODO Auto-generated method stub
		
	}

}
