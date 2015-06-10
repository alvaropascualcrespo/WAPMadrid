package com.wapmadrid.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wapmadrid.R;
import com.wapmadrid.grupo.GrupoCaracteristicasFragment;
import com.wapmadrid.grupo.GrupoFotosFragment;
import com.wapmadrid.grupo.GrupoMiembrosFragment;
import com.wapmadrid.grupo.GrupoNotasFragment;
import com.wapmadrid.grupo.GrupoRutaFragment;
import com.wapmadrid.utilities.Constants;

public class GrupoPageAdapter extends FragmentStatePagerAdapter {
	
	private Fragment[] fragments = new Fragment[]{ 	new GrupoCaracteristicasFragment(),
			new GrupoRutaFragment(),
			 new GrupoMiembrosFragment(),
			new GrupoNotasFragment()};
	private String[] grupo_navigation;

		public GrupoPageAdapter(Activity activity, FragmentManager fm, String groupID, String routeID) {
			super(fm);
			for (int i = 0; i < fragments.length; i++){
				Bundle bundle = new Bundle();
				bundle.putString(Constants.GROUP_ID,groupID);
				bundle.putString("ROUTE_ID",routeID);
				fragments[i].setArguments(bundle);
			}
			grupo_navigation = activity.getResources().getStringArray(R.array.grupo_tabs);
		}

		@Override
		public Fragment getItem(int i) {
			return fragments[i];
		}
		
		@Override
		public int getCount() {
			return fragments.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return grupo_navigation[position];
		}
}
