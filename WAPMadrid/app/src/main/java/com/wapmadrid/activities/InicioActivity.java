package com.wapmadrid.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.wapmadrid.R;
import com.wapmadrid.centroMedico.CentroMedicoDescripcionFragment;
import com.wapmadrid.fragments.CapitanListFragment;
import com.wapmadrid.fragments.EnlacesFragment;
import com.wapmadrid.fragments.GrupoListFragment;
import com.wapmadrid.fragments.HomeFragment;
import com.wapmadrid.fragments.PerfilFragment;
import com.wapmadrid.fragments.RutasListFragment;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;

public class InicioActivity extends FragmentActivity {

    private ListView drawerList;
    private String[] drawerOptions;
    private DrawerLayout drawerLayout;
    private int lastIndex = 0;
    protected static final String OPTION = "option";
    private ActionBarDrawerToggle drawerToggle;
    private Fragment[] fragments = new Fragment[]{new HomeFragment(),
            new RutasListFragment(),
            new CentroMedicoDescripcionFragment(),
            new PerfilFragment(),
          //  new AmigosListFragment(),
            new GrupoListFragment(),
            new CapitanListFragment(),
            new EnlacesFragment()};
    private FrameLayout contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        int option = 0;
        if (getIntent().hasExtra(OPTION)) {
            option = Integer.parseInt(getIntent().getStringExtra(OPTION));
        }

        contentFrame = (FrameLayout) findViewById(R.id.contentFrame);

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
                R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {

                invalidateOptionsMenu();
                getActionBar().setIcon(R.drawable.action_bar_negro);
                if (lastIndex == 0) {
                    getActionBar().setTitle("");
                } else
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
               // .add(R.id.contentFrame, fragments[7])
                .commitAllowingStateLoss();

        manager.beginTransaction().hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .hide(fragments[4])
                .hide(fragments[5])
                .hide(fragments[6])
          //      .hide(fragments[7])
                .commitAllowingStateLoss();

        contentFrame.setVisibility(View.VISIBLE);
        drawerList.setVisibility(View.VISIBLE);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
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
                .commitAllowingStateLoss();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(drawerOptions[index]);
        drawerList.setItemChecked(index, true);
        drawerLayout.closeDrawer(drawerList);

        if ((index == 0) || (index == 4) || (index == 6) || (index == 7)) {
            actionBar.setTitle("");
        }
   /*     if (index == 0){
            ((HomeFragment) toShow).fill();
        }*/
        if (index == 1) {
            ((RutasListFragment) toShow).fill();
        }
        if (index == 3) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            ((PerfilFragment) toShow).setTabs();
            ((PerfilFragment) toShow).fill();
        }
        if (index == 4) {
            ((GrupoListFragment) toShow).fill();
        }
        if (index == 5) {
            ((CapitanListFragment) toShow).fill();
        }

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT))
                    drawerLayout.closeDrawer(Gravity.LEFT);
                else
                    drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.logOut:
                logOut();
                break;
        }
        return true;
    }

    private void logOut() {
        DataManager dm = new DataManager(this);
        String[] cred = dm.getCred();
        dm.logout();
        //TODO Llamar a API
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            setContent(position);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
           /* if (requestCode == Constants.RESULT_EDIT) {
                setContent(3);
                ((PerfilFragment) fragments[3]).fill();
            } else if (requestCode == Constants.NUEVO_GRUPO) {
                setContent(4);
                ((GrupoListFragment) fragments[4]).fill();
            } else if (requestCode == Constants.RESULT_SELECTED_CMS){
                setContent(2);
                ((CentroMedicoDescripcionFragment) fragments[2]).fill();
            }*/
            recreate();
        }
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }
}
