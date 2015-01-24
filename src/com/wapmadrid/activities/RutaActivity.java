package com.wapmadrid.activities;

import com.wapmadrid.R;
import com.wapmadrid.fragments.MapFragment;
import com.wapmadrid.fragments.RutasListFragment;
import com.wapmadrid.ruta.RutaDescripcionFragment;
import com.wapmadrid.ruta.RutaMapFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RutaActivity extends FragmentActivity implements ActionBar.TabListener{

	private Fragment[] fragments = new Fragment[]{ 	new RutaDescripcionFragment(),
													new RutaMapFragment()};
	private int lastIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ruta);
		String[] tabs = getResources().getStringArray(R.array.ruta_tabs);
		final ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
		for (int i = 0; i</*tabs.length*/2; i++){
	        actionBar.addTab(
	                actionBar.newTab()
	                        .setText(tabs[i])
	                        .setTabListener(this));
		}
		FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
        	    .add(R.id.ruta_tabs, fragments[0])
        	    .add(R.id.ruta_tabs, fragments[1])
        	    .commit();	
        
        manager.beginTransaction().hide(fragments[1])
				        		  .commit();
        
        setContent(0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_rutas, container, false);// -- linea original
		
		
		setHasOptionsMenu(true);
		//setTabs();
		
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction()
        	    .add(R.id.rutasListTab, fragments[0])
        	    .add(R.id.rutasListTab, fragments[1])
        	    .commit();	
        
        manager.beginTransaction().hide(fragments[1])
				        		  .commit();
		return view;
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
