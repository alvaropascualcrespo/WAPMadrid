package com.wapmadrid.miPerfil.cuestionario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.activities.InicioActivity;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ismael on 18/05/2015.
 */
public class CuestionarioActivity  extends FragmentActivity {


    private CuestionarioPageAdapter pageAdapterCuestionario;
    private ViewPager cuestionarioViewPager;
    private int[] respuestas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        pageAdapterCuestionario = new CuestionarioPageAdapter(this, getSupportFragmentManager());

        cuestionarioViewPager = (ViewPager) findViewById(R.id.cuestionarioViewPager);
        cuestionarioViewPager.setAdapter(pageAdapterCuestionario);

        cuestionarioViewPager.setCurrentItem(0);
        cuestionarioViewPager.setOffscreenPageLimit(1);

        respuestas = new int[14];
        for (int i = 0; i < respuestas.length; i++){
            respuestas[i] = -1;
        }

        setWidthToParent();
    }

    private void setWidthToParent() {
        ViewGroup.LayoutParams rlConfigParams = cuestionarioViewPager.getLayoutParams();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        getWindow().setLayout(width, rlConfigParams.height);
    }


    public void setRespuesta(int position, int i) {
        cuestionarioViewPager.setCurrentItem(position + 1);
        respuestas[position] = i;
        checkFinished();
    }

    private void checkFinished() {
        boolean finished = true;
        int result = 0;
        int i = 0;
        while( i < respuestas.length && finished){
            finished = respuestas[i] == 0 || respuestas[i] == 1;
            result += respuestas[i];
            i++;
        }
        if (finished){
            sendDieta(String.valueOf(result));
        }
    }

    private void sendDieta(final String result) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DataManager dm = new DataManager(this);
        final String[] cred = dm.getCred();
        String url = Helper.getUpdateDietaUrl(cred[0]);

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
                        intent.putExtra("value", result);
                        setResult(Activity.RESULT_OK, intent);
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
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("token", cred[1]);
                params.put("diet", result);
                return params;
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
