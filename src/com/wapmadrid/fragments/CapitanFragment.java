package com.wapmadrid.fragments;

import java.util.ArrayList;

import com.wapmadrid.R;
import com.wapmadrid.R.drawable;
import com.wapmadrid.activities.capitan.CederCapitaniaViewActivity;
import com.wapmadrid.activities.capitan.ComenzarRutaViewActivity;
import com.wapmadrid.activities.capitan.CrearRutaViewActivity;
import com.wapmadrid.activities.capitan.MensajesCapitanViewActivity;
import com.wapmadrid.adapters.AdapterItemLayoutCapitan;
import com.wapmadrid.data.ItemLayoutCapitan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class CapitanFragment extends Fragment{

	ArrayList<ItemLayoutCapitan> arraydir;
	AdapterItemLayoutCapitan adapter;
	GridView lista;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_capitan, container, false);// -- linea original
		setHasOptionsMenu(true);
		
		lista = (GridView) view.findViewById(R.id.gridViewCapitan);
        arraydir = new ArrayList<ItemLayoutCapitan>();       
	    adapter = new AdapterItemLayoutCapitan(getActivity(), arraydir);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				Intent intent;
				switch(position){
				case 0:
					intent = new Intent(getActivity(), MensajesCapitanViewActivity.class);
	                //intent.putExtra(MensajesCapitanViewActivity.ID, String.valueOf(adapter.getItemId(position)));
	                startActivity(intent);
				
				case 1:
					intent = new Intent(getActivity(), ComenzarRutaViewActivity.class);
	                //intent.putExtra(ComenzarRutaViewActivity.ID, String.valueOf(adapter.getItemId(position)));
	                startActivity(intent);
	                
				case 2:
					intent = new Intent(getActivity(), CrearRutaViewActivity.class);
	                //intent.putExtra(CrearRutaViewActivity.ID, String.valueOf(adapter.getItemId(position)));
	                startActivity(intent);
	                
				case 3:
					intent = new Intent(getActivity(), CederCapitaniaViewActivity.class);
	                //intent.putExtra(CederCapitaniaViewActivity.ID, String.valueOf(adapter.getItemId(position)));
	                startActivity(intent);
				}
			}			
		});
        fill();		
		return view;
	}

	private void fill() {
		ItemLayoutCapitan i1 = new ItemLayoutCapitan(R.drawable.ic_drawer,"Mensajes",0);
    	arraydir.add(i1);
    	ItemLayoutCapitan i2 = new ItemLayoutCapitan(R.drawable.ic_drawer,"Comenzar Ruta",1);
    	arraydir.add(i2);
    	ItemLayoutCapitan i3 = new ItemLayoutCapitan(R.drawable.ic_drawer,"Nueva Ruta",2);
    	arraydir.add(i3);
    	ItemLayoutCapitan i4 = new ItemLayoutCapitan(R.drawable.ic_drawer,"Ceder Capitania",3);
    	arraydir.add(i4);
    	adapter.notifyDataSetChanged();		
	}
	
}
