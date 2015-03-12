package com.wapmadrid.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;

public class AmigosListFragment extends Fragment{

	ArrayList<ItemAmigo> arraydir;
	AdapterItemAmigo adapter;
    private ProgressBar pgAmigosList;
    private GridView grid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_amigos_list, container, false);
        grid = (GridView) v.findViewById(R.id.gridAmigos);
        pgAmigosList = (ProgressBar) v.findViewById(R.id.pgAmigosList);
        
        arraydir = new ArrayList<ItemAmigo>();
        arraydir.add(new ItemAmigo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "Alvaro", 1));
        arraydir.add(new ItemAmigo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "Yuli", 1));
        arraydir.add(new ItemAmigo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "Isma", 1));
        arraydir.add(new ItemAmigo("https://lh3.googleusercontent.com/-2_grrZEzMTk/AAAAAAAAAAI/AAAAAAAAABI/6XQWT20qEBs/photo.jpg", "Victoria", 1));
        
        adapter = new AdapterItemAmigo(getActivity(), arraydir);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(rutasListClickListener());
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
