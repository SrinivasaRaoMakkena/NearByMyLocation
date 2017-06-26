package com.example.srinivas.nearbyme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DestinationCoordinatesActivity extends AppCompatActivity {
TextView lattitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_coordinates);

        lattitude = (TextView) findViewById(R.id.lattitude);
        longitude = (TextView) findViewById(R.id.longitude);

        Intent intent = getIntent();
        lattitude.setText(""+intent.getDoubleExtra("lattitude",0.0));
        longitude.setText(""+intent.getDoubleExtra("longitude",0.0));
    }
}
