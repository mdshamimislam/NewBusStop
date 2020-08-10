package com.shamim.newbusstop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.shamim.newbusstop.drawer_layout.all_bus;
import com.shamim.newbusstop.drawer_layout.contact;
import com.shamim.newbusstop.drawer_layout.exit;
import com.shamim.newbusstop.drawer_layout.login;
import com.shamim.newbusstop.drawer_layout.logout;
import com.shamim.newbusstop.drawer_layout.profile;
import com.shamim.newbusstop.drawer_layout.register;
import com.shamim.newbusstop.drawer_layout.setting;
import com.shamim.newbusstop.drawer_layout.ticket;


import java.io.IOException;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    //for map variable
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker CurrentUserLocationMarker;
    private static final int Request_User_Location_code = 99;

    private DrawerLayout drawerLayout;
    String TAG ="drawerlayout";
    //navigation bar
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            CheckUserLocationPermission();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
             mapFragment.getMapAsync(this);

        //For Used Drawerlayout (ToolBar)
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.drawerlayout_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d(TAG,"Navigation botton");




    }

    //For Drawerlayout Method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG,"Navigation drawer");
        Fragment fragment=null;
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent(Home.this,Home.class);
                startActivity(intent);
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new profile()).commit();
                break;

            case R.id.allbus:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new all_bus()).commit();
                break;
            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new setting()).commit();
                break;
            case R.id.login:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new login()).commit();
                break;
            case R.id.register:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new register()).commit();
                break;

            case R.id.ticket:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new ticket()).commit();
                break;
            case R.id.contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new contact()).commit();
                break;
            case R.id.logout:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new logout()).commit();
                break;
            case R.id.exit:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new exit()).commit();
                break;

            case R.id.share:
                Toast.makeText(this, "Share this app", Toast.LENGTH_SHORT).show();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //End  Drawerlayout Method

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(locationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        if (CurrentUserLocationMarker != null) {
            CurrentUserLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("User Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        CurrentUserLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);

        }



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
        }
    }

        public Boolean CheckUserLocationPermission() {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_code);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_code);
                }
                return false;

            } else {
                return true;
            }

        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case Request_User_Location_code:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient==null)
                        {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else {
                    Toast.makeText(this, "Permission Denied.....", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    //For create new client
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }


    public void restaurant(View view) {



    }

    public void atm(View view) {


    }

    public void hospital(View view) {



    }

    public void search_image_Botton(View view) {
        switch (view.getId()) {
            case R.id.search_image_Botton:
                EditText addressField = (EditText) findViewById(R.id.Location_search);
                String address = addressField.getText().toString();
                List<Address> addressList = null;
                MarkerOptions usermarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(address, 1);
                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {
                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                usermarkerOptions.position(latLng);
                                usermarkerOptions.title(address);
                                usermarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                mMap.addMarker(usermarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        }
                        else
                        {
                            Toast.makeText(this, "Location not Found...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(this, "Please Enter any Location", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }
    }


