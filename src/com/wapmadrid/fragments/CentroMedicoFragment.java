package com.wapmadrid.fragments;

import com.wapmadrid.R;
import com.wapmadrid.centroMedico.CentroMedicoDescripcionFragment;
import com.wapmadrid.centroMedico.CentroMedicoEventosFragment;
import com.wapmadrid.rutas.MapFragment;
import com.wapmadrid.rutas.RutasListFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CentroMedicoFragment extends Fragment implements  ActionBar.TabListener{

	private Fragment[] fragments = new Fragment[]{ 	new CentroMedicoDescripcionFragment(),
			new CentroMedicoEventosFragment()};
	private int lastIndex = 0;	

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_centro_medico, container, false);// -- linea original
		
		
		setHasOptionsMenu(true);
		//setTabs();
		
		FragmentManager manager = getChildFragmentManager();
		manager.beginTransaction()
		.add(R.id.centroMedicoLayout, fragments[0])
		.add(R.id.centroMedicoLayout, fragments[1])
		.commit();	
		
		manager.beginTransaction().hide(fragments[1])
		.commit();
		return view;
		}
		
		public void setTabs(){
		
		final ActionBar actionBar = getActivity().getActionBar();
		
			actionBar.removeAllTabs();
			
			actionBar.addTab(
					actionBar.newTab()
						.setText("Información")
						.setTabListener(this));
			
			actionBar.addTab(
					actionBar.newTab()
						.setText("Eventos")
						.setTabListener(this));
		
		}
		
		public void setContent(int index) {
			Fragment toHide = null;
			Fragment toShow = null;
			
			toHide = fragments[lastIndex];
			toShow =  fragments[index];
			lastIndex = index;
			
			FragmentManager manager = getChildFragmentManager();
			
			manager.beginTransaction()
				.hide(toHide)
				.show(toShow)
				.commit();
			
			if (index == 0){
				((CentroMedicoDescripcionFragment)toShow).fill();
			}
			if (index == 1) {
				((CentroMedicoEventosFragment) toShow).fill();
			}
		}
		
		@Override
		public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
			// TODO Auto-generated method stub
		
		}
		
		@Override
		public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
			setContent(arg0.getPosition());
		
		}
		
		@Override
		public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
			// TODO Auto-generated method stub
		
		}
	
}
