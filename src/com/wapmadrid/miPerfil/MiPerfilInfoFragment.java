package com.wapmadrid.miPerfil;

import com.wapmadrid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MiPerfilInfoFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.amigo_info_fragment, container, false);
        
        return v;
    }
	
	public void fill() {
		// TODO Auto-generated method stub
		
	}

}
