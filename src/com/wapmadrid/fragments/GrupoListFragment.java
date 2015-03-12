package com.wapmadrid.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.wapmadrid.R;
import com.wapmadrid.activities.GrupoActivity;
import com.wapmadrid.adapters.AdapterItemGrupo;
import com.wapmadrid.data.ItemGrupo;

public class GrupoListFragment extends Fragment {

	ArrayList<ItemGrupo> arraydir;
	AdapterItemGrupo adapter;
    private ProgressBar pgAmigosList;
    private ListView list;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_grupo_list, container, false);// -- linea original
		
		arraydir = new ArrayList<ItemGrupo>();
		list = (ListView) view.findViewById(R.id.listaGrupos);
		arraydir.add(new ItemGrupo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "Lobos de Pensilvania", "Ruta Centro", 1));
        arraydir.add(new ItemGrupo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "Equipo A","Ruta Centro", 1));
        arraydir.add(new ItemGrupo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "Vallecanos","Ruta Vallevcas", 1));
        arraydir.add(new ItemGrupo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "SanSe","Ruta San Sebastián", 1));
		adapter = new AdapterItemGrupo(getActivity(), arraydir);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(getActivity().getApplicationContext(), GrupoActivity.class);
				startActivity(i);
				
			}
		});
		
		
		return view;
		
		
		}
		

		public void fill() {
			// TODO Auto-generated method stub
			
		}
}
