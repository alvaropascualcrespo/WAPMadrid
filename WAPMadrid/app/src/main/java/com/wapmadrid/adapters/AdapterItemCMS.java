package com.wapmadrid.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wapmadrid.R;
import com.wapmadrid.data.ItemCMS;
import com.wapmadrid.data.ItemRuta;

import java.util.ArrayList;

/**
 * Created by Ismael on 22/05/2015.
 */
public class AdapterItemCMS extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<ItemCMS> items;

    public AdapterItemCMS(Activity activity, ArrayList<ItemCMS> items) {
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

        ViewHolderRuta holder;
        final ItemCMS item = items.get(position);
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
