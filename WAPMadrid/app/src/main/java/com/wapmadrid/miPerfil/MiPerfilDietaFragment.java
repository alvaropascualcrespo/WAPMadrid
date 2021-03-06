package com.wapmadrid.miPerfil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wapmadrid.R;
import com.wapmadrid.miPerfil.cuestionario.CuestionarioActivity;
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.utilities.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ismael on 18/05/2015.
 */
public class MiPerfilDietaFragment  extends Fragment {


    private Walker walker;
    private LineChart chartDieta;
    private Button btnCuestionario;
    // private BarGraph chartIMC;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.perfil_dieta_fragment, container, false);

        chartDieta = (LineChart) v.findViewById(R.id.chartDieta);
        btnCuestionario = (Button) v.findViewById(R.id.btnCuestionario);

        btnCuestionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cuestionarioIntent = new Intent(getActivity().getApplicationContext(), CuestionarioActivity.class);
                getActivity().startActivityForResult(cuestionarioIntent, Constants.RESULT_EDIT);
            }
        });


     //   chartDieta.setDrawBarShadow(false);
     //   chartDieta.setDrawValueAboveBar(true);
        chartDieta.setDescription("");
        chartDieta.setMaxVisibleValueCount(60);
        chartDieta.setPinchZoom(false);
        chartDieta.setDrawGridBackground(false);

        return v;
    }

    public void setWalker(Walker walker) {
        this.walker = walker;
        configChart();
        setData(walker.getDiet_value(), walker.getDiet_date());
    }

    private void setData(ArrayList<String> diet_value, ArrayList<String> diet_date) {
        ArrayList<Entry> imcVals = new ArrayList<Entry>();

        for (int i = 0; i < diet_value.size(); i++) {
            imcVals.add(new Entry(Float.valueOf(diet_value.get(i)), i));
        }

        LineDataSet setIMC = new LineDataSet(imcVals, "Dieta Mediterranea");
       // setIMC.setBarSpacePercent(35f);
        setIMC.setColor(getResources().getColor(R.color.orange_wap));
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setIMC);

        LineData data = new LineData (diet_date, dataSets);
        data.setValueTextSize(10f);

        chartDieta.animateY(2500);
        chartDieta.setData(data);
        chartDieta.invalidate();
    }

    private void configChart() {
        YAxis leftAxis = chartDieta.getAxisLeft();
        leftAxis.setEnabled(false);


        YAxis rightAxis = chartDieta.getAxisRight();
        rightAxis.setStartAtZero(false);
        rightAxis.setDrawGridLines(true);
        rightAxis.setLabelCount(2);
        rightAxis.setAxisMinValue(50);

        Legend l = chartDieta.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }
}
