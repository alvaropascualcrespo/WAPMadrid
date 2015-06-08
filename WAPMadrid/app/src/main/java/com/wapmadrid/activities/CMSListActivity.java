package com.wapmadrid.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.activities.RoutesListActivity;
import com.wapmadrid.adapters.AdapterItemCMS;
import com.wapmadrid.adapters.AdapterItemRuta;
import com.wapmadrid.data.ItemCMS;
import com.wapmadrid.data.ItemRuta;
import com.wapmadrid.miPerfil.MiPerfilActividadFragment;
import com.wapmadrid.miPerfil.MiPerfilInfoFragment;
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CMSListActivity extends Activity {


    ArrayList<ItemCMS> arraydir;
    AdapterItemCMS adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmslist);

        arraydir = new ArrayList<>();
        list = (ListView) findViewById(R.id.listaCMS);
        adapter = new AdapterItemCMS(this, arraydir);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setCMS(arraydir.get(position).getId());
            }
        });
        getData();
    }

    private void setCMS(final String cmsID) {
        final DataManager dm = new DataManager(getApplicationContext());
        String[] cred = dm.getCred();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Helper.getCMSUrl(cred[0]);
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
                        Intent i = new Intent();
                        setResult(RESULT_OK,i);
                        finish();
                    } else {
                        String msg = root.getString("error_message");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
        StringRequest request = new StringRequest(Request.Method.PUT, url, succeedListener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("cms", cmsID);
                return params;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Por favor, selecciona un centro de Madrid Salud",Toast.LENGTH_SHORT).show();
    }

    public void getData() {
        final DataManager dm = new DataManager(getApplicationContext());
        String[] cred = dm.getCred();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Helper.getCMSListUrl(cred[0]);
        final String token = cred[1];


        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    JSONArray array = root.getJSONArray("users");
                    if (error.equals("0")) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject userAux = array.getJSONObject(i);
                            ItemCMS aux = new ItemCMS(userAux.getString("image"), userAux.getString("name"), userAux.getString("_id"));
                            arraydir.add(aux);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        String msg = root.getString("error_message");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
