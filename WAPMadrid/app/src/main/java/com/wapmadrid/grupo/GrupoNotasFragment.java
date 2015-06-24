package com.wapmadrid.grupo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrupoNotasFragment extends Fragment {

    private ListView lvMensaje;
    private ArrayList<HashMap<String, String>> arraydir;
    private SimpleAdapter adapter;
    private EditText etMensaje;
    private Button btnEnviar;
    private String groupID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_capitan_mesajes, container, false);

        lvMensaje = (ListView) v.findViewById(R.id.lvMensaje);
        etMensaje = (EditText) v.findViewById(R.id.etMensaje);
        btnEnviar = (Button) v.findViewById(R.id.btnEnviar);

        arraydir = new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(), arraydir,
                R.layout.item_mensajes,
                new String[]{"line1", "line2"},
                new int[] {R.id.line_a, R.id.line_b});
        lvMensaje.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
                HashMap<String,String> aux = new HashMap<>();
                aux.put("line1",etMensaje.getText().toString());
                aux.put("line2","Yo");
                arraydir.add(aux);
                adapter.notifyDataSetChanged();
            }
        });
        groupID = getArguments().getString(Constants.GROUP_ID);
        fill();
        return v;
    }

    private void sendMessage() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        final String[] cred = dm.getCred();
        String url = Helper.getMessagesUrl(cred[0]);

        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    if (error.equals("0")) {
                        etMensaje.setText("");
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
                params.put("token", cred[1]);
                params.put("groupID", groupID);
                params.put("text", etMensaje.getText().toString());
                return params;
            }
        };
        requestQueue.add(request);
    }

    public void fill() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final DataManager dm = new DataManager(getActivity().getApplicationContext());
        final String[] cred = dm.getCred();
        String url = Helper.getMessagesUrl(cred[0]);

        Response.Listener<String> succeedListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject root = new JSONObject(response);
                    String error = root.getString("error");
                    if (error.equals("0")) {
                        JSONArray array = root.getJSONArray("messages");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject auxArray = array.getJSONObject(i);
                            String text = auxArray.getString("text");
                            String name = auxArray.getJSONObject("idSender").getString("displayName");
                            HashMap<String,String> aux = new HashMap<String, String>();
                            aux.put("line1",text);
                            aux.put("line2",name);
                            arraydir.add(aux);
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
                params.put("groupID", groupID);
                return params;
            }
        };
        requestQueue.add(request);
    }

}
