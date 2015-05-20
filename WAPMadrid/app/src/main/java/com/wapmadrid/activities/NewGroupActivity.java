package com.wapmadrid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewGroupActivity extends Activity {

    private LinearLayout lyNewGroup;
    private TextView tvRoute;
    private EditText etNombreGrupo;
    private EditText etHorario;
    private EditText etNivel;
    private HashMap<String,String> dataToSend;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        dataToSend = new HashMap<>();

        lyNewGroup = (LinearLayout) findViewById(R.id.lyNewGroup);

        tvRoute = (TextView) findViewById(R.id.tvRoute);
        etNombreGrupo = (EditText) findViewById(R.id.etNombreGrupo);
        etHorario = (EditText) findViewById(R.id.etHorario);
        etNivel = (EditText) findViewById(R.id.etNivel);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        tvRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RoutesListActivity.class);
                startActivityForResult(intent, Constants.RESULT_SELECTED_ROUTE);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataToSend.put("name", etNombreGrupo.getText().toString());
                dataToSend.put("schedule", etHorario.getText().toString());
                dataToSend.put("level", etNivel.getText().toString());
                dataToSend.put("image", "");
                sendData();
            }
        });

        setWidthToParent();
    }

    private void sendData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DataManager dm = new DataManager(this);
        final String[] cred = dm.getCred();
        String url = Helper.getCreateGroupUrl(cred[0]);

        Response.Listener<String> succeedListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject respuesta = new JSONObject(response);
                    String error = respuesta.getString("error");
                    if (error.equals("0")) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        Toast.makeText(getApplicationContext(), "Creado", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        String error_message = respuesta.getString("error_message");
                        setErrorMsg(error_message);
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

        StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener)
        {
            @Override
            protected Map<String, String> getParams()
            {
                dataToSend.put("token", cred[1]);
                return dataToSend;
            }
        };

        requestQueue.add(request);

    }

    private void setErrorMsg(String string){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(string)
                .setTitle("Error al crear un grupo")
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        //register_error.setText(string);
    }

    private void setWidthToParent() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        getWindow().setLayout(width, lyNewGroup.getLayoutParams().height);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == Constants.RESULT_SELECTED_ROUTE){
                String name = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                dataToSend.put("route",id);
                tvRoute.setText(name);
            }
        }
    }
}
