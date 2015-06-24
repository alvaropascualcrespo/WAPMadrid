package com.wapmadrid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private NetworkImageView imgGroup;

    private static final int RESULT_CROP = 3;

    private static int RESULT_LOAD_IMAGE = 1;

    String encodedImage = "";
    private String picture;
    private boolean update;

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
        imgGroup = (NetworkImageView) findViewById(R.id.imgGroup);

        imgGroup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

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
                if (checkEmptyFields()) {
                    String error_message = getResources().getString(R.string.fill_fields);
                    setErrorMsg(error_message);
                } else {
                    dataToSend.put("name", etNombreGrupo.getText().toString());
                    dataToSend.put("schedule", etHorario.getText().toString());
                    dataToSend.put("level", etNivel.getText().toString());
                    if (update) {
                        updateData();
                    } else {
                        sendData();
                    }
                }
            }
        });

        if (Constants.PARENT_INFO.equals(getIntent().getStringExtra(Constants.PARENT))){
            dataToSend.put("groupID", getIntent().getStringExtra(Constants.GROUP_ID));
            etNombreGrupo.setText(getIntent().getStringExtra("NombreGrupo"));
            etNivel.setText(getIntent().getStringExtra("Level"));
            etHorario.setText(getIntent().getStringExtra("Schedule"));
            tvRoute.setText(getIntent().getStringExtra("Ruta"));
            dataToSend.put("route", getIntent().getStringExtra("RutaID"));
            picture = getIntent().getStringExtra("Picture");
            update = true;
        } else {
            update = false;
            picture = Helper.getDefaultProfilePictureUrl();
        }
        RequestQueue requestQueueImagen = Volley.newRequestQueue(getApplicationContext());
        ImageLoader imageLoader = new ImageLoader(requestQueueImagen, new BitmapLRUCache());
        imgGroup.setImageUrl(picture, imageLoader);
        setWidthToParent();
    }

    private boolean checkEmptyFields() {
        return "".equals(etNombreGrupo.getText().toString()) ||
                "".equals(etHorario.getText().toString()) ||
                "".equals(etNivel.getText().toString()) ||
                "".equals(tvRoute.getText().toString());

    }

    private void crop(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setData(photoUri);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null){
                crop(selectedImage);
            }
        } else if (requestCode == RESULT_CROP && resultCode == Activity.RESULT_OK && data != null){

            Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap bm = extras.getParcelable("data");
                imgGroup.setImageBitmap(bm);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG,40,baos);
                // bitmap object
                byte[] byteImage_photo = baos.toByteArray();
                //generate base64 string of image
                encodedImage = Base64.encodeToString(byteImage_photo, Base64.DEFAULT);
                dataToSend.put("profileImage", encodedImage);
            }
        }else if (resultCode == RESULT_OK){
            if (requestCode == Constants.RESULT_SELECTED_ROUTE){
                String name = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                dataToSend.put("route",id);
                tvRoute.setText(name);
            }
        }
    }

    private void sendData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DataManager dm = new DataManager(this);
        String[] cred = dm.getCred();
        dataToSend.put("token", cred[1]);
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
                } catch(JSONException e) {
                    String error_message = getResources().getString(R.string.connection_error);
                    setErrorMsg(error_message);
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                String error_message = getResources().getString(R.string.connection_error);
                setErrorMsg(error_message);
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener)
        {
            @Override
            protected Map<String, String> getParams()
            {
                return dataToSend;
            }
        };
        requestQueue.add(request);

    }

    private void updateData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        DataManager dm = new DataManager(this);
        String[] cred = dm.getCred();
        dataToSend.put("token", cred[1]);
        String url = Helper.getUpdateGroupUrl(cred[0]);

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
                } catch(JSONException e) {
                    String error_message = getResources().getString(R.string.connection_error);
                    setErrorMsg(error_message);
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                String error_message = getResources().getString(R.string.connection_error);
                setErrorMsg(error_message);
            }
        };

        StringRequest request = new StringRequest(Request.Method.POST, url, succeedListener, errorListener)
        {
            @Override
            protected Map<String, String> getParams()
            {
                return dataToSend;
            }
        };
        requestQueue.add(request);

    }

    private void setErrorMsg(String error_message){

        Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
    }

    private void setWidthToParent() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        getWindow().setLayout(width, lyNewGroup.getLayoutParams().height);
    }

}
