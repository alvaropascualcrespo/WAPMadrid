package com.wapmadrid.grupo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.wapmadrid.R;
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

        groupID = getArguments().getString(Constants.GROUP_ID);

        btnJoinRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });

        fill();

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
                        if (!member){
                            btnJoinRequest.setVisibility(View.VISIBLE);
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

}
