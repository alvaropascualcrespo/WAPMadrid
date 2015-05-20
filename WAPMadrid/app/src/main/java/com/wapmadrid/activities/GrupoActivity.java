package com.wapmadrid.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import com.wapmadrid.utilities.Constants;


public class GrupoActivity extends FragmentActivity {

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
    private String groupID;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gupo);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
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
        groupID = getIntent().getStringExtra(Constants.GROUP_ID);

        String[] grupo_tabs = getResources().getStringArray(R.array.grupo_tabs);
        for (int i = 0; i < grupo_tabs.length; i++)
            actionBar.addTab(actionBar.newTab()
                    .setText(grupo_tabs[i])
                    .setTabListener(tabListener));
        grupoPageAdapter = new GrupoPageAdapter(this, getSupportFragmentManager(), groupID);

        // Set up the ViewPager with the sections adapter.

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        mViewPager.setAdapter(grupoPageAdapter);

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
                startActivity(i);
                break;
        }
        return true;
    }


}
