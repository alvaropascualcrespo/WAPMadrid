package com.wapmadrid.adapters;

import java.util.ArrayList;

import com.wapmadrid.R;
import com.wapmadrid.data.ItemLayoutCapitan;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterItemLayoutCapitan extends BaseAdapter{

	protected Activity activity;
    protected ArrayList<ItemLayoutCapitan> items;
    private Drawable image;
    
    public AdapterItemLayoutCapitan(Activity activity, ArrayList<ItemLayoutCapitan> items,
			Drawable image) {
		super();
		this.activity = activity;
		this.items = items;
        this.image = image;
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
		return ((ItemLayoutCapitan) getItem(arg0)).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 // Generamos una convertView por motivos de eficiencia
        View v = convertView;
        ViewHolderLayoutCapitan holder;
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
        	holder = new ViewHolderLayoutCapitan();
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.layout_item_capitan, null);
            holder.nombre = (TextView) v.findViewById(R.id.txtLayout);
            holder.imagen = (ImageView) v.findViewById(R.id.imagenLayout);
            v.setTag(holder);
        } else {
        	holder = (ViewHolderLayoutCapitan) convertView.getTag();
        }
 
        // Creamos un objeto ItemEvent
        ItemLayoutCapitan dir = items.get(position);
        //Rellenamos la picturegrafía
        if (!dir.getPicture().equals("") && !dir.getPicture().equals("null")){
        	holder.imagen.setImageDrawable(image);

        }else{
        	holder.imagen.setImageDrawable(image); //FIXME
        }
        //Rellenamos
        holder.nombre.setText(dir.getName());
 
        // Retornamos la vista
        return v;
	}
	
	static class ViewHolderLayoutCapitan{
		public TextView nombre;
		public ImageView imagen;
	}
	
}
