
package com.example.tps;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapsActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    FrameLayout map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        map = findViewById(R.id.map);
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap=googleMap;
        LatLng mapEnsaFes = new LatLng(33.996679209345885, -4.991836426998822);
        LatLng mapEnsaAgadir = new LatLng(30.406085945162136, -9.529883239731834);

        LatLng mapEnsaMarrakech = new LatLng(31.65990974101816, -8.023517370518274);
        LatLng mapEnsaOujda = new LatLng(34.966120174475805, -1.9183119445915966);
        LatLng mapEnsaTetouan = new LatLng(35.56231630546612, -5.364557524611684);
        LatLng mapEnsaElHoceima = new LatLng(35.211989518006945, -3.8538081153190893);
        LatLng mapEnsaKhouribga = new LatLng(32.89723873261541, -6.9136887938067435);
        LatLng mapEnsaKenitra = new LatLng(34.26562448457443, -6.5777415972578055);
        this.gMap.addMarker(new MarkerOptions().position(mapEnsaFes).title("ENSA FEZ"));
        this.gMap.addMarker(new MarkerOptions().position(mapEnsaAgadir).title("ENSA Agadir"));

        this.gMap.addMarker(new MarkerOptions().position(mapEnsaMarrakech).title("ENSA Marrakech"));
        this.gMap.addMarker(new MarkerOptions().position(mapEnsaOujda).title("ENSA Oujda"));
        this.gMap.addMarker(new MarkerOptions().position(mapEnsaTetouan).title("ENSA Tetouan"));
        this.gMap.addMarker(new MarkerOptions().position(mapEnsaElHoceima).title("ENSA El houceima"));
        this.gMap.addMarker(new MarkerOptions().position(mapEnsaKhouribga).title("ENSA Khouribga"));
        this.gMap.addMarker(new MarkerOptions().position(mapEnsaKenitra).title("ENSA Kenitra"));

        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapEnsaFes));
    }
}