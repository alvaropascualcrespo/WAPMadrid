package com.wapmadrid.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import android.view.View;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;

public class AmigoPeticionActivity extends Activity{

	protected static final String ID = null;
	private RequestQueue requestQueue;
	private ProgressBar pgAmigosList;
	ArrayList<ItemAmigo> arraydir;
	AdapterItemAmigo adapter;
	GridView gridAmigos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_amigos_list);

		pgAmigosList = (ProgressBar) findViewById(R.id.pgAmigosList);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher);
		requestQueue = Volley.newRequestQueue(getApplicationContext());
		gridAmigos = (GridView) findViewById(R.id.gridAmigos);
		arraydir = new ArrayList<ItemAmigo>();    
		adapter = new AdapterItemAmigo(this, arraydir);
		gridAmigos.setAdapter(adapter);

		gridAmigos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
				Intent intent = new Intent(AmigoPeticionActivity.this, AmigoPeticionActivity.class);
				intent.putExtra(AmigoPeticionActivity.ID, String.valueOf(adapter.getItemId(position)));
				startActivity(intent);
			}
		});

		getFriendshipRequest();

		/*ListView listaMensajes = (ListView) findViewById(R.id.gridAmigos);
		arraydir2 = new ArrayList<ItemAmigo>();
		adapter2 = new AdapterItemAmigoList(this, arraydir2);
		listaMensajes.setAdapter(adapter2);

		listaMensajes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Intent intent = new Intent(AmigoPeticionActivity.this, MensajesViewActivity.class);
				intent.putExtra(MensajesViewActivity.ID, String.valueOf(adapter2.getItemId(position)));
				intent.putExtra(MensajesViewActivity.NAME, ((ItemAmigo)adapter2.getItem(position)).getName() );
				startActivity(intent);
			}

		});

		getNewMessages();*/

	}


	/*private void getNewMessages() {
		pgAmigosList.setVisibility(View.VISIBLE);
		final DataManager dm = new DataManager(getApplicationContext());
		String[] cred = dm.getCred();
		String url = Helper.getFriendsMessagesUrl() + "/" + cred[0] + "/" + cred[1];
		Log.e("Pido Mensajes",url);

		Response.Listener<String> succeedListener = new Response.Listener<String>() 
				{
			@Override
			public void onResponse(String response) {
				// response
				Log.e("Response new message", response);
				try {
					JSONArray root = new JSONArray(response);
					for (int i = 0; i < root.length(); i++){
						JSONObject aux = root.getJSONObject(i);
						int num = Integer.valueOf(aux.getString("num"));
						JSONObject aux2 = aux.getJSONObject(String.valueOf(num - 1));
						String mode = "1";
						String modeRec = aux2.getString("mode");
						if (modeRec.equals(mode)){
							long idProfile = Long.valueOf(aux.getString("idProfile"));
							String picture = aux.getString("picture");
							picture = picture.replace("\\", "");
							String name = aux.getString("name") + " " +  aux.getString("surnames");
							ItemAmigo friend = new ItemAmigo(picture,name,idProfile);
							arraydir2.add(friend);
						}
					}
					connectionProgressDialog.dismiss();
					adapter2.notifyDataSetChanged();
					connectionProgressDialog.dismiss();
				} catch (Exception e) {
					connectionProgressDialog.dismiss();
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

				StringRequest request = new StringRequest(Request.Method.GET, url, succeedListener, errorListener); 

				requestQueue.add(request);
	}*/




	private void getFriendshipRequest() {
		final DataManager dm = new DataManager(getApplicationContext());
		String[] cred = dm.getCred(); 
		String url = Helper.getPeticionesAmigosUrl(cred[0]);
		final String token = cred[1];

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
							if(estado.equals("request")){
								JSONObject aux = auxArray.getJSONObject("friendID");
								String picture = aux.getString("profileImage");
								String idProfile = aux.getString("_id");
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
					params.put("token", token);
					return params;
			    }
		}; 
		requestQueue.add(request);
	}

	@Override
	public Intent getParentActivityIntent() {
		Intent intent = new Intent(this, MainActivity.class);
		return intent;
	}

}
