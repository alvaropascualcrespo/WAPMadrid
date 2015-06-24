package com.wapmadrid.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.activities.CapitanActivity;
import com.wapmadrid.activities.InicioActivity;
import com.wapmadrid.activities.NewGroupActivity;
import com.wapmadrid.adapters.AdapterItemGrupo;
import com.wapmadrid.data.ItemGrupo;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

public class CapitanListFragment extends Fragment {
    ArrayList<ItemGrupo> arraydir;
    AdapterItemGrupo adapter;
    private ProgressBar pgAmigosList;
    private ListView list;
    private RequestQueue requestQueue;
    private TextView tvNewGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grupo_list, container, false);// -- linea original

        arraydir = new ArrayList<ItemGrupo>();
        list = (ListView) view.findViewById(R.id.listaGrupos);
        adapter = new AdapterItemGrupo(getActivity(), arraydir);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(getActivity().getApplicationContext(), CapitanActivity.class);
                i.putExtra(Constants.GROUP_ID, arraydir.get(position).getId());
                i.putExtra("ROUTE_ID", arraydir.get(position).getRutaId());
                startActivity(i);

            }
        });
        tvNewGroup = (TextView) view.findViewById(R.id.tvNewGroup);
        tvNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), NewGroupActivity.class);
                getActivity().startActivityForResult(i, Constants.NUEVO_GRUPO);
            }
        });
        fill();
        return view;


    }


    public void fill() {
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        final String[] cred = dm.getCred();
        String url = Helper.getGruposUrl(cred[0]);


        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    if (error.equals("0")) {
                        arraydir.clear();
                        JSONArray array = root.getJSONArray("groups");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject aux = array.getJSONObject(i);
                            String captain = aux.getString("captain");
                            if (captain.equals(cred[0])) {
                                String picture = aux.getString("image");
                                String name = aux.getString("name");
                                String id = aux.getString("_id");
                                JSONObject auxRoute = aux.getJSONObject("route");
                                String rutaId = auxRoute.getString("_id");
                                String ruta = auxRoute.getString("name");
                                ItemGrupo grupo = new ItemGrupo(picture, name, ruta, id, rutaId);
                                arraydir.add(grupo);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
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
                return params;
            }
        };
        requestQueue.add(request);
    }
}