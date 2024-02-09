package com.github.philadelphia_radar_project;                                  // Package name

import androidx.fragment.app.FragmentActivity;                                  // Needed for the FragmentActivity class.
import com.google.android.gms.maps.CameraUpdateFactory;                         // Needed for the CameraUpdateFactory class.
import com.google.android.gms.maps.GoogleMap;                                   // Needed for the GoogleMap class.
import com.google.android.gms.maps.OnMapReadyCallback;                          // Needed for the OnMapReadyCallback interface.
import com.google.android.gms.maps.SupportMapFragment;                          // Needed for the SupportMapFragment class.
import com.google.android.gms.maps.model.LatLng;                                // Needed for the LatLng class.
import com.google.android.gms.maps.model.MarkerOptions;                         // Needed for the MarkerOptions class.
import com.github.philadelphia_radar_project.databinding.ActivityMapsBinding;   // Needed for the ActivityMapsBinding class.

import android.os.Bundle;                                                       // Needed for the Bundle class.

/**
 * <b>Class MapsActivity.java</b>
 * @author Christopher McGarrity
 * @author Jonathan Gehret
 * @author Joseph Piazza
 *
 * This class initializes a Google Map element. The user's location is indicated by
 * a custom map marker (Incomplete).
 */

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap map;                 // map field
    private ActivityMapsBinding binding;    // binding field

    /**
     * The <i>onCreate()</i> method initializes the activity.
     * @param savedInstanceState | A <i>Bundle</i> object reference.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Initialize a reference to the map element.
        binding = ActivityMapsBinding.inflate(getLayoutInflater());

        // Set the layout to be used for this activity.
        setContentView(binding.getRoot());

        // Acquire and display the Google map element.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        // Initialize the Google map element.
        map = googleMap;

        // Add a marker to the Google map.
        LatLng location = new LatLng(39.9526, -75.1652);
        map.addMarker(new MarkerOptions().position(location));

        // Update the camera view.
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}