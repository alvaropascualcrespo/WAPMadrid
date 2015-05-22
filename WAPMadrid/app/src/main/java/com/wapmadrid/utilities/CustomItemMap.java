package com.wapmadrid.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.wapmadrid.data.ItemRuta;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Ismael on 22/05/2015.
 */
public class CustomItemMap extends LinearLayout{

    Context context;
    GoogleMap map;
    ItemRuta item;
    TextView txtName;
    FrameLayout frame;
    Fragment frag;
    SupportMapFragment mapFrag;

    public CustomItemMap(Fragment frag,Activity activity, ItemRuta item) {
        super(activity.getApplicationContext());
        this.item = item;
        this.context = activity.getApplicationContext();
        this.frag = frag;
        int myGeneratedFrameLayoutId = Math.getExponent(Math.random());
        myGeneratedFrameLayoutId += item.getPoints().size(); // choose any way you want to generate your view id

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.item_ruta, null);
        frame = new FrameLayout(context);
        frame.setId(myGeneratedFrameLayoutId);

        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,height);
        frame.setLayoutParams(layoutParams);

        txtName = new TextView(context);
        txtName.setTextColor(Color.WHITE);
        txtName.setGravity(Gravity.CENTER_HORIZONTAL);

        LayoutParams lptv = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        lptv.setMargins(margin,margin,margin,margin);
        txtName.setLayoutParams(lptv);

        view.addView(txtName);
        view.addView(frame);

        txtName.setText(item.getName());

        GoogleMapOptions options = new GoogleMapOptions();
        options.liteMode(true); //this makes it possible, otherwise your list view would be really slow
        mapFrag = SupportMapFragment.newInstance(options);

        mapFrag.getMapAsync(new MyMapCallback(item));
        FragmentManager fm = frag.getChildFragmentManager();
        fm.beginTransaction().add(frame.getId(),mapFrag).commit();

        addView(view);

    }

    public class MyMapCallback implements OnMapReadyCallback {

        ItemRuta itemRuta;

        MyMapCallback(ItemRuta itemRuta_in) {
           this.itemRuta = itemRuta_in;
        }


        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.width(8);
            polylineOptions.color(context.getResources().getColor(R.color.orange_wap));
            polylineOptions.geodesic(true);
            Polyline poly = googleMap.addPolyline(polylineOptions);
            poly.setPoints(itemRuta.getPoints());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(itemRuta.getPoints().get(1), 13));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                }
            });
        }
    }



}
