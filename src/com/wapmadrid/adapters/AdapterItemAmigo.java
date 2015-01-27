package com.wapmadrid.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.adapters.AdapterItemEvento.ViewHolderEvento;
import com.wapmadrid.data.ItemAmigo;
import com.wapmadrid.data.ItemEvento;
import com.wapmadrid.data.ItemRutasList;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.Helper;

public class AdapterItemAmigo extends BaseAdapter{

	protected Activity activity;
    protected ArrayList<ItemAmigo> items;
    private ImageLoader imageLoader;
    
    public AdapterItemAmigo(Activity activity, ArrayList<ItemAmigo> items,
			ImageLoader imageLoader) {
		super();
		this.activity = activity;
		this.items = items;
		RequestQueue requestQueue = Volley.newRequestQueue(activity.getApplicationContext());
        this.imageLoader = new ImageLoader(requestQueue, new BitmapLRUCache());
	}

    @Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return ((ItemAmigo) getItem(arg0)).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 // Generamos una convertView por motivos de eficiencia
        View v = convertView;
        ViewHolderAmigo holder;
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
        	holder = new ViewHolderAmigo();
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_amigo, null);
            holder.nombre = (TextView) v.findViewById(R.id.txtNombreAmigo);
            holder.imagen = (NetworkImageView) v.findViewById(R.id.imagenAmigo);
            v.setTag(holder);
        } else {
        	holder = (ViewHolderAmigo) convertView.getTag();
        }
 
        // Creamos un objeto ItemEvent
        ItemAmigo dir = items.get(position);
        //Rellenamos la picturegrafía
        if (!dir.getPicture().equals("") && !dir.getPicture().equals("null")){
        	holder.imagen.setImageUrl(dir.getPicture(), imageLoader);
        }else{
        	holder.imagen.setImageUrl(Helper.getDefaultRutaPictureUrl(), imageLoader); //FIXME
        }
        //Rellenamos
        holder.nombre.setText(dir.getName());
 
        // Retornamos la vista
        return v;
	}
	
	static class ViewHolderAmigo{
		public TextView nombre;
		public NetworkImageView imagen;
	}
	
}
