package com.wapmadrid.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wapmadrid.R;
import com.wapmadrid.activities.RoutesListActivity;
import com.wapmadrid.data.ItemGrupo;
import com.wapmadrid.data.ItemRuta;
import com.wapmadrid.data.ItemRutasList;
import com.wapmadrid.utilities.BitmapLRUCache;
import com.wapmadrid.utilities.CustomItemMap;
import com.wapmadrid.utilities.Helper;

import java.util.ArrayList;

/**
 * Created by Ismael on 22/05/2015.
 */
public class AdapterItemRuta extends BaseAdapter {

    protected FragmentActivity activity;
    protected ArrayList<ItemRuta> items;
    private Fragment fragment;

    public AdapterItemRuta(Fragment frag, FragmentActivity activity, ArrayList<ItemRuta> items) {
        super();
        this.activity = activity;
        this.items = items;
        this.fragment = frag;
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

        ViewHolderRuta holder;
        final ItemRuta item = items.get(position);
        if ( convertView == null ) {
            holder = new ViewHolderRuta();
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.item_ruta, null);
            holder.name = (TextView) convertView.findViewById(R.id.tvRouteName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderRuta) convertView.getTag();
        }

        holder.name.setText(item.getName());

        return convertView;
    }

    static class ViewHolderRuta{
        public TextView name;
    }

}
