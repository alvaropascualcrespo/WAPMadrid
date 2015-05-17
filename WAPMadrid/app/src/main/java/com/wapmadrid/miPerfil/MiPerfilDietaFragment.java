package com.wapmadrid.miPerfil;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wapmadrid.modelos.Walker;

import com.wapmadrid.R;
import com.wapmadrid.modelos.Walker;

import java.util.ArrayList;


/**
 * Created by Ismael on 17/05/2015.
 */
public class MiPerfilDietaFragment extends Fragment {


    private Walker walker;
   // private BarGraph chartIMC;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.amigo_diet_fragment, container, false);

      //  chartIMC = (BarGraph) v.findViewById(R.id.chartIMC);


        return v;
    }

    public void setWalker(Walker walker) {
        this.walker = walker;
    /*    ArrayList<Bar> points = new ArrayList<Bar>();
        ArrayList<String> weight_imc = walker.getWeight_imc();
        ArrayList<String> weight_date = walker.getWeight_date();
        for (int i=0; i < weight_date.size(); i++){
            Bar d = new Bar();
            d.setColor(Color.parseColor("#99CC00"));
            d.setName(weight_date.get(i));
            d.setValue(Float.valueOf(weight_imc.get(i)));
            points.add(d);
        }
        chartIMC.setBars(points);*/

    }

}
