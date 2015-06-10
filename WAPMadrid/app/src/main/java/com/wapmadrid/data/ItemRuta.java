package com.wapmadrid.data;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wapmadrid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ismael on 22/05/2015.
 */
public class ItemRuta{

    private ArrayList<LatLng> points;
    private String id;
    private String name;

    public ItemRuta(String id, String name, JSONArray puntos) {
        this.id = id;
        this.name = name;
        if (puntos != null) {
            points = new ArrayList<>();
            for (int i = 0; i < puntos.length(); i++) {
                try {
                    JSONObject aux = puntos.getJSONObject(i);
                    float _lat = Float.valueOf(aux.getString("_lat"));
                    float _long = Float.valueOf(aux.getString("_long"));
                    points.add(new LatLng(_lat, _long));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public ArrayList<LatLng> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<LatLng> points) {
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
