package com.wapmadrid.activities;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wapmadrid.NavigationDrawerFragment;
import com.wapmadrid.R;
import com.wapmadrid.fragments.AmigosListFragment;
import com.wapmadrid.fragments.CapitanListFragment;
import com.wapmadrid.fragments.CentroMedicoFragment;
import com.wapmadrid.fragments.EnlacesFragment;
import com.wapmadrid.fragments.GrupoListFragment;
import com.wapmadrid.fragments.HomeFragment;
import com.wapmadrid.fragments.PerfilFragment;
import com.wapmadrid.fragments.RutasFragment;

public class InicioActivity extends FragmentActivity {
	
	private ListView drawerList;
    private String[] drawerOptions;
    private DrawerLayout drawerLayout;
    private int lastIndex = 0;
    protected static final String OPTION = "option";
    private ActionBarDrawerToggle drawerToggle;	
	private Fragment[] fragments = new Fragment[]{	new HomeFragment(),
													new RutasFragment(),
													new CentroMedicoFragment(),
													new PerfilFragment(),
													new AmigosListFragment(),
													new GrupoListFragment(),
													new CapitanListFragment(),
													new EnlacesFragment()};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		
		int option = 0;
        if (getIntent().hasExtra(OPTION)){
        	option = Integer.parseInt(getIntent().getStringExtra(OPTION));
        }
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
		//Menu lateral
		drawerLayout = (DrawerLayout) this.findViewById(R.id.drawerLayout);
        drawerList = (ListView) this.findViewById(R.id.leftDrawer);
        drawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                                                       R.layout.drawer_list_item, 
                                                       drawerOptions));
        
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        drawerList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        drawerList.setItemChecked(0, true);
        
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 
        											R.drawable.ic_drawer, 
        											R.string.drawer_open, 
        											R.string.drawer_close){
            
        	@Override
			public void onDrawerClosed(View view) {
        		
            	invalidateOptionsMenu();
            	getActionBar().setIcon(R.drawable.action_bar_negro);
            	if (lastIndex == 0){
            		getActionBar().setTitle("");
            	}else
            		getActionBar().setTitle(drawerOptions[lastIndex]);
            }

        	@Override
			public void onDrawerOpened(View drawerView) {
            	invalidateOptionsMenu();
            	getActionBar().setIcon(R.drawable.logo_wap_negro);
            	getActionBar().setTitle("");
            }
        };
        
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(drawerToggle);
		
        //Cambios de fragmento
		FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
        	    .add(R.id.contentFrame, fragments[0])
        		.add(R.id.contentFrame, fragments[1])
        		.add(R.id.contentFrame, fragments[2])
        		.add(R.id.contentFrame, fragments[3])
        		.add(R.id.contentFrame, fragments[4])
        		.add(R.id.contentFrame, fragments[5])
        		.add(R.id.contentFrame, fragments[6])
        		.add(R.id.contentFrame, fragments[7])
        	    .commit();	
        
        manager.beginTransaction().hide(fragments[1])
        						  .hide(fragments[2])
        						  .hide(fragments[3])
        						  .hide(fragments[4])
        						  .hide(fragments[5])
        						  .hide(fragments[6])
        						  .hide(fragments[7])
				        		  .commit();
        
        setContent(option);
        
        getActionBar().setIcon(R.drawable.action_bar_negro);
	}
	
	
	
	 @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        drawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        drawerToggle.onConfigurationChanged(newConfig);
	    }
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("deprecation")
	public void setContent(int index) {
	    Fragment toHide = null;
		Fragment toShow = null;
		final ActionBar actionBar = getActionBar();
		toHide = fragments[lastIndex];
		toShow = fragments[index];
		lastIndex = index;

		FragmentManager manager = getSupportFragmentManager();
		 
		/*if (lastIndex != 1 || index != 1) 
		manager.beginTransaction().detach(toHide).attach(toShow).commit();*/
		
		manager.beginTransaction()
				.hide(toHide)
				.show(toShow)
				.commit();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(drawerOptions[index]);
		drawerList.setItemChecked(index, true);
	    drawerLayout.closeDrawer(drawerList);	
	   
	    if ((index == 0) || (index == 4) || (index == 6) || (index == 7)) {
	    	actionBar.setTitle("");
	    }
	    if (index == 1) {
	    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    	((RutasFragment) toShow).setTabs();
	    }
	    if (index == 2) {
	    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    	((CentroMedicoFragment) toShow).setTabs();
	    }
	    if (index == 3) {
	    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    	((PerfilFragment) toShow).setTabs();
	    }
	    
    }
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    	setContent(position);
	    }
	}

}
