package com.wapmadrid.miPerfil.cuestionario;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.wapmadrid.R;

/**
 * Created by Ismael on 18/05/2015.
 */
public class CuestionarioActivity  extends FragmentActivity {


    private CuestionarioPageAdapter pageAdapterCuestionario;
    private ViewPager cuestionarioViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        pageAdapterCuestionario = new CuestionarioPageAdapter(this, getSupportFragmentManager());

        cuestionarioViewPager = (ViewPager) findViewById(R.id.cuestionarioViewPager);
        cuestionarioViewPager.setAdapter(pageAdapterCuestionario);

        cuestionarioViewPager.setCurrentItem(1);
        cuestionarioViewPager.setOffscreenPageLimit(1);
    }
}
