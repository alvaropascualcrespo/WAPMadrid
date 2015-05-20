package com.wapmadrid.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoutesListActivity extends Activity {

    private EditText etSearchRoute;
    ArrayList<HashMap<String, String>> routesArray;
    private ListView lvRoutes;
    private SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);

        etSearchRoute = (EditText) findViewById(R.id.etSearchRoute);
        lvRoutes = (ListView) findViewById(R.id.lvRoutes);
        routesArray = new ArrayList<>();

        etSearchRoute.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });




        sa = new SimpleAdapter(this, routesArray,
                R.layout.item_routes_activity,
                new String[]{"name", "id"},
                new int[]{R.id.tvRouteName, R.id.tvRouteId});
        lvRoutes.setAdapter(sa);

        lvRoutes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent();
                i.putExtra("name", routesArray.get(position).get("name"));
                i.putExtra("id", routesArray.get(position).get("id"));
                setResult(Activity.RESULT_OK, i);
                finish();

            }
        });
        fillData();

    }

    private void fillData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DataManager dm = new DataManager(this);
        String url = Helper.getGetRoutesUrl();

        Response.Listener<String> succeedListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    sa.notifyDataSetInvalidated();
                    JSONObject respuesta = new JSONObject(response);
                    String error = respuesta.getString("error");
                    if (error.equals("0")) {
                        HashMap<String, String> item;
                        JSONArray routesJArray = respuesta.getJSONArray("routes");
                        for(int i = 0; i < routesJArray.length(); i++) {
                            JSONObject aux = routesJArray.getJSONObject(i);
                            item = new HashMap<String, String>();
                            item.put("name", aux.getString("name"));
                            item.put("id",  aux.getString("_id"));
                            routesArray.add(item);
                        }
                        sa.notifyDataSetChanged();
                    }

                } catch(JSONException e) {}
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());
            }
        };

        StringRequest request = new StringRequest(Request.Method.GET, url, succeedListener, errorListener);


        requestQueue.add(request);

    }

}
