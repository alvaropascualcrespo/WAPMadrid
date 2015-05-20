package com.wapmadrid.grupo;

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
import com.wapmadrid.activities.AmigoActivity;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;
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
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class GrupoMiembrosFragment extends Fragment{

	private GridView grid;
	private ProgressBar pgAmigosList;
	private ArrayList<ItemAmigo> arraydir;
	private AdapterItemAmigo adapter;
	private String groupID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_amigos_list, container, false);
        grid = (GridView) v.findViewById(R.id.gridAmigos);
        pgAmigosList = (ProgressBar) v.findViewById(R.id.pgAmigosList);
        
        arraydir = new ArrayList<ItemAmigo>();

        adapter = new AdapterItemAmigo(getActivity(), arraydir);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(rutasListClickListener());

		groupID = getArguments().getString(Constants.GROUP_ID);

		fill();

        return v;
    }
	
	private OnItemClickListener rutasListClickListener() {
		
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity().getApplicationContext(), AmigoActivity.class);
				i.putExtra(Constants.WALKER_ID,arraydir.get(position).getId());
				startActivity(i);
			}
		};
	}

	private void fill() {
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
		final DataManager dm = new DataManager(getActivity().getApplicationContext());
		final String[] cred = dm.getCred();
		String url = Helper.getReadGrupoMembersUrl(cred[0]);


		Response.Listener<String> succeedListener = new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// response
				Log.e("Response", response);
				try {
					JSONObject root = new JSONObject(response);
					String error = root.getString("error");
					if (error.equals("0")) {
						JSONArray membersArray = root.getJSONArray("members");
						arraydir.clear();
						for (int i = 0; i < membersArray.length(); i++){
							JSONObject member = membersArray.getJSONObject(i);
							JSONObject walker = member.getJSONObject("idMember");
							arraydir.add(new ItemAmigo(walker.getString("profileImage"), walker.getString("displayName"), walker.getString("_id")));
						}
						adapter.notifyDataSetChanged();
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
