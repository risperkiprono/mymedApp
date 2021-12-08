 package com.example.mymedapp;

 import android.os.Bundle;
import android.widget.SearchView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

 public class GoogleMap extends FragmentActivity implements OnMapReadyCallback {
     GoogleMap map;
     SupportMapFragment mapFragment;
     SearchView searchView;
     private Object LatLng;
     private Object GoogleMap;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        searchView=findViewById(R.id.sv_location);
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

         assert mapFragment != null;
         mapFragment.getMapAsync(this);
    }//replace maharashtra with my own location

     @Override
     public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
    googleMap.setOnMapClickListener(new com.google.android.gms.maps.GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(com.google.android.gms.maps.model.LatLng latLng) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(latLng.latitude+":"+ latLng.longitude);
            googleMap.clear();
            //Animating to zoom the marker
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                  latLng,10));
            //Add Marker on the map
            googleMap.addMarker(markerOptions);
        }
    });

      //   LatLng Maharashtra  = new LatLng(19.169257,73.341601);
       //  map.addMarker(new MarkerOptions().position(Maharashtra).title("maharashtra"));
         //map.moveCamera(CameraUpdateFactory.nevLatLng(Maharashtra));
     }
 }