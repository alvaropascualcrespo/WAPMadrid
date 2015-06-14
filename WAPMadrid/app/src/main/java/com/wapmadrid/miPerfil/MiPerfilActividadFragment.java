package com.wapmadrid.miPerfil;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.maps.SupportMapFragment;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItem;
import com.wapmadrid.data.Item;
import com.wapmadrid.modelos.Walker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MiPerfilActividadFragment extends Fragment{

	private Walker walker;
	private CombinedChart chartEjercicio;

	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {

	        View v = inflater.inflate(R.layout.amigo_actividad_fragment, container, false);
			chartEjercicio = (CombinedChart) v.findViewById(R.id.chartEjercicio);
	        return v;
	    }
	
	public void fill() {
		// TODO Auto-generated method stub
		
	}

	public void setWalker(Walker walker) {
		this.walker = walker;
		configChart();
		setData(walker.getStats_distance(), walker.getStats_date(), walker.getStats_kcal());
	}

	private void setData(ArrayList<String> distance_value, ArrayList<String> diet_date, ArrayList<String> stats_kcal) {
		CombinedData data = new CombinedData(diet_date);

		data.setData(generateLineData(distance_value));
		data.setData(generateBarData(stats_kcal));

		data.setValueTextSize(10f);

		chartEjercicio.setData(data);
		chartEjercicio.invalidate();
	}

	private BarData generateBarData(ArrayList<String> value) {
		BarData d = new BarData();
		ArrayList<BarEntry> values = new ArrayList<>();

		for (int i = 0; i < value.size(); i++) {
			values.add(new BarEntry(Float.valueOf(value.get(i)), i));
		}

		BarDataSet setDistance = new BarDataSet(values, "Kcal");
		setDistance.setColor(getResources().getColor(R.color.orange_wap));
		setDistance.setValueTextColor(getResources().getColor(R.color.black));
		setDistance.setValueTextSize(10f);
		d.addDataSet(setDistance);

		setDistance.setAxisDependency(YAxis.AxisDependency.RIGHT);

		return d;
	}

	private LineData generateLineData(ArrayList<String> value) {
		LineData d = new LineData();

		ArrayList<Entry> entries = new ArrayList<>();

		for (int i = 0; i < value.size(); i++) {
			entries.add(new Entry(Float.valueOf(value.get(i)), i));
		}

		LineDataSet set = new LineDataSet(entries, "KM");
		set.setColor(getResources().getColor(R.color.background_cards));
		set.setLineWidth(2.5f);
		set.setCircleColor(getResources().getColor(R.color.background_cards));
		set.setCircleSize(5f);
		set.setFillColor(Color.rgb(240, 238, 70));
		set.setDrawCubic(true);
		set.setDrawValues(true);
		set.setValueTextSize(10f);
		set.setValueTextColor(getResources().getColor(R.color.black));

		set.setAxisDependency(YAxis.AxisDependency.LEFT);

		d.addDataSet(set);

		return d;
	}

	private void configChart() {
		chartEjercicio.setDescription("");
		chartEjercicio.setBackgroundColor(getResources().getColor(R.color.background_cards));
		chartEjercicio.setActivated(false);
		YAxis leftAxis = chartEjercicio.getAxisLeft();
		leftAxis.setEnabled(true);
		leftAxis.setDrawGridLines(false);
		leftAxis.setSpaceTop(95.0f);


		YAxis rightAxis = chartEjercicio.getAxisRight();
		rightAxis.setEnabled(true);
		rightAxis.setDrawGridLines(false);
		rightAxis.setSpaceTop(35.0f);

		Legend l = chartEjercicio.getLegend();
		l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
		l.setForm(Legend.LegendForm.CIRCLE);
		l.setFormSize(9f);
		l.setTextSize(11f);
		l.setXEntrySpace(4f);

		chartEjercicio.setDrawOrder(new CombinedChart.DrawOrder[]{
				CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
		});
	}
}
