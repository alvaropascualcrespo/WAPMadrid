package com.wapmadrid.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.wapmadrid.R;
import com.wapmadrid.data.ItemEvento;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterItemEvento extends BaseAdapter{
	
	protected Activity activity;
    protected ArrayList<ItemEvento> items;
    
    public AdapterItemEvento(Activity activity, ArrayList<ItemEvento> items,
			ImageLoader imageLoader) {
		super();
		this.activity = activity;
		this.items = items;
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
        ViewHolderEvento holder;
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
        	holder = new ViewHolderEvento();
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_evento, null);
            holder.centroMedico = (TextView) v.findViewById(R.id.txtNombreGrupo);
            holder.titulo = (TextView) v.findViewById(R.id.txtTituloEvento);
            holder.texto = (TextView) v.findViewById(R.id.txtTextoEvento);
            holder.fecha = (TextView) v.findViewById(R.id.txtFechaEvento);
            holder.hora = (TextView) v.findViewById(R.id.txtHoraEvento);
            v.setTag(holder);
        } else {
        	holder = (ViewHolderEvento) convertView.getTag();
        }
 
        // Creamos un objeto ItemEvent
        ItemEvento dir = items.get(position);
        //Rellenamos
        holder.centroMedico.setText(dir.getCentroMedico());
        holder.titulo.setText(dir.getTitulo());
        holder.texto.setText(dir.getTexto());
        holder.fecha.setText(dir.getFecha());
        holder.hora.setText(dir.getHora());
 
        // Retornamos la vista
        return v;
	}
	
	static class ViewHolderEvento{
		public TextView centroMedico;
		public TextView titulo;
		public TextView texto;
		public TextView fecha;
		public TextView hora;
	}

}
