package com.wapmadrid.capitan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.wapmadrid.R;
import com.wapmadrid.activities.CapitanActivity;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GrupoCapitanCaracteristicasFragment extends Fragment{

	private GoogleMap mapa;
	LatLng position;
	private ProgressBar pgCMDescripcion;
    private String groupID;
    private TextView txtNombreGrupo;
    private TextView txtRuta;
    private TextView txtNombreCapitan;
    private TextView txtEmail;
    private TextView txtHorario;
    private TextView txtNivel;
    private Button btnEmpezarRuta;

    private Boolean empezado;

    private Calendar tiempoInicio;
    private Calendar tiempoFin;
    private ArrayList<JSONObject> userIDList;
    private HashMap<String, String> dataToSend;
    private ArrayList<Float> stats_value;
    private ArrayList<String> stats_date;
    private HorizontalBarChart chartStatsGrupo;


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_grupo_capitan_caracterisitcas, container, false);
        pgCMDescripcion = (ProgressBar) v.findViewById(R.id.pgCMDescripcion);
        txtNombreGrupo = (TextView) v.findViewById(R.id.txtNombreGrupo);
        txtRuta = (TextView) v.findViewById(R.id.txtRuta);
        txtNombreCapitan = (TextView) v.findViewById(R.id.txtNombreCapitan);
        txtEmail = (TextView) v.findViewById(R.id.txtEmail);
        txtHorario = (TextView) v.findViewById(R.id.txtHorario);
        txtNivel = (TextView) v.findViewById(R.id.txtNivel);
        btnEmpezarRuta = (Button) v.findViewById(R.id.btnEmpezarRuta);
        chartStatsGrupo = (HorizontalBarChart) v.findViewById(R.id.chartStatsGrupo);

        dataToSend = new HashMap<>();

        empezado = false;

        btnEmpezarRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (empezado) {
                    empezado = false;
                    tiempoFin = Calendar.getInstance();
                    long tiempoEmpleado = tiempoFin.getTimeInMillis() - tiempoInicio.getTimeInMillis();
                    dataToSend.put("timeSpent", String.valueOf(tiempoEmpleado));
                    Intent i = new Intent(getActivity(), CaptainSelectMembersActivity.class);
                    i.putExtra(Constants.GROUP_ID, groupID);
                    startActivityForResult(i, Constants.RESULT_SELECTED_MEMBERS);
                    btnEmpezarRuta.setText(getResources().getText(R.string.empezarRuta));
                } else {
                    btnEmpezarRuta.setText(getResources().getText(R.string.terminarEjercicio));
                    empezado = true;
                    tiempoInicio = Calendar.getInstance();
                }
            }
        });

        groupID = getArguments().getString(Constants.GROUP_ID);

        fill();

        configChart();
        userIDList = new ArrayList<>();

        return v;
    }

    private void configChart() {
        chartStatsGrupo.setDescription("");
        chartStatsGrupo.setActivated(false);
        chartStatsGrupo.animateY(2500);
        YAxis leftAxis = chartStatsGrupo.getAxisLeft();
        leftAxis.setEnabled(false);


        YAxis rightAxis = chartStatsGrupo.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setDrawGridLines(false);
        rightAxis.setSpaceTop(35.0f);

        Legend l = chartStatsGrupo.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
    }

    private void setData(ArrayList<Float> value, ArrayList<String> date) {
        ArrayList<BarEntry> vals = new ArrayList<>();

        for (int i = 0; i < value.size(); i++) {
            vals.add(new BarEntry(value.get(i), i));
        }

        BarDataSet setIMC = new BarDataSet(vals, "KMS totales del grupo");
        setIMC.setBarSpacePercent(35f);
        setIMC.setColor(getResources().getColor(R.color.orange_wap));
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(setIMC);

        BarData data = new BarData(date, dataSets);
        data.setValueTextSize(10f);

        chartStatsGrupo.setData(data);
        chartStatsGrupo.invalidate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.RESULT_SELECTED_MEMBERS){
                ArrayList<String> aux = data.getStringArrayListExtra(Constants.SELECTED_MEMBERS);
                userIDList.clear();
                for (String id : aux) {
                    HashMap<String, String> selected = new HashMap<>();
                    selected.put("id", id);
                    userIDList.add(new JSONObject(selected));
                }
                String distance = ((CapitanActivity) getActivity()).getRouteDistance();
                dataToSend.put("distance", distance);
                sendEndRoute();
            }
        }
    }

    private void sendEndRoute() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        DataManager dm = new DataManager(getActivity().getApplicationContext());
        String[] cred = dm.getCred();
        String url = Helper.getSendGrupoStatsUrl(cred[0]);
        dataToSend.put("token", cred[1]);
        dataToSend.put("groupID", groupID);


        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    if (error.equals("0")) {
                        Toast.makeText(getActivity(),"Datos Guardados",Toast.LENGTH_SHORT).show();
                    } else {
                        String msg = root.getString("error_message");
                        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.e("Error.Response", error.toString());
            }
        };

        StringRequest request = new StringRequest(Request.Method.PUT, url, succeedListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                JSONArray jarray = new JSONArray(userIDList);
                dataToSend.put("members", "{\"members\": " + jarray.toString() + "}");
                return dataToSend;
            }
        };
        requestQueue.add(request);
    }

    private void fill() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        final String[] cred = dm.getCred();
        String url = Helper.getReadGrupoUrl(cred[0]);


        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    if (error.equals("0")) {
                        JSONObject group = root.getJSONObject("group");
                        boolean member = root.getBoolean("member");
                      //  String picture = group.getString("image");
                        String name = group.getString("name");
                        String schedule = group.getString("schedule");
                        String level = group.getString("level");
                        JSONObject captain = group.getJSONObject("captain");
                        String captainName = captain.getString("displayName");
                        String captainEmail = captain.getString("email");
                        JSONObject auxRoute = group.getJSONObject("route");
                        String ruta = auxRoute.getString("name");
                        String nombreRutaRes = getResources().getString(R.string.routeName);
                        String nombreCapitanRes = getResources().getString(R.string.captainName);
                        String emailRes = getResources().getString(R.string.emailCapitan);
                        String nivelRes = getResources().getString(R.string.nivelGrupo);
                        txtNombreGrupo.setText(name);
                        txtRuta.setText(String.format(nombreRutaRes, ruta));
                        txtNombreCapitan.setText(String.format(nombreCapitanRes, captainName));
                        txtEmail.setText(String.format(emailRes, captainEmail));
                        txtNivel.setText(String.format(nivelRes, level));
                        txtHorario.setText(schedule);
                        getActivity().getActionBar().setTitle(name);
                        stats_value = new ArrayList<>();
                        stats_date = new ArrayList<>();
                        JSONArray auxStats = group.getJSONArray("stats");
                        float totalDist = 0;
                        for (int i = 0; i < auxStats.length(); i++) {
                            JSONObject stats = auxStats.getJSONObject(i);
                            totalDist += Float.valueOf(stats.getString("distance"));
                        }
                        if (totalDist != 0){
                            stats_value.add(totalDist);
                            stats_date.add("");
                            setData(stats_value, stats_date);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error
                Log.e("Error.Response", error.toString());
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", cred[1]);
                params.put("groupID", groupID);
                return params;
            }
        };
        requestQueue.add(request);
    }

    public String setDate(String date) {
        String aux = date.substring(0, 10);
        String auxDate[] = aux.split("-");
        return auxDate[2] + "/" + auxDate[1] + "/" + auxDate[0];
    }
}
