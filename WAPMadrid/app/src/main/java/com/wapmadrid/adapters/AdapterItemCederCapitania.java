package com.wapmadrid.adapters;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.wapmadrid.R;
import com.wapmadrid.data.ItemCederCapitania;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.Helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterItemCederCapitania extends BaseAdapter{

	protected Activity activity;
    protected ArrayList<ItemCederCapitania> items;
    private ImageLoader imageLoader;
    
    public AdapterItemCederCapitania(Activity activity, ArrayList<ItemCederCapitania> items,
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
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 // Generamos una convertView por motivos de eficiencia
        View v = convertView;
        ViewHolderCederCapitania holder;
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
        	holder = new ViewHolderCederCapitania();
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_ceder_capitania, null);
            holder.nombre = (TextView) v.findViewById(R.id.txtNombreAmigoC);
            holder.imagen = (NetworkImageView) v.findViewById(R.id.imagenAmigoC);
            v.setTag(holder);
        } else {
        	holder = (ViewHolderCederCapitania) convertView.getTag();
        }
 
        // Creamos un objeto ItemEvent
        ItemCederCapitania dir = items.get(position);
        //Rellenamos la picturegrafía
        if (!dir.getPicture().equals("") && !dir.getPicture().equals("null")){
        	holder.imagen.setImageUrl(dir.getPicture(), imageLoader);
        }else{
        	holder.imagen.setImageUrl(Helper.getDefaultRutaPictureUrl(), imageLoader); //FIXME
        }
        //Rellenamos
        holder.nombre.setText(dir.getNombre());
 
        // Retornamos la vista
        return v;
	}
	
	static class ViewHolderCederCapitania{
		public TextView nombre;
		public NetworkImageView imagen;
	}
	
}
