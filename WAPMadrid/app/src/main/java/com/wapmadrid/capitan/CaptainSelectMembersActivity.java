package com.wapmadrid.capitan;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CaptainSelectMembersActivity extends Activity {

    private GridView grid;
    private ProgressBar pgAmigosList;
    private ArrayList<ItemAmigo> arraydir;
    private AdapterItemAmigo adapter;
    private String groupID;
    private ArrayList<String> membersSelected;
    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captain_select_members);

        arraydir = new ArrayList<ItemAmigo>();

        grid = (GridView) findViewById(R.id.gridAmigos);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);

        adapter = new AdapterItemAmigo(this, arraydir);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(rutasListClickListener());

        membersSelected = new ArrayList<>();

        groupID = getIntent().getStringExtra(Constants.GROUP_ID);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(Constants.SELECTED_MEMBERS, membersSelected);
                setResult(RESULT_OK,i);
                finish();
            }
        });

        fill();
    }

    private AdapterView.OnItemClickListener rutasListClickListener() {

        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String member = arraydir.get(position).getId();
                if (!membersSelected.contains(member)){
                    membersSelected.add(member);
                    view.setSelected(true);
                } else {
                    membersSelected.remove(member);
                    view.setSelected(false);
                }
            }
        };
    }

    private void fill() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final DataManager dm = new DataManager(getApplicationContext());
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
