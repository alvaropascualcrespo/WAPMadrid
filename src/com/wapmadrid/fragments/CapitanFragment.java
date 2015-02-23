package com.wapmadrid.fragments;

import com.wapmadrid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CapitanFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_capitan, container, false);// -- linea original
		setHasOptionsMenu(true);
		return view;
	}
	
}
