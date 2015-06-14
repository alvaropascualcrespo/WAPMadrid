package com.wapmadrid.activities;

import com.wapmadrid.R;
import com.wapmadrid.rutaIndividual.RutaDescripcionFragment;
import com.wapmadrid.rutaIndividual.RutaMapFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

public class RutaActivity extends FragmentActivity implements ActionBar.TabListener{

	private Fragment[] fragments = new Fragment[]{ 	new RutaDescripcionFragment(),
													new RutaMapFragment()};
	private int lastIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ruta);
		final ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.action_bar_negro);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
        	    .add(R.id.rutaLayout, fragments[0])
        	    .add(R.id.rutaLayout, fragments[1])
        	    .commit();	
        
        manager.beginTransaction().hide(fragments[1])
				        		  .commit();
        
        setContent(0);
	}

	private void setContent(int index) {
		Fragment toHide = null;
		Fragment toShow = null;
		
		toHide = fragments[lastIndex];
		toShow =  fragments[index];
		lastIndex = index;
		
		FragmentManager manager = getSupportFragmentManager();
		
		manager.beginTransaction()
		.hide(toHide)
		.show(toShow)
		.commit();


		if (index == 0) ((RutaDescripcionFragment) toShow).fill();
		if (index == 1) ((RutaMapFragment) toShow).fill();
		
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		setContent(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
			case android.R.id.home:
				Intent i = new Intent(this, InicioActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
				break;
		}
		return true;
	}
	
}
