package com.example.coffeeshop.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.example.coffeeshop.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private GoogleMap mMap;
    private String orderId = "orderId123";
    private DatabaseReference database;
    private List<LatLng> pathPoints = new ArrayList<>();
    private Polyline polyline;
    private LatLng orderLocation;
    private LatLng deliveryLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        database = FirebaseDatabase.getInstance().getReference();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        requestLocationPermission();
        fetchOrderLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;
                for (Location location : locationResult.getLocations()) {
                    orderLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    updateMap(orderLocation, deliveryLocation);
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void fetchOrderLocation() {
        database.child("orders").child(orderId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Double orderLat = dataSnapshot.child("order_lat").getValue(Double.class);
                    Double orderLng = dataSnapshot.child("order_lng").getValue(Double.class);
                    Double deliveryLat = dataSnapshot.child("delivery_lat").getValue(Double.class);
                    Double deliveryLng = dataSnapshot.child("delivery_lng").getValue(Double.class);

                    if (orderLat != null && orderLng != null) {
                        orderLocation = new LatLng(orderLat, orderLng);
                    }
                    if (deliveryLat != null && deliveryLng != null) {
                        deliveryLocation = new LatLng(deliveryLat, deliveryLng);
                    }
                    updateMap(orderLocation, deliveryLocation);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MapUpdate", "Failed to fetch order location.", databaseError.toException());
            }
        });
    }

    private void updateMap(LatLng orderLocation, LatLng deliveryLocation) {
        if (mMap != null) {
            mMap.clear();

            if (orderLocation != null) {
                mMap.addMarker(new MarkerOptions().position(orderLocation).title("Your Location"));
            }
            if (deliveryLocation != null) {
                mMap.addMarker(new MarkerOptions().position(deliveryLocation).title("Delivery Location"));
            }

            if (orderLocation != null && deliveryLocation != null) {
                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(orderLocation)
                        .add(deliveryLocation)
                        .width(8)
                        .color(0xFFFF0000);

                if (polyline != null) {
                    polyline.remove();
                }
                polyline = mMap.addPolyline(polylineOptions);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(orderLocation, 12));
            }
        }
    }
}
