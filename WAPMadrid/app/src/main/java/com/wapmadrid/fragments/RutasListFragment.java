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
import com.wapmadrid.activities.NewGroupActivity;
import com.wapmadrid.activities.RouteActivity;
import com.wapmadrid.activities.RoutesListActivity;
import com.wapmadrid.adapters.AdapterItemRuta;
import com.wapmadrid.adapters.AdapterItemRutas;
import com.wapmadrid.data.ItemGrupo;
import com.wapmadrid.data.ItemRuta;
import com.wapmadrid.data.ItemRutasList;
import com.wapmadrid.rutas.CrearRutaViewActivity;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class RutasListFragment extends Fragment {

	ArrayList<ItemRuta> arraydir;
	AdapterItemRuta adapter;
    private ProgressBar pgRutasList;
    private ListView list;
	private TextView tvNewRoute;
	boolean create_group = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_rutas_list, container, false);
		arraydir = new ArrayList<ItemRuta>();
        list = (ListView) v.findViewById(R.id.listaRutas);
		adapter = new AdapterItemRuta(this, getActivity(), arraydir);
		list.setAdapter(adapter);
		pgRutasList = (ProgressBar) v.findViewById(R.id.pgRutasList);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (create_group){
					ItemRuta itemRuta = arraydir.get(position);
					((RoutesListActivity)getActivity()).selectedRuta(itemRuta.getId(),itemRuta.getName());
				}
			}
		});
		if (getArguments() != null)
			create_group = getArguments().get("CREATE_GROUP") != null;
		if (!create_group) {
			tvNewRoute = (TextView) v.findViewById(R.id.tvNewRoute);
			ImageView imNewRoute = (ImageView) v.findViewById(R.id.imNewRoute);
			imNewRoute.setVisibility(View.VISIBLE);
			tvNewRoute.setVisibility(View.VISIBLE);
			tvNewRoute.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity().getApplicationContext(), CrearRutaViewActivity.class);
					startActivity(i);
				}
			});
		}
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (create_group){
					((RoutesListActivity) getActivity()).selectedRuta(arraydir.get(position).getId(),arraydir.get(position).getName());
				} else {
					Intent i = new Intent(getActivity().getApplicationContext(), RouteActivity.class);
					i.putExtra("ROUTE_ID", arraydir.get(position).getId());
					startActivity(i);
				}
			}
		});
		fill();
        return v;
    }


	public void fill() {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
		final DataManager dm = new DataManager(getActivity().getApplicationContext());
		final String[] cred = dm.getCred();
		String url = Helper.getWalkerRoutesUrl(cred[0]);


		Response.Listener<String> succeedListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// response
				Log.e("Response", response);
				try {
					JSONObject root = new JSONObject(response);
					String error = root.getString("error");
					if (error.equals("0")) {
						JSONArray array = root.getJSONArray("routes");
						arraydir.clear();
							for (int i = 0; i < array.length(); i++) {
								JSONObject ruta = array.getJSONObject(i);
								try {
									arraydir.add(new ItemRuta(ruta.getString("_id"), ruta.getString("name"), null));
								} catch (Exception e){
									e.printStackTrace();
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
