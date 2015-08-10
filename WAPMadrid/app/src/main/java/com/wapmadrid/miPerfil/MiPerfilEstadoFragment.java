package com.wapmadrid.miPerfil;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.wapmadrid.miPerfil.editarPerfil.EditInfoActivity;
import com.wapmadrid.miPerfil.editarPerfil.EditStatusActivity;
import com.wapmadrid.modelos.Walker;

import com.wapmadrid.R;
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.utilities.Constants;

import java.util.ArrayList;


/**
 * Created by Ismael on 17/05/2015.
 */
public class MiPerfilEstadoFragment extends Fragment {


    private Walker walker;
    private LineChart chartIMC;
    private TextView txtAltura;
    private TextView txtPeso;
    private TextView txtFumador;
    private TextView txtAlcohol;
    private Button btnEditProfile;
    // private BarGraph chartIMC;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.perfil_status_fragment, container, false);

        chartIMC = (LineChart) v.findViewById(R.id.chartIMC);
        txtAltura = (TextView) v.findViewById(R.id.txtAltura);
        txtPeso = (TextView) v.findViewById(R.id.txtPeso);
        txtFumador = (TextView) v.findViewById(R.id.txtFumador);
        txtAlcohol = (TextView) v.findViewById(R.id.txtAlcohol);
        btnEditProfile = (Button)v.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditStatusActivity.class);
                intent.putExtra("WALKER", walker);
                getActivity().startActivityForResult(intent, Constants.RESULT_EDIT);
            }
        });




        chartIMC.setDescription("");
        chartIMC.setMaxVisibleValueCount(60);
        chartIMC.setPinchZoom(false);
        chartIMC.setDrawGridBackground(false);

        

        return v;
    }

    public void setWalker(Walker walker) {
        this.walker = walker;
        configChart();
        ArrayList<String> weight_value = walker.getWeight_value();
        setData(walker.getWeight_imc(), weight_value, walker.getWeight_date());
       
        String alturaRes = getResources().getString(R.string.altura);
        String pesoRes = getResources().getString(R.string.peso);
        String fumadorRes = getResources().getString(R.string.fumador);
        String alcoholRes = getResources().getString(R.string.alcohol);
        String height = walker.getHeight();
        int size = weight_value.size();
        String weight = "0";
        if (size > 0)
            weight = weight_value.get(size - 1);
        String smoker = walker.getSmoker();
        String alcohol = walker.getAlcohol();
        txtAltura.setText(String.format(alturaRes,height));
        txtPeso.setText(String.format(pesoRes,weight));
        txtFumador.setText(String.format(fumadorRes, smoker));
        txtAlcohol.setText(String.format(alcoholRes,alcohol));

    }

    private void configChart() {
        YAxis leftAxis = chartIMC.getAxisLeft();
        leftAxis.setEnabled(false);


        YAxis rightAxis = chartIMC.getAxisRight();
        rightAxis.setStartAtZero(false);
        rightAxis.setDrawGridLines(true);
        rightAxis.setLabelCount(2);
        rightAxis.setAxisMinValue(50);

        Legend l = chartIMC.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    private void setData(ArrayList<String> weight_imc, ArrayList<String> weight_value, ArrayList<String> weight_date) {
        ArrayList<Entry> imcVals = new ArrayList<Entry>();

        for (int i = 0; i < weight_imc.size(); i++) {
            imcVals.add(new Entry(Float.valueOf(weight_imc.get(i)), i));
        }

        LineDataSet setIMC = new LineDataSet(imcVals, "IMC");
        setIMC.setColor(getResources().getColor(R.color.orange_wap));
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setIMC);

        LineData data = new LineData(weight_date, dataSets);
        data.setValueTextSize(10f);

        chartIMC.animateY(2500);
        chartIMC.setData(data);
        chartIMC.invalidate();
    }



}
