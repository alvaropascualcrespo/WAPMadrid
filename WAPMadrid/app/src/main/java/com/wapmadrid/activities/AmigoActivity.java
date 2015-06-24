package com.wapmadrid.activities;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.miPerfil.MiPerfilActividadFragment;
import com.wapmadrid.miPerfil.MiPerfilDietaFragment;
import com.wapmadrid.miPerfil.MiPerfilEstadoFragment;
import com.wapmadrid.miPerfil.MiPerfilInfoFragment;
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.rutaIndividual.RutaDescripcionFragment;
import com.wapmadrid.rutaIndividual.RutaMapFragment;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AmigoActivity extends FragmentActivity implements ActionBar.TabListener {

    public static final String ID = "id";
    private Fragment[] fragments = new Fragment[]{new MiPerfilInfoFragment(),
            new MiPerfilActividadFragment()};

    private int lastIndex = 0;
    private String walkerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo);
        walkerID = getIntent().getStringExtra(Constants.WALKER_ID);
        String[] tabs = getResources().getStringArray(R.array.amigo_tabs);
        final ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.action_bar_negro);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 0; i </*tabs.length*/2; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(tabs[i])
                            .setTabListener(this));
        }
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fgAmigoLayout, fragments[0])
                .add(R.id.fgAmigoLayout, fragments[1])
                .commit();

        manager.beginTransaction().hide(fragments[1])
                .commit();

        fill();

        final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");
        findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
            }
        });
        setContent(0);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
            switch (itemId) {
            case android.R.id.home:
                    Intent i = new Intent(this, InicioActivity.class);
                    startActivity(i);
                    break;
        }
        return true;
    }

    private void setContent(int index) {
        Fragment toHide = null;
        Fragment toShow = null;

        toHide = fragments[lastIndex];
        toShow = fragments[index];
        lastIndex = index;

        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction()
                .hide(toHide)
                .show(toShow)
                .commit();


        //	if (index == 0) ((MiPerfilInfoFragment) toShow).fill();
        if (index == 1) ((MiPerfilActividadFragment) toShow).fill();

    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
        setContent(tab.getPosition());

    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }
    public void fill() {
        final DataManager dm = new DataManager(getApplicationContext());
        String[] cred = dm.getCred();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                        ((MiPerfilInfoFragment) fragments[0]).fromFriend();
                        ((MiPerfilActividadFragment) fragments[1]).setWalker(walker);
                    } else {
                        String msg = root.getString("   error_message");
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
                params.put("walkerID", walkerID);
                return params;
            }
        };

        requestQueue.add(request);

    }

}
