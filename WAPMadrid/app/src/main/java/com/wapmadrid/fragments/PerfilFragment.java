package com.wapmadrid.fragments;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.centroMedico.CentroMedicoDescripcionFragment;
import com.wapmadrid.centroMedico.CentroMedicoEventosFragment;
import com.wapmadrid.miPerfil.MiPerfilActividadFragment;
import com.wapmadrid.miPerfil.MiPerfilDietaFragment;
import com.wapmadrid.miPerfil.MiPerfilEstadoFragment;
import com.wapmadrid.miPerfil.MiPerfilInfoFragment;
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment implements ActionBar.TabListener {

    private Fragment[] fragments = new Fragment[]{new MiPerfilInfoFragment(),
            new MiPerfilEstadoFragment(),
            new MiPerfilDietaFragment(),
            new MiPerfilActividadFragment()};
    private int lastIndex = 0;
    private RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);// -- linea original

        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction()
                .add(R.id.perfilLayout, fragments[0])
                .add(R.id.perfilLayout, fragments[1])
                .add(R.id.perfilLayout, fragments[2])
                .add(R.id.perfilLayout, fragments[3])
                .commit();

        manager.beginTransaction().hide(fragments[1])
                .hide(fragments[2])
                .hide(fragments[3])
                .commit();

        fill();
        return view;
    }

    public void setTabs() {

        final ActionBar actionBar = getActivity().getActionBar();

        actionBar.removeAllTabs();
        String[] perfil_tabs = getResources().getStringArray(R.array.perfil_tabs);
        for (int i = 0; i < perfil_tabs.length; i++)
            actionBar.addTab(actionBar.newTab()
                    .setText(perfil_tabs[i])
                    .setTabListener(this));
    }

    public void setContent(int index) {
        Fragment toHide = null;
        Fragment toShow = null;

        toHide = fragments[lastIndex];
        toShow = fragments[index];
        lastIndex = index;

        FragmentManager manager = getChildFragmentManager();

        manager.beginTransaction()
                .hide(toHide)
                .show(toShow)
                .commit();
    }

    @Override
    public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
        setContent(arg0.getPosition());

    }

    @Override
    public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    public void fill() {
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        String[] cred = dm.getCred();
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = Helper.getReadUrl(cred[0]);
        final String token = cred[1];


        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    if (error.equals("0")) {
                        JSONObject walker_json = root.getJSONObject("walker");
                        Walker walker = new Walker(walker_json);
                        ((MiPerfilInfoFragment) fragments[0]).setWalker(walker);
                        ((MiPerfilEstadoFragment) fragments[1]).setWalker(walker);
                        ((MiPerfilDietaFragment) fragments[2]).setWalker(walker);
                        ((MiPerfilActividadFragment) fragments[3]).setWalker(walker);
                    } else {
                        //TODO
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
                params.put("token", token);
                return params;
            }
        };

        requestQueue.add(request);

    }

}
