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
import com.wapmadrid.activities.GrupoActivity;
import com.wapmadrid.activities.InicioActivity;
import com.wapmadrid.activities.NewGroupActivity;
import com.wapmadrid.adapters.AdapterItemGrupo;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.data.ItemGrupo;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

public class GrupoListFragment extends Fragment {

    ArrayList<ItemGrupo> arraydir;
    AdapterItemGrupo adapter;
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
                Intent i = new Intent(getActivity().getApplicationContext(), GrupoActivity.class);
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

        return view;


    }


    public void fill() {
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        String[] cred = dm.getCred();
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
                        JSONArray array = root.getJSONArray("groups");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject auxArray = array.getJSONObject(i);
                            String capitan = auxArray.getString("rol");
                            if (capitan.equals("user")) {
                                JSONObject aux = auxArray.getJSONObject("groupID");
                                String picture = aux.getString("image");
                                String ruta = aux.getString("route");
                                long idProfile = Long.valueOf(aux.getString("_id"));
                                String name = aux.getString("name");
                                ItemGrupo grupo = new ItemGrupo(picture, name, ruta, idProfile);
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
                params.put("token", InicioActivity.TOKEN);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
