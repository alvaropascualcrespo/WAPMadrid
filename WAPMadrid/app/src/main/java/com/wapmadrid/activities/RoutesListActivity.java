package com.wapmadrid.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import android.widget.FrameLayout;
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
import com.wapmadrid.fragments.RutasListFragment;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoutesListActivity extends FragmentActivity {

    private EditText etSearchRoute;
    private FrameLayout lvRoutes;
    private RutasListFragment rutasListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);

        etSearchRoute = (EditText) findViewById(R.id.etSearchRoute);
        lvRoutes = (FrameLayout) findViewById(R.id.lvRoutes);
        rutasListFragment = new RutasListFragment();
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

        FragmentManager fm = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("CREATE_GROUP","CREATE_GROUP");
        rutasListFragment.setArguments(bundle);
        fm.beginTransaction().add(R.id.lvRoutes, rutasListFragment).commit();

    }

    public void selectedRuta(String id, String name){
        Intent i = new Intent();
        i.putExtra("name", name);
        i.putExtra("id", id);
        setResult(Activity.RESULT_OK, i);
        finish();

    }

}
