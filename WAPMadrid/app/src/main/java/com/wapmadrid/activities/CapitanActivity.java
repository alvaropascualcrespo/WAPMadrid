package com.wapmadrid.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemLayoutCapitan;
import com.wapmadrid.adapters.CapitanPageAdapter;
import com.wapmadrid.data.ItemLayoutCapitan;
import com.wapmadrid.utilities.Constants;

public class CapitanActivity extends FragmentActivity {
	ArrayList<ItemLayoutCapitan> arraydir;
	AdapterItemLayoutCapitan adapter;
	GridView lista;
	private String groupID;
	private String routeID;
	ViewPager mViewPager;
	private CapitanPageAdapter capitanPageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capitan);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.action_bar_negro);

		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub

			}
		};
		groupID = getIntent().getStringExtra(Constants.GROUP_ID);
		routeID = getIntent().getStringExtra("ROUTE_ID");

		String[] grupo_tabs = getResources().getStringArray(R.array.capitan_tabs);
		for (int i = 0; i < grupo_tabs.length; i++)
			actionBar.addTab(actionBar.newTab()
					.setText(grupo_tabs[i])
					.setTabListener(tabListener));
		capitanPageAdapter = new CapitanPageAdapter(this, getSupportFragmentManager(), groupID,routeID);

		// Set up the ViewPager with the sections adapter.

		mViewPager.setOnPageChangeListener(
				new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						getActionBar().setSelectedNavigationItem(position);
					}
				});
		mViewPager.setAdapter(capitanPageAdapter);

		final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");
		findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Do something
			}
		});

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

	public String getRouteDistance() {
		return capitanPageAdapter.getRouteDistance();
	}
}
