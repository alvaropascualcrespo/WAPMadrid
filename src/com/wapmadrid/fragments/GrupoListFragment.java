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
        arraydir.add(new ItemGrupo("https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/10606331_865211706830045_5889653945981354421_n.jpg?oh=4d6f5ae6de87ca02b2635ec1261f9de0&oe=556FD430&__gda__=1437775450_ef21e2d51765c16f6d1296c92866c843", "Equipo A","Ruta Centro", 1));
        arraydir.add(new ItemGrupo("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/1016554_10202866548402024_914065242_n.jpg?oh=766c0e283f4d2500159244643f568a64&oe=5572D01E&__gda__=1434315669_7ff7c2debd16b966f820c54a155d13cf", "Vallecanos","Ruta Vallevcas", 1));
        arraydir.add(new ItemGrupo("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/1016554_10202866548402024_914065242_n.jpg?oh=766c0e283f4d2500159244643f568a64&oe=5572D01E&__gda__=1434315669_7ff7c2debd16b966f820c54a155d13cf", "SanSe","Ruta San Sebastián", 1));
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
