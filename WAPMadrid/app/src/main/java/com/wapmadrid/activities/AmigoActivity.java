package com.wapmadrid.activities;

import com.wapmadrid.R;
import com.wapmadrid.miPerfil.MiPerfilActividadFragment;
import com.wapmadrid.miPerfil.MiPerfilDietaFragment;
import com.wapmadrid.miPerfil.MiPerfilInfoFragment;
import com.wapmadrid.rutaIndividual.RutaDescripcionFragment;
import com.wapmadrid.rutaIndividual.RutaMapFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class AmigoActivity extends FragmentActivity implements ActionBar.TabListener {

    public static final String ID = "id";
    private Fragment[] fragments = new Fragment[]{new MiPerfilInfoFragment(),
            new MiPerfilDietaFragment(),
            new MiPerfilActividadFragment()};

    private int lastIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo);
        String[] tabs = getResources().getStringArray(R.array.amigo_tabs);
        final ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Ismael Requena");
        actionBar.setDisplayHomeAsUpEnabled(true);
        for (int i = 0; i </*tabs.length*/3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabs[i])
                            .setTabListener(this));
        }
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fgAmigoLayout, fragments[0])
                .add(R.id.fgAmigoLayout, fragments[1])
                .add(R.id.fgAmigoLayout, fragments[2])
                .commit();

        manager.beginTransaction().hide(fragments[1])
                .hide(fragments[2])
                .commit();

        setContent(0);
    }

    private void setContent(int index) {
        Fragment toHide = null;
        Fragment toShow = null;

        toHide = fragments[lastIndex];
        toShow = fragments[index];
        lastIndex = index;

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .hide(toHide)
                .show(toShow)
                .commit();


        //	if (index == 0) ((MiPerfilInfoFragment) toShow).fill();
        if (index == 1) ((MiPerfilActividadFragment) toShow).fill();

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

}