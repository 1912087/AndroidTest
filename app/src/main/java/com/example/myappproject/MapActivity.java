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
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    ImageView ivOptions, ivPerson, ivImg;
    TextView tvLocation, tvCountry, tvContent;
    EditText search_location;
    ImageView btnSearch;
    String lat = "13.7672467799382";
    String lng = "100.4930243959339";

    LinearLayout linear1, linear2, noSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.gmap);
        mapFrag.getMapAsync(this);

        ivOptions = (ImageView) findViewById(R.id.ivOptions);
        ivPerson = (ImageView) findViewById(R.id.ivPerson);
        ivImg = (ImageView) findViewById(R.id.ivImg);

        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvCountry = (TextView) findViewById(R.id.tvCountry);
        tvContent = (TextView) findViewById(R.id.tvContent);

        search_location = (EditText) findViewById(R.id.search_location);
        btnSearch = (ImageView) findViewById(R.id.btnSearch);

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        noSearch = (LinearLayout) findViewById(R.id.noSearch);

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
                dlg.setPositiveButton("exit", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int witch){
                        finishAffinity();
                        System.runFinalization(); //현재 실행 중인 모든 앱을 종료56
                        System.exit(0);
                    }
                });
                dlg.setNegativeButton("logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dlg.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noSearch.setVisibility(View.GONE);
                linear1.setVisibility(View.VISIBLE);
                linear2.setVisibility(View.VISIBLE);
                if(search_location.getText().length() == 0){
                    Toast.makeText(MapActivity.this, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    search_location.requestFocus();
                }else if(search_location.getText().length() < 2){
                    Toast.makeText(MapActivity.this, "2글자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    search_location.requestFocus();
                }else{
                    String searchText = search_location.getText().toString().trim();
                    MapDto mapDto = MainActivity.mapDao.search(searchText);
                    if(mapDto.getCount() != 0){
                        tvLocation.setText(mapDto.getLocation());
                        tvCountry.setText(mapDto.getCountry());
                        tvContent.setText(mapDto.getContent());
                        ivImg.setImageResource(mapDto.getImg());
                        lat = mapDto.getLat();
                        lng = mapDto.getLng();

                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), 15));
                    }else{
                        noSearch.setVisibility(View.VISIBLE);
                        linear1.setVisibility(View.GONE);
                        linear2.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap = map;
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), 15));
    }

}
