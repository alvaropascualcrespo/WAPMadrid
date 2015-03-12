package com.wapmadrid.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wapmadrid.R;
import com.wapmadrid.R.id;
import com.wapmadrid.R.layout;
import com.wapmadrid.R.menu;
import com.wapmadrid.adapters.GrupoPageAdapter;


public class GrupoActivity extends FragmentActivity  {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	
	GrupoPageAdapter grupoPageAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gupo);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.action_bar_negro);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		 ActionBar.TabListener tabListener = new ActionBar.TabListener() {
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					mViewPager.setCurrentItem(tab.getPosition());
				}

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					// TODO Auto-generated method stub
					
				}
		    };
		String[] grupo_tabs = getResources().getStringArray(R.array.grupo_tabs);
		for (int i = 0; i < grupo_tabs.length; i++)
			 actionBar.addTab(actionBar.newTab()
		                        .setText(grupo_tabs[i])
		                        .setTabListener(tabListener));
		grupoPageAdapter = new GrupoPageAdapter(this,getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		
		mViewPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    getActionBar().setSelectedNavigationItem(position);
	                }
	            });
		mViewPager.setAdapter(grupoPageAdapter);

	}

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


	

}
