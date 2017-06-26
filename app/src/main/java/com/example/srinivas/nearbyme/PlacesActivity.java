package com.example.srinivas.nearbyme;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srinivas.routemap.MapsActivity;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends Activity {
//    private LocationManager locationManager;
//    private LocationListener listener;
    private double latitude;
    private double longitude;
    private String searchElmt;
ListView placesLists;
    ProgressBar progress;
    private static  ArrayList<String> placeName;
    private  ArrayList<Double> lattitudeOfPlace;
    private  ArrayList<Double> longitudeOfPlace;
    //private double placeInDistance;
    //ListView hospitalPlaces;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        setContentView(R.layout.places_activity);

        placesLists = (ListView) findViewById(R.id.placeListLV);
        progress = (ProgressBar) findViewById(R.id.progressBar1);

        //present coordiantes

        Intent i = getIntent();
        latitude = i.getDoubleExtra("Lat",40.353);
        longitude = i.getDoubleExtra("Lng",-94.8);
        searchElmt = i.getStringExtra("type");

        Log.i("Place ",searchElmt);
        //placeInDistance = i.getDoubleExtra("distance",1000);
        //Log.i("Place Latitude",Double.toString(latitude));
        //Log.i("Place Latitude",Double.toString(longitude));

        //MainActivity.GetPlaces pl = new MainActivity.GetPlaces(getApplicationContext(), getListView());
        new GetPlaces().execute();
//pl.execute();

placesLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(PlacesActivity.this,MapsActivity.class);
        intent.putExtra("lattitudeDest",lattitudeOfPlace.get(position));
        intent.putExtra("longitudeDest",longitudeOfPlace.get(position));
        intent.putExtra("myLatitude",latitude);
        intent.putExtra("myLongitude",longitude);
        startActivity(intent);

    }
});


    }



    class GetPlaces extends AsyncTask<Void, Void, Void> {

       // private  dialog;
        private Context context;

        private String[] imageUrl;
        //private ListView listView;

//        public GetPlaces(Context context, ListView listView) {
//            // TODO Auto-generated constructor stub
//            this.context = context;
//            this.listView = listView;
//        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
progress.setVisibility(View.INVISIBLE);
            //dialog.dismiss();
            if (placeName.size() == 0){
                placeName.add("No" +searchElmt+" in  "+MainActivity.distnceNear+" meters radius");
            }
            ArrayAdapter<String> hospitals = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1,placeName);
            placesLists.setAdapter(hospitals);
            hospitals.notifyDataSetChanged();

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
           // listView = new ListView();
            progress.setVisibility(View.VISIBLE);
//list view header
            TextView textView = new TextView(PlacesActivity.this);
            textView.setText(searchElmt+ " in radius of "+MainActivity.distnceNear+" meters");
            textView.setTextSize(30);
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            placesLists.addHeaderView(textView);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            PlacesService service = new PlacesService("AIzaSyDPBnT7RTIYZuosjXTlxauD9ipFcrHA-IM");
            List<Place> findPlaces = service.findPlaces(latitude,longitude,searchElmt);


            // hospiral for hospital
            // atm for ATM

            System.out.println(latitude);
            System.out.println(longitude);

            placeName = new ArrayList<String>();
            lattitudeOfPlace = new ArrayList<>();
            longitudeOfPlace = new ArrayList<>();

            imageUrl = new String[findPlaces.size()];



            for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                placeDetail.getIcon();

                System.out.println(  placeDetail.getName());
                placeName.add(placeDetail.getName());



                lattitudeOfPlace.add(placeDetail.getLatitude());
                longitudeOfPlace.add(placeDetail.getLongitude());

                imageUrl[i] =placeDetail.getIcon();

            }
            return null;
        }

    }



}