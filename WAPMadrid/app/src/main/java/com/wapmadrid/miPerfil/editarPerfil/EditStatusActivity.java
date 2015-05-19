package com.wapmadrid.miPerfil.editarPerfil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditStatusActivity extends Activity {

    private EditText etPeso;
    private EditText etAltura;
    private Spinner spFumador;
    private Spinner spAlcohol;
    private Button btnEnviar;
    HashMap<String,String> dataToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_status);

        dataToSend = new HashMap<>();
        etPeso = (EditText) findViewById(R.id.etPeso);
        etAltura = (EditText) findViewById(R.id.etAltura);
        etAltura = (EditText) findViewById(R.id.etAltura);
        spFumador = (Spinner) findViewById(R.id.spFumador);
        spAlcohol = (Spinner) findViewById(R.id.spAlcohol);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);

        Walker walker = getIntent().getParcelableExtra("WALKER");

        String alturaRes = getResources().getString(R.string.altura);
        String pesoRes = getResources().getString(R.string.peso);
        String height = walker.getHeight();
        int size = walker.getWeight_value().size();
        String weight = "0";
        if (size > 0)
            weight = walker.getWeight_value().get(size - 1);
        String smoker = walker.getSmoker();
        String alcohol = walker.getAlcohol();
        etAltura.setHint(String.format(alturaRes, height));
        etPeso.setHint(String.format(pesoRes, weight));

        dataToSend.put("weight", weight);
        dataToSend.put("height", height);
        dataToSend.put("smoker", smoker);
        dataToSend.put("alcohol", alcohol);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean send = true;

                String peso = etPeso.getText().toString();
                String altura = etAltura.getText().toString();
                String smoker_sel = String.valueOf(spFumador.getSelectedItemPosition());
                String alcohol_sel = String.valueOf(spAlcohol.getSelectedItemPosition());

                try{
                    Integer.valueOf(peso);
                }catch (Exception e){
                    send = false;
                }
                try{
                    Float.valueOf(altura);
                }catch (Exception e){
                    send = false;
                }

                if (send){
                    dataToSend.put("weight", peso);
                    dataToSend.put("height", altura);
                    dataToSend.put("smoker", smoker_sel);
                    dataToSend.put("alcohol", alcohol_sel);
                    sendData();
                } else {
                    Toast.makeText(getApplicationContext(), "Los datos introducidos no son correctos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayAdapter<CharSequence> adapterSmoker = ArrayAdapter.createFromResource(this,
                R.array.smoker_array, android.R.layout.simple_spinner_item);
        adapterSmoker.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spFumador.setAdapter(adapterSmoker);
        ArrayAdapter<CharSequence> adapterAlcohol = ArrayAdapter.createFromResource(this,
                R.array.alcohol_array, android.R.layout.simple_spinner_item);
        adapterAlcohol.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spAlcohol.setAdapter(adapterAlcohol);
        try {
            spFumador.setSelection(Integer.valueOf(smoker));
            spAlcohol.setSelection(Integer.valueOf(alcohol));
        } catch (Exception e){}


    }

    private void sendData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DataManager dm = new DataManager(this);
        final String[] cred = dm.getCred();
        String url = Helper.getUpdateStatusUrl(cred[0]);

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
                        finish();
                        Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
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
                .setTitle("Error en el registro")
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

}
