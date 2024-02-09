package com.github.philadelphia_radar_project;                                   // Package Name

import android.Manifest;                                                         // Needed for the Manifest class.

import android.content.pm.PackageManager;                                        // Needed for the PackageManager class.

import android.content.DialogInterface;                                          // Needed for the DialogInterface class.

import androidx.appcompat.app.AlertDialog;                                       // Needed for the AlertDialog class.

import androidx.appcompat.app.AppCompatActivity;                                 // Needed for the AppCompatActivity class.
import androidx.core.app.ActivityCompat;                                         // Needed for the ActivityCompat class.
import androidx.core.content.ContextCompat;                                      // Needed for the ContextCompat class.

import com.google.android.gms.location.FusedLocationProviderClient;              // Needed for the FusedLocationProvider class.
import com.google.android.gms.location.LocationServices;                         // Needed for the LocationServices class.
import com.google.android.gms.tasks.OnSuccessListener;                           // Needed for the OnSuccessListener interface.

import android.location.Location;                                                // Needed for the Location class.

import android.os.Bundle;                                                        // Needed for the Bundle class.
import android.view.View;                                                        // Needed for the View class.
import android.widget.TextView;                                                  // Needed for the TextView class.
import com.google.android.material.floatingactionbutton.FloatingActionButton;    // Needed for the FloatingActionButton class.

import org.json.JSONException;                                                   // Needed for the JSONException class.

/**-----------------------------------------------------------------------------------------------------
 * <b>Class GeolocationActivity.java</b>
 * @author Christopher McGarrity
 * @author Jonathan Gehret
 * @author Joseph Piazza
 *
 * This class uses Google's Geolocation API to retrieve the user's GPS location on Android.
-------------------------------------------------------------------------------------------------------*/

public class GeolocationActivity extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1; // Permission field
    private FloatingActionButton btnGetCoordinates;                             // Button field
    private TextView txtCoordinates;                                            // TextView field
    private FusedLocationProviderClient aFusedLocationClient;                   // FusedLocationProviderClient field

    /**
     * The <i>onCreate()</i> method initializes the activity.
     * @param savedInstanceState | A <i>Bundle</i> object reference.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation);

        // Create a TextView using the element's ID.
        this.txtCoordinates = findViewById(R.id.txtCoordinates);

        // Create a button.
        this.btnGetCoordinates = findViewById(R.id.btnGetCoordinates);

        // Initialize the location client.
        aFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Get the user's location.
        btnGetCoordinates.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getLocationUpdate();
            }
        });
    }

    /**
     * The <i>getLocation()</i> method retrieves the GPS location of the device.
     * The method first asks for Android's permission to receive location updates.
     */

    public void getLocationUpdate()
    {
        // Check to see if the activity has permission to use location services.
        if (ContextCompat.checkSelfPermission(GeolocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //If the activity does NOT have permission, initialize a dialog that requests permission access.
            if (ActivityCompat.shouldShowRequestPermissionRationale(GeolocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION))
            {
                new AlertDialog.Builder(this).setTitle("Location Access Required").setMessage("Please enable this permission to access this application").setPositiveButton("Enable", new DialogInterface.OnClickListener()
                {
                    // If "enable", grant permission access.
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ActivityCompat.requestPermissions(GeolocationActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    // Otherwise, dismiss the dialog.
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
            }
        }
        else
        {
            // If so, attempt to connect to a client.
            this.aFusedLocationClient.getLastLocation().addOnSuccessListener(GeolocationActivity.this, new OnSuccessListener<Location>()
            {
                @Override
                public void onSuccess(Location location)
                {
                    // If a location was found, attempt to connect to the APIs.
                    if (location != null)
                    {
                        // Start a new thread.
                        Thread thread = new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                // Get the latitude and longitude of the location.
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                try
                                {
                                    try
                                    {
                                        // Connect to the APIs using an endpoint, API key, and GPS position.
                                        CrimeLocationData criminalStat = new CrimeLocationData("https://api.crimeometer.com/v1/incidents/raw-data", "k3RAzKN1Ag14xTPlculT39RZb38LGgsG8n27ZycG", latitude,  longitude);
                                        YelpLocationData businessStat = new YelpLocationData("https://api.yelp.com/v3/businesses/search", "-_Rs7keJSgr9hZAVIfImQ4R-sFDvSK6QwuyUyiiRrwOOzDX2qncN8Rl5gx7n0U-vl2oI_nZa7t5-pd8wc88RfuqWhrD31yveEofG9efYI87NFzjh25RiZBHDHx2QYXYx", latitude,  longitude);

                                        // Convert the API data to JSON.
                                        org.json.simple.JSONObject obj1 = criminalStat.assignJSON();
                                        criminalStat.grade(obj1);

                                        org.json.simple.JSONObject obj2 = businessStat.assignJSON();
                                        businessStat.grade(obj2);

                                        // Update the UI.
                                        GeolocationActivity.this.runOnUiThread(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                // Display the information.
                                                txtCoordinates.setText("LAT: " + latitude + "\nLONG: " + longitude + "\nCrime grade: " + criminalStat.getGrade() + "\nBusiness grade: " + businessStat.getGrade());
                                            }
                                        });
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });

                        // Start the thread.
                        thread.start();
                    }
                }
            });
        }
    }
}
