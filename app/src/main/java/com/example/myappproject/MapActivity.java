package com.example.myappproject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends Activity implements OnMapReadyCallback {

    GoogleMap gMap;
    MapFragment mapFrag;
    GroundOverlayOptions videoMark;
    ImageView ivOptions, ivPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.gmap);
        mapFrag.getMapAsync(this);

        ivOptions = (ImageView) findViewById(R.id.ivOptions);
        ivPerson = (ImageView) findViewById(R.id.ivPerson);

        ivOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotplaceIntent = new Intent(getApplicationContext(), HotplaceActivity.class);
                startActivity(hotplaceIntent);
            }
        });

        ivPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MapActivity.this);
                dlg.setTitle("confirm exit");
                dlg.setIcon(R.drawable.exit);
                dlg.setMessage("Are you sure you want to exit?");

                dlg.setPositiveButton("exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.runFinalization();
                        System.exit(0);
                    }
                });

                dlg.show();
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.7672467799382, 100.4930243959339), 15));
    }

}
