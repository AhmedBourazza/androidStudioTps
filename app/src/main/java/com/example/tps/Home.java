package com.example.tps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button logoutBtn,showMap ;
    DrawerLayout drawer ;
    GoogleMap gMap;
    FrameLayout map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer=findViewById(R.id.draw_layout);

        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,R.string.navigation_drawer_open,R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmennt_container,
                    new MapFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_map);

        }





       /* logoutBtn = findViewById(R.id.lout);
        showMap = findViewById(R.id.smap);
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this,mapsActivity.class);
                startActivity(i);
            }
        });
      */
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_map) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmennt_container,
                    new MapFragment()).commit();
        }
        else if(item.getItemId() == R.id.nav_calculatrice)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmennt_container,
                    new CalculatriceFragment()).commit();
        }
        else if(item.getItemId() == R.id.nav_profil)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmennt_container,
                    new ProfilFragment()).commit();
        }
        else if(item.getItemId() == R.id.nav_logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent =new Intent(Home.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }
    }
}