package com.wapmadrid.fragments;

import java.util.ArrayList;

import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.adapters.AdapterItemRutas;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.data.ItemRutasList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class AmigosListFragment extends Fragment{

	ArrayList<ItemAmigo> arraydir;
	AdapterItemAmigo adapter;
    private ProgressBar pgAmigosList;
    private ListView list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_amigos_list, container, false);
        list = (ListView) v.findViewById(R.id.listaAmigos);
        pgAmigosList = (ProgressBar) v.findViewById(R.id.pgAmigosList);
		list.setOnItemClickListener(rutasListClickListener());
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
