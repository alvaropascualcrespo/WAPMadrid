package com.wapmadrid.miPerfil.cuestionario;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wapmadrid.miPerfil.cuestionario.CuestionarioFragment;
import com.wapmadrid.utilities.Constants;

public class CuestionarioPageAdapter extends FragmentStatePagerAdapter {

    private Fragment[] fragments = new Fragment[]{new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
            new CuestionarioFragment(),
    };
    private Activity activity;

    public CuestionarioPageAdapter(Activity activity, FragmentManager fm) {
        super(fm);
        this.activity = activity;
        for (int i = 0; i < fragments.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.FRAGMENT_POSITION, i);
            fragments[i].setArguments(bundle);
        }
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
        return "Pregunta " + (position + 1) + " de 14";
    }
}