package com.wapmadrid.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItem;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.adapters.AdapterItemEvent;
import com.wapmadrid.data.Item;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.data.ItemEvent;
import com.wapmadrid.data.ItemRuta;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    ArrayList<ItemEvent> arraydir;
    AdapterItemEvent adapter;
    private ListView list;
    private ProgressBar pgHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        list = (ListView) v.findViewById(R.id.listaHome);
        arraydir = new ArrayList<>();
        adapter = new AdapterItemEvent(getActivity(), arraydir);
        list.setAdapter(adapter);
        fill();
        return v;
    }

    public void fill() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        final String[] cred = dm.getCred();
        String url = Helper.getEventsUrl();


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
                        JSONArray array = root.getJSONArray("events");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject aux = array.getJSONObject(i);
                                arraydir.add(new ItemEvent(aux.getString("name"),aux.getString("text"),aux.getString("date")));
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

        StringRequest request = new StringRequest(Request.Method.GET, url, succeedListener, errorListener);
        requestQueue.add(request);
    }

}
