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
import com.wapmadrid.data.ItemEvent;
import com.wapmadrid.data.ItemRuta;

import java.util.ArrayList;

/**
 * Created by Ismael on 22/05/2015.
 */
public class AdapterItemEvent extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<ItemEvent> items;

    public AdapterItemEvent(Activity activity, ArrayList<ItemEvent> items) {
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
        final ItemEvent item = items.get(position);
        if ( convertView == null ) {
            holder = new ViewHolderRuta();
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.item_event, null);
            holder.tvTitleEvent = (TextView) convertView.findViewById(R.id.tvTitleEvent);
            holder.tvDescriptionEvent = (TextView) convertView.findViewById(R.id.tvDescriptionEvent);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderRuta) convertView.getTag();
        }

        holder.tvTitleEvent.setText(item.getTitle());
        holder.tvDescriptionEvent.setText(item.getDescription());

        return convertView;
    }

    static class ViewHolderRuta{
        public TextView tvTitleEvent;
        public TextView tvDescriptionEvent;
    }

}
