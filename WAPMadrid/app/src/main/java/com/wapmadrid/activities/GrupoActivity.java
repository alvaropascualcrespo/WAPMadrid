package com.wapmadrid.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.R.id;
import com.wapmadrid.R.layout;
import com.wapmadrid.R.menu;
import com.wapmadrid.adapters.GrupoPageAdapter;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class GrupoActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this
     * becomes too memory intensive, it may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */

    GrupoPageAdapter grupoPageAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private String groupID;
    private String routeID;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gupo);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setIcon(R.drawable.action_bar_negro);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTabReselected(Tab tab, FragmentTransaction ft) {
                // TODO Auto-generated method stub

            }
        };
        groupID = getIntent().getStringExtra(Constants.GROUP_ID);
        routeID = getIntent().getStringExtra("ROUTE_ID");

        String[] grupo_tabs = getResources().getStringArray(R.array.grupo_tabs);
        for (int i = 0; i < grupo_tabs.length; i++)
            actionBar.addTab(actionBar.newTab()
                    .setText(grupo_tabs[i])
                    .setTabListener(tabListener));
        grupoPageAdapter = new GrupoPageAdapter(this, getSupportFragmentManager(), groupID, routeID);

        // Set up the ViewPager with the sections adapter.

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        mViewPager.setAdapter(grupoPageAdapter);

        final int abTitleId = getResources().getIdentifier("action_bar_title", "id", "android");
        findViewById(abTitleId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                Intent i = new Intent(this, InicioActivity.class);
                startActivity(i);
                break;
            case id.abandonarGrupo:
                abandonarGrupo();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grupo, menu);
        return true;
    }

    private void abandonarGrupo() {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            final DataManager dm = new DataManager(this);
            final String[] cred = dm.getCred();
            String url = Helper.getLeaveGrupoUrl(cred[0]);


            Response.Listener<String> succeedListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // response
                    Log.e("Response", response);
                    try {
                        JSONObject root = new JSONObject(response);
                        String error = root.getString("error");
                        if (error.equals("0")) {
                            Intent i = new Intent(getApplicationContext(), InicioActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        } else {
                            String error_message = root.getString("error_message");
                            Toast.makeText(getApplicationContext(),error_message,Toast.LENGTH_SHORT).show();
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
                    String error_message = getResources().getString(R.string.connection_error);
                    Toast.makeText(getApplicationContext(),error_message,Toast.LENGTH_SHORT).show();
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
