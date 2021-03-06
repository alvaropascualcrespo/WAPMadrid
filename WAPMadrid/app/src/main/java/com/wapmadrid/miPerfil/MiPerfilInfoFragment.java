package com.wapmadrid.miPerfil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemAmigo;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.miPerfil.editarPerfil.EditInfoActivity;
import com.wapmadrid.modelos.Walker;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.Constants;
import com.wapmadrid.utilities.DataManager;
import com.wapmadrid.utilities.Helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MiPerfilInfoFragment extends Fragment{

    private ProgressBar pgAmigoView;

	private TextView txtCiudad;
	private TextView txtSobreMi;
	private TextView txtNombreyApellidos;
	private Button btnMessage;
	private NetworkImageView imgAmigo;
	private Button btnAceptar;
	private Button btnRechazar;
	private Walker walker;
	private TextView txtEmail;
	private TextView txtPhone;
	private Button btnEditProfile;
	private TextView txtAddress;

	public void setWalker(Walker walker) {
		this.walker = walker;
		txtCiudad.setText(walker.getCity());
		txtNombreyApellidos.setText(walker.getDisplayName());
		txtSobreMi.setText(walker.getAbout());
		txtEmail.setText(walker.getEmail());
		txtPhone.setText(walker.getTelephone());
		txtAddress.setText(walker.getAddress());

		RequestQueue requestQueueImagen = Volley.newRequestQueue(getActivity().getApplicationContext());
		ImageLoader imageLoader = new ImageLoader(requestQueueImagen, new BitmapLRUCache());
		imgAmigo.setImageUrl(walker.getProfileImage(), imageLoader);
	}

	public void fromFriend(){
		btnEditProfile.setVisibility(View.GONE);
		getActivity().getActionBar().setTitle(walker.getDisplayName());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.amigo_info_fragment, container, false);
        pgAmigoView = (ProgressBar)v.findViewById(R.id.pgAmigoView);
        txtCiudad = (TextView)v.findViewById(R.id.txtCiudad);
        txtSobreMi = (TextView)v.findViewById(R.id.txtSobreMi);
		txtSobreMi.setVisibility(View.GONE);
        txtNombreyApellidos = (TextView)v.findViewById(R.id.txtNombreyApellidos);
		txtEmail = (TextView)v.findViewById(R.id.txtEmail);
		txtPhone = (TextView)v.findViewById(R.id.txtPhone);
		txtAddress = (TextView)v.findViewById(R.id.txtAddress);
        btnMessage = (Button)v.findViewById(R.id.btnMessage);
        btnAceptar = (Button)v.findViewById(R.id.btnAceptar);
        btnRechazar = (Button)v.findViewById(R.id.btnRechazar);
		btnEditProfile = (Button)v.findViewById(R.id.btnEditProfile);
        imgAmigo = (NetworkImageView)v.findViewById(R.id.imgAmigo);
		btnMessage.setVisibility(View.GONE);
		btnAceptar.setVisibility(View.GONE);
		btnRechazar.setVisibility(View.GONE);
		btnEditProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), EditInfoActivity.class);
				intent.putExtra("WALKER", walker);
				startActivityForResult(intent, Constants.RESULT_EDIT);
			}
		});
        return v;
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK){
			if (requestCode == Constants.RESULT_EDIT){
				getActivity().recreate();
			}
		}
	}
}
