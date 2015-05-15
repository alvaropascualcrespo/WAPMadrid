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
import android.widget.GridView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.wapmadrid.R;
import com.wapmadrid.activities.AmigoActivity;
import com.wapmadrid.activities.InicioActivity;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

public class AmigosListFragment extends Fragment{

	ArrayList<ItemAmigo> arraydir;
	AdapterItemAmigo adapter;
    private ProgressBar pgAmigosList;
    private GridView grid;
    private RequestQueue requestQueue;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_amigos_list, container, false);
        grid = (GridView) v.findViewById(R.id.gridAmigos);
        pgAmigosList = (ProgressBar) v.findViewById(R.id.pgAmigosList);
        
        arraydir = new ArrayList<ItemAmigo>();
        adapter = new AdapterItemAmigo(getActivity(), arraydir);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity().getApplicationContext(), AmigoActivity.class);
				i.putExtra(AmigoActivity.ID, arraydir.get(position).getId());
				startActivity(i);
			}
        });
        return v;
    }

	public void fill() {
		final DataManager dm = new DataManager(getActivity().getApplicationContext());
		String[] cred = dm.getCred(); 
		String url = Helper.getAmigosUrl(cred[0]);


		Response.Listener<String> succeedListener = new Response.Listener<String>() 
				{
			@Override
			public void onResponse(String response) {
				// response
				Log.e("Response", response);
				try {
					JSONObject root = new JSONObject(response);
					String error = root.getString("error");
					if (error.equals("0")){
						JSONArray array = root.getJSONArray("friends");
						for (int i = 0; i < array.length(); i++){
							JSONObject auxArray = array.getJSONObject(i);
							String estado = auxArray.getString("state");
							if(estado.equals("accepted")){
								JSONObject aux = auxArray.getJSONObject("friendID");
								String picture = aux.getString("profileImage");
								long idProfile = Long.valueOf(aux.getString("_id"));
								String name = aux.getString("displayName");
								ItemAmigo friend = new ItemAmigo(picture,name,idProfile);
								arraydir.add(friend);
							}
						}
					}
					pgAmigosList.setVisibility(View.GONE);
					adapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Response.ErrorListener errorListener = new Response.ErrorListener() 
		{
			@Override
			public void onErrorResponse(VolleyError error) {
				// error
				Log.e("Error.Response", error.toString());
			}
		};

		StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener) 
		{     
			    @Override
			    protected Map<String, String> getParams() 
			    {  
			    	HashMap<String, String> params = new HashMap<String, String>();
					params.put("token", InicioActivity.TOKEN);
					return params;
			    }
		}; 
		requestQueue.add(request);
	}
	
}
