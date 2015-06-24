package com.wapmadrid.miPerfil.editarPerfil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EditInfoActivity extends Activity {

    private EditText etRegistroNombre;
    private EditText etRegistroApellidos;
    private EditText etEmail;
    private EditText etTelephone;
    private EditText etAddress;
    private NetworkImageView imgAmigo;
    private Button btnEnviarInfo;
    HashMap<String,String> dataToSend;
    private EditText etCity;

    private static final int RESULT_CROP = 3;

    private static int RESULT_LOAD_IMAGE = 1;

    String encodedImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        dataToSend = new HashMap<>();
        imgAmigo = (NetworkImageView) findViewById(R.id.imgAmigo);
        etRegistroNombre = (EditText) findViewById(R.id.etRegistroNombre);
        etRegistroApellidos = (EditText) findViewById(R.id.etRegistroApellidos);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etTelephone = (EditText) findViewById(R.id.etTelephone);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById(R.id.etCity);

        btnEnviarInfo = (Button) findViewById(R.id.btnEnviarInfo);

        Walker walker = getIntent().getParcelableExtra("WALKER");

        String firstName = walker.getFirstName();
        etRegistroNombre.setHint(firstName);
        String lastName = walker.getLastName();
        etRegistroApellidos.setHint(lastName);
        String email = walker.getEmail();
        etEmail.setHint(email);
        String address = walker.getAddress();
        if (!address.equals("")){
            etAddress.setHint(address);
        }
        String city = walker.getCity();
        if (!city.equals("")){
           etCity.setHint(city);
        }
        String telephone = walker.getTelephone();
        if (!telephone.equals("")){
            etTelephone.setHint(telephone);
        }

        imgAmigo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        RequestQueue requestQueueImagen = Volley.newRequestQueue(getApplicationContext());
        ImageLoader imageLoader = new ImageLoader(requestQueueImagen, new BitmapLRUCache());
        imgAmigo.setImageUrl(walker.getProfileImage(), imageLoader);

        dataToSend.put("firstName", firstName);
        dataToSend.put("lastName", lastName);
        dataToSend.put("email", email);
        dataToSend.put("address", address);
        dataToSend.put("city", city);
        dataToSend.put("telephone", telephone);

        btnEnviarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean send = true;

                String name_edit = etRegistroNombre.getText().toString();
                if (!name_edit.equals(""))
                    dataToSend.put("firstName", name_edit);
                String last_edit = etRegistroApellidos.getText().toString();
                if (!last_edit.equals(""))
                    dataToSend.put("lastName", last_edit);
                String email_edit = etEmail.getText().toString();
                if (!email_edit.equals(""))
                    dataToSend.put("email", email_edit);
                String address_edit = etAddress.getText().toString();
                if (!address_edit.equals(""))
                    dataToSend.put("address", address_edit);
                String city_edit = etCity.getText().toString();
                if (!city_edit.equals(""))
                    dataToSend.put("city", city_edit);
                String telephone_edit = etTelephone.getText().toString();
                if (!telephone_edit.equals(""))
                    dataToSend.put("telephone", telephone_edit);
                sendData();
            }
        });




    }

    private void sendData() {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            DataManager dm = new DataManager(this);
            final String[] cred = dm.getCred();
            String url = Helper.getUpdateInfoUrl(cred[0]);

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
                            Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT).show();
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



                imgAmigo.setImageBitmap(bm);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                bm.compress(Bitmap.CompressFormat.JPEG,40,baos);


                // bitmap object

                byte[] byteImage_photo = baos.toByteArray();

                //generate base64 string of image

                encodedImage = Base64.encodeToString(byteImage_photo, Base64.DEFAULT);
                dataToSend.put("profileImage", encodedImage);
            }
        }
    }
}
