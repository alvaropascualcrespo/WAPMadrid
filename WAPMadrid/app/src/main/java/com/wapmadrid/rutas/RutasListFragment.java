package com.wapmadrid.rutas;

import java.util.ArrayList;

import com.wapmadrid.R;
import com.wapmadrid.activities.NewGroupActivity;
import com.wapmadrid.adapters.AdapterItemRutas;
import com.wapmadrid.data.ItemRutasList;
import com.wapmadrid.utilities.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RutasListFragment extends Fragment {

	ArrayList<ItemRutasList> arraydir;
	AdapterItemRutas adapter;
    private ProgressBar pgRutasList;
    private ListView list;
	private TextView tvNewRoute;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rutas_list, container, false);
        list = (ListView) v.findViewById(R.id.listaRutas);
		pgRutasList = (ProgressBar) v.findViewById(R.id.pgRutasList);
		list.setOnItemClickListener(rutasListClickListener());
		tvNewRoute = (TextView) v.findViewById(R.id.tvNewRoute);
		tvNewRoute.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity().getApplicationContext(), CrearRutaViewActivity.class);
				startActivity(i);
			}
		});

        return v;
    }
	
	private OnItemClickListener rutasListClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

	public void fill() {
		// TODO Auto-generated method stub
		
	}

}
