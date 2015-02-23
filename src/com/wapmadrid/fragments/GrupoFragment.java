package com.wapmadrid.fragments;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wapmadrid.R;
import com.wapmadrid.grupo.GrupoCaracteristicasFragment;
import com.wapmadrid.grupo.GrupoFotosFragment;
import com.wapmadrid.grupo.GrupoMiembrosFragment;
import com.wapmadrid.grupo.GrupoNotasFragment;
import com.wapmadrid.grupo.GrupoRutaFragment;

public class GrupoFragment extends Fragment implements  ActionBar.TabListener{

	private Fragment[] fragments = new Fragment[]{ 	new GrupoCaracteristicasFragment(),
			new GrupoRutaFragment(), new GrupoMiembrosFragment(), new GrupoFotosFragment(),
			new GrupoNotasFragment()};
	private int lastIndex = 0;	

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_grupo, container, false);// -- linea original
		
		
		setHasOptionsMenu(true);
		//setTabs();
		
		FragmentManager manager = getChildFragmentManager();
		manager.beginTransaction()
		.add(R.id.grupoLayout, fragments[0])
		.add(R.id.grupoLayout, fragments[1])
		.add(R.id.grupoLayout, fragments[2])
		.add(R.id.grupoLayout, fragments[3])
		.add(R.id.grupoLayout, fragments[4])
		.commit();	
		
		manager.beginTransaction().hide(fragments[1])
								  .hide(fragments[2])
        						  .hide(fragments[3])
        						  .hide(fragments[4])
		.commit();
		return view;
		}
		
		public void setTabs(){
		
		final ActionBar actionBar = getActivity().getActionBar();
		
			actionBar.removeAllTabs();
			
			actionBar.addTab(
					actionBar.newTab()
						.setText("Características")
						.setTabListener(this));
			
			actionBar.addTab(
					actionBar.newTab()
						.setText("Ruta")
						.setTabListener(this));
			
			actionBar.addTab(
					actionBar.newTab()
						.setText("Miembros")
						.setTabListener(this));
			
			actionBar.addTab(
					actionBar.newTab()
						.setText("Fotos")
						.setTabListener(this));
			
			actionBar.addTab(
					actionBar.newTab()
						.setText("Notas")
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
				((GrupoCaracteristicasFragment)toShow).fill();
			}
			if (index == 1) {
				((GrupoRutaFragment) toShow).fill();
			}
			if (index == 2) {
				((GrupoMiembrosFragment) toShow).fill();
			}
			if (index == 3) {
				((GrupoFotosFragment) toShow).fill();
			}
			if (index == 4) {
				((GrupoNotasFragment) toShow).fill();
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
