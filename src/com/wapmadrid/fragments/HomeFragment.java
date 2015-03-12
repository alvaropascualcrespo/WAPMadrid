package com.wapmadrid.fragments;

import java.util.ArrayList;

import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItem;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.Item;
import com.wapmadrid.data.ItemAmigo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class HomeFragment extends Fragment {

	ArrayList<Item> arraydir;
	AdapterItem adapter;
    private ListView list;
    private ProgressBar pgHome;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        list = (ListView) v.findViewById(R.id.listaHome);
		list.setOnItemClickListener(eventosHomeListClickListener());
		pgHome = (ProgressBar) v.findViewById(R.id.pgHome);
        return v;
    }
	
	private OnItemClickListener eventosHomeListClickListener() {
		// TODO Auto-generated method stub
		return null;
	}

	public void fill() {
		// TODO Auto-generated method stub
		
	}
	
}
