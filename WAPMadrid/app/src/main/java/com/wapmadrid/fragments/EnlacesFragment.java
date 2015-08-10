package com.wapmadrid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wapmadrid.R;

public class EnlacesFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.enlaces_fragment, container, false);
        TextView tvEnlace1 = (TextView) v.findViewById(R.id.tvEnlace1);
        TextView tvEnlace2 = (TextView) v.findViewById(R.id.tvEnlace2);

        Linkify.addLinks(tvEnlace1, Linkify.ALL);
        Linkify.addLinks(tvEnlace2, Linkify.ALL);

        return v;
    }

}
