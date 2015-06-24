package com.wapmadrid.grupo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.wapmadrid.R;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrupoCaracteristicasFragment extends Fragment{

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
    private Button btnJoinRequest;
    private HorizontalBarChart chartStatsGrupo;
    private ArrayList<Float> stats_value;
    private ArrayList<String> stats_date;
    private NetworkImageView imgGroup;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_grupo_caracterisitcas, container, false);
        pgCMDescripcion = (ProgressBar) v.findViewById(R.id.pgCMDescripcion);
        txtNombreGrupo = (TextView) v.findViewById(R.id.txtNombreGrupo);
        txtRuta = (TextView) v.findViewById(R.id.txtRuta);
        txtNombreCapitan = (TextView) v.findViewById(R.id.txtNombreCapitan);
        txtEmail = (TextView) v.findViewById(R.id.txtEmail);
        txtHorario = (TextView) v.findViewById(R.id.txtHorario);
        txtNivel = (TextView) v.findViewById(R.id.txtNivel);
        btnJoinRequest = (Button) v.findViewById(R.id.btnJoinRequest);
        chartStatsGrupo = (HorizontalBarChart) v.findViewById(R.id.chartStatsGrupo);
        imgGroup = (NetworkImageView) v.findViewById(R.id.imgGroup);


        groupID = getArguments().getString(Constants.GROUP_ID);

        btnJoinRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        fill();
        configChart();
        return v;
    }

    private void sendRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        final String[] cred = dm.getCred();
        String url = Helper.getJoinGrupoUrl(cred[0]);


        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    if (error.equals("0")) {
                        Toast.makeText(getActivity(),"Petición enviada",Toast.LENGTH_SHORT).show();
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
                        String picture;

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
                        if (!member){
                            btnJoinRequest.setVisibility(View.VISIBLE);
                        }
                        stats_value = new ArrayList<>();
                        stats_date = new ArrayList<>();
                        JSONArray auxStats = group.getJSONArray("stats");
                        float totalDist = 0;
                        RequestQueue requestQueueImagen = Volley.newRequestQueue(getActivity().getApplicationContext());
                        ImageLoader imageLoader = new ImageLoader(requestQueueImagen, new BitmapLRUCache());
                        if (group.has("image") && !"".equals(group.getString("image"))){
                            picture = group.getString("image");
                        } else {
                            picture = Helper.getDefaultProfilePictureUrl();
                        }

                        imgGroup.setImageUrl(picture, imageLoader);


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

}
