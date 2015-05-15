package com.wapmadrid.adapters;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.data.ItemRutasList;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterItemRutas extends BaseAdapter{

	protected Activity activity;
    protected ArrayList<ItemRutasList> items;
    private ImageLoader imageLoader;
    
	public AdapterItemRutas(Activity activity, ArrayList<ItemRutasList> items,
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
		return ((ItemRutasList) getItem(arg0)).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 // Generamos una convertView por motivos de eficiencia
        View v = convertView;
        ViewHolderRutas holder;
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
        	holder = new ViewHolderRutas();
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_rutas_list, null);
            holder.picture = (NetworkImageView) v.findViewById(R.id.imagenRuta);
            holder.txtname = (TextView) v.findViewById(R.id.txtNombre);
            holder.txtdescripcion = (TextView) v.findViewById(R.id.txtDescripcion);
            v.setTag(holder);
        } else {
        	holder = (ViewHolderRutas) convertView.getTag();
        }
 
        // Creamos un objeto ItemEvent
        ItemRutasList dir = items.get(position);
        //Rellenamos la picturegrafía
        if (!dir.getImagen().equals("") && !dir.getImagen().equals("null")){
        	holder.picture.setImageUrl(dir.getImagen(), imageLoader);
        }else{
        	holder.picture.setImageUrl(Helper.getDefaultRutaPictureUrl(), imageLoader); //FIXME
        }
        //Rellenamos el name
        holder.txtname.setText(dir.getNombre());
        //Rellenamos la descripcion
        holder.txtdescripcion.setText(dir.getDescripcion());
 
        // Retornamos la vista
        return v;
	}
	
	static class ViewHolderRutas {
		public NetworkImageView picture;
		public TextView txtname;
		public TextView txtdescripcion;
	}

}
