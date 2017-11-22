package com.example.srinivas.nearbyme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView lat,longi ;
    TextView searchElement,distance ;



public static double distnceNear;

    private LocationManager locationManager;
    private LocationListener listener;
    private  double latitudePresent;
    private  double longitudePresent;
    String address;
    public void search(View view){
        String d = distance.getText().toString();
        if(d != "" && !d.isEmpty()) {
            distnceNear = Double.parseDouble(d);
        }
        Intent places = new Intent(MainActivity.this,PlacesActivity.class);

        places.putExtra("Lat", latitudePresent);
        places.putExtra("Lng", longitudePresent);
        places.putExtra("type",searchElement.getText().toString().trim());
        //places.putExtra("distance",distance.getText().toString());

        startActivity(places);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //request code --> 1
        //permissions array , we do haVE ONLY ONE PERMISSION
        //PERMISSION RESULT

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            } else {
                return;
            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lat = (TextView) findViewById(R.id.lattitude);
        longi = (TextView) findViewById(R.id.longitude);
        searchElement = (TextView) findViewById(R.id.searchType);
        distance = (TextView) findViewById(R.id.distance);




        lat.setText(Double.toString(longitudePresent));
        longi.setText(Double.toString(latitudePresent));

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //configure_button();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitudePresent = location.getLatitude();
                longitudePresent = location.getLongitude();

               // Log.i("Lattitude",Double.toString(latitude));
                //Log.i("Longitude",Double.toString(longitude));

                lat.setText(Double.toString(longitudePresent));
                longi.setText(Double.toString(latitudePresent));



               // address = getCompleteAddressString(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
// first check for permissions
        //if API <23, no need of permissions
        if (Build.VERSION.SDK_INT < 23) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // if no permission -> ask for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                //if we have permissions already..if not not ask for permission result
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            }
        }



    }
}
