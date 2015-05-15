package com.wapmadrid.grupo;

import java.util.ArrayList;

import com.wapmadrid.R;
import com.wapmadrid.activities.AmigoActivity;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class GrupoMiembrosFragment extends Fragment{

	private GridView grid;
	private ProgressBar pgAmigosList;
	private ArrayList<ItemAmigo> arraydir;
	private AdapterItemAmigo adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_amigos_list, container, false);
        grid = (GridView) v.findViewById(R.id.gridAmigos);
        pgAmigosList = (ProgressBar) v.findViewById(R.id.pgAmigosList);
        
        arraydir = new ArrayList<ItemAmigo>();
        arraydir.add(new ItemAmigo("https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xpf1/v/t1.0-9/10606331_865211706830045_5889653945981354421_n.jpg?oh=4d6f5ae6de87ca02b2635ec1261f9de0&oe=556FD430&__gda__=1437775450_ef21e2d51765c16f6d1296c92866c843", "Alvaro", 1));
        arraydir.add(new ItemAmigo("https://scontent-mad.xx.fbcdn.net/hphotos-xfp1/v/l/t1.0-9/1507939_10152027009448208_1127720385_n.jpg?oh=7a817c52caf9b239d731ed41a63b93b5&oe=55B53A6B", "Yuli", 1));
        arraydir.add(new ItemAmigo("https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-xaf1/v/t1.0-9/1016554_10202866548402024_914065242_n.jpg?oh=766c0e283f4d2500159244643f568a64&oe=5572D01E&__gda__=1434315669_7ff7c2debd16b966f820c54a155d13cf", "Ismael", 1));
        
        adapter = new AdapterItemAmigo(getActivity(), arraydir);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(rutasListClickListener());
        return v;
    }
	
	private OnItemClickListener rutasListClickListener() {
		
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity().getApplicationContext(), AmigoActivity.class);
				startActivity(i);
			}
		};
	}

}
