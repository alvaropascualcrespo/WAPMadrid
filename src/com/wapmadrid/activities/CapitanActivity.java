package com.wapmadrid.activities;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemLayoutCapitan;
import com.wapmadrid.capitan.CederCapitaniaViewActivity;
import com.wapmadrid.capitan.ComenzarRutaViewActivity;
import com.wapmadrid.capitan.CrearRutaViewActivity;
import com.wapmadrid.capitan.MensajesCapitanViewActivity;
import com.wapmadrid.data.ItemLayoutCapitan;

public class CapitanActivity extends Activity{

	ArrayList<ItemLayoutCapitan> arraydir;
	AdapterItemLayoutCapitan adapter;
	GridView lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capitan);
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setIcon(R.drawable.action_bar_negro);
		lista = (GridView) findViewById(R.id.gridViewCapitan);
        arraydir = new ArrayList<ItemLayoutCapitan>();       
	    adapter = new AdapterItemLayoutCapitan(this, arraydir);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				switch(position){
					case 0:
						Intent intent1 = new Intent(getApplicationContext(), MensajesCapitanViewActivity.class);
		                //intent.putExtra(MensajesCapitanViewActivity.ID, String.valueOf(adapter.getItemId(position)));
		                startActivity(intent1);
		                break;
					
					case 1:
						Intent intent2 = new Intent(getApplicationContext(), ComenzarRutaViewActivity.class);
		                //intent.putExtra(ComenzarRutaViewActivity.ID, String.valueOf(adapter.getItemId(position)));
		                startActivity(intent2);
		                break;
		                
					case 2:
						Intent intent3 = new Intent(getApplicationContext(), CrearRutaViewActivity.class);
		                //intent.putExtra(CrearRutaViewActivity.ID, String.valueOf(adapter.getItemId(position)));
		                startActivity(intent3);
		                break;
		                
					case 3:
						Intent intent4 = new Intent(getApplicationContext(), CederCapitaniaViewActivity.class);
		                //intent.putExtra(CederCapitaniaViewActivity.ID, String.valueOf(adapter.getItemId(position)));
		                startActivity(intent4);
		                break;
		            
		            default:
		            	Toast.makeText(getApplicationContext(), "Error en la posicion", Toast.LENGTH_LONG).show();
		            	break;
				}
			}		
		});
        fill();		
	}

	private void fill() {
		ItemLayoutCapitan i1 = new ItemLayoutCapitan(R.drawable.icono_mensajes,"Mensajes",0);
    	arraydir.add(i1);
    	ItemLayoutCapitan i2 = new ItemLayoutCapitan(R.drawable.icono_empezar_ruta,"Comenzar Ruta",1);
    	arraydir.add(i2);
    	ItemLayoutCapitan i3 = new ItemLayoutCapitan(R.drawable.icono_crear_ruta,"Nueva Ruta",2);
    	arraydir.add(i3);
    	ItemLayoutCapitan i4 = new ItemLayoutCapitan(R.drawable.icono_ceder_capitania,"Ceder Capitania",3);
    	arraydir.add(i4);
    	adapter.notifyDataSetChanged();		
	}
	
}
