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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.shamim.newbusstop.Nearby_Places.GetNearby_Place;
import com.shamim.newbusstop.drawer_layout.all_bus;
import com.shamim.newbusstop.drawer_layout.contact;
import com.shamim.newbusstop.drawer_layout.login;
import com.shamim.newbusstop.drawer_layout.register;
import com.shamim.newbusstop.drawer_layout.setting;

import java.util.Arrays;
import java.util.Locale;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    //for map variable

    private GoogleMap mMap;
    private String destination, requestService;

    private LatLng destinationLatLng;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker CurrentUserLocationMarker;
    private static final int Request_User_Location_code = 99;
    private double latitude, longitude;
    private int proximityRadius = 10000;
    private DrawerLayout drawerLayout;
    String TAG = "drawerlayout";
    //navigation bar
    private BottomNavigationView bottomNavigationView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        /*sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
           String usertype= sharedPref.getString("userType","NA");
           String drivertype = sharedPref.getString("userType","NA");
        Toast.makeText(this,usertype, Toast.LENGTH_SHORT).show();
        Toast.makeText(this,drivertype, Toast.LENGTH_SHORT).show();*/

        preferences = getSharedPreferences("BusBD_Info", Context.MODE_PRIVATE);
        editor = preferences.edit();

        Boolean User_isLogged = preferences.getBoolean("User_isLogged", false);
        Boolean Driver_islogged = preferences.getBoolean("Driver_isLogged", false);
        if (User_isLogged) {
            Intent intent = new Intent(Home.this, Customer_MapsActivity.class);
            startActivity(intent);
            finish();
        }
        else if (Driver_islogged)
        {
            Intent intent = new Intent(Home.this, Driver_maps_Activity.class);
            startActivity(intent);
            finish();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

        Log.d(TAG, "Navigation botton");



        Places.initialize(getApplicationContext(), "mykey", Locale.US);
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull com.google.android.libraries.places.api.model.Place place)
            {
                destination = place.getName().toString();
                destinationLatLng = place.getLatLng();
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.d(TAG,"Place Search Error:=="+status.getStatusMessage());

            }
        } );




    }

    //For Drawerlayout Method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "Navigation drawer");
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home_alluser:
                Intent intent = new Intent(Home.this, Home.class);
                startActivity(intent);
                break;

            case R.id.allbus:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new all_bus()).commit();
                break;
            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.test2,
                        new setting()).commit();
                break;
            case R.id.login:
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                login loginFragment = new login();
                transaction.replace(R.id.test3, loginFragment);
                transaction.addToBackStack(null);
                transaction.commit();


                break;
            case R.id.register:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new register()).commit();

                break;

            case R.id.contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.test3,
                        new contact()).commit();
                break;
            case R.id.exit:
                finish();
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
        latitude = location.getLatitude();
        longitude = location.getLongitude();
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
        switch (requestCode) {
            case Request_User_Location_code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
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
       /* String restaurant="restaurant";
        Object transferData[] = new Object[2];
        GetNearby_Place getNearby_place=new GetNearby_Place();
        mMap.clear();
        String url = getUrl(latitude, longitude, restaurant);
        transferData[0] = mMap;
        transferData[1] = url;

        getNearby_place.execute(transferData);
        Toast.makeText(this, "Searching for Nearby restaurant...", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Showing Nearby restaurant...", Toast.LENGTH_SHORT).show();*/


    }

    public void atm(View view) {


    }

    public void hospital(View view) {


    }

    public void search_image_Botton(View view) {
        String hospital = "hospital", school = "school", restaurant = "restaurant";
        Object transferData[] = new Object[2];
        GetNearby_Place getNearbyPlaces = new GetNearby_Place();


        switch (view.getId()) {
           /* case R.id.search_image_Botton:
                EditText addressField = (EditText) findViewById(R.id.Location_search);
                String address = addressField.getText().toString();

                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(address)) {
                    Geocoder geocoder = new Geocoder(this);

                    try {
                        addressList = geocoder.getFromLocationName(address, 1);

                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {
                                Address userAddress = addressList.get(i);
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(address);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mMap.addMarker(userMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        } else {
                            Toast.makeText(this, "Location not found...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "please write any location name...", Toast.LENGTH_SHORT).show();
                }
                break;*/


            case R.id.hospital_image:
                Log.d(TAG, "Hospital" + "" + hospital);
                mMap.clear();
                String url = getUrl(latitude, longitude, hospital);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for Nearby Hospitals...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby Hospitals...", Toast.LENGTH_SHORT).show();
                break;


            case R.id.atm_image:
                mMap.clear();
                url = getUrl(latitude, longitude, school);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for Nearby Schools...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby Schools...", Toast.LENGTH_SHORT).show();
                break;


            case R.id.restaurant_image:
                mMap.clear();
                url = getUrl(latitude, longitude, restaurant);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for Nearby Restaurants...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing Nearby Restaurants...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "," + longitude);
        googleURL.append("&radius=" + proximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyDtIWXQDUA1ufc_Vff3qbz522DnZ26Nk9w");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());

        return googleURL.toString();
    }

}



