package com.example.asad.homebuyerproject;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;


import android.support.v4.app.FragmentActivity;

import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxAutoCompleteTextView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.example.asad.homebuyerproject.data.model.Location;
import com.example.asad.homebuyerproject.data.model.PlaceAutocompleteResult;
import com.example.asad.homebuyerproject.data.model.PlaceDetailsResult;
import com.example.asad.homebuyerproject.data.model.Prediction;
import com.example.asad.homebuyerproject.data.RestClient;
import com.example.asad.homebuyerproject.utils.KeyboardHelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;


import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class Property_Located_Activity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = "PlaceSelectionListener";
    //private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(24, 61), new LatLng(37, 75.5));
    private static final int REQUEST_SELECT_PLACE = 1000;
    private static final String TAG = "MainActivity";
    private Button mNextActivity;

    private Toolbar toolbar;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;
    private static final long DELAY_IN_MILLIS = 500;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();
    private GoogleMap map;
    String datafrompreviousactivity;


    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property__located);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();

            if (extras == null)
            {
                datafrompreviousactivity = null;
            }
            else
            {
                datafrompreviousactivity = extras.getString("SellRentData");
            }

        }

        else
       {
           datafrompreviousactivity = (String) savedInstanceState.getSerializable("SellRentData");
        }


      // TypeCasting();

        SetToolbar();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mNextActivity=(Button)findViewById(R.id.NextActivity);

        mNextActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                Toast.makeText(getApplication(), datafrompreviousactivity, Toast.LENGTH_SHORT).show();
                Intent next = new Intent(Property_Located_Activity.this, Residential_Commercial_Activity.class);
                //pass data to other activity which get from SellRentActivity
                next.putExtra("SellRentData", datafrompreviousactivity);
                startActivity(next);
            }
        });




        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s)
            {

            }

            @Override
            public void onProviderDisabled(String s)
            {
            }
        };
        if (ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
            }, 10);
            return;
        } else {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                showSettingsAlert();
            } else {
                configurebutton();
            }
        }
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autocomplete_text);
        addOnAutoCompleteTextViewItemClickedSubscriber(autoCompleteTextView);
        addOnAutoCompleteTextViewTextChangedObserver(autoCompleteTextView);
    }

    private void SetToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
//      Toolbartext.setText("Test Screen");
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurebutton();
                    return;
                }
        }
    }

    private void configurebutton() {
        ImageView img_view = (ImageView) findViewById(R.id.img);

        img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Property_Located_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        android.location.Location networklocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (networklocation != null) {
                            LatLng latLng = new LatLng(networklocation.getLatitude(), networklocation.getLongitude());
                            map.clear();
                            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("Home Sweet Home"));
                            marker.showInfoWindow();
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        }
                    }
                    if (locationManager != null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        android.location.Location gpslocation = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (gpslocation != null) {
                            LatLng latLng = new LatLng(gpslocation.getLatitude(), gpslocation.getLongitude());
                            map.clear();
                            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("Home Sweet Home"));
                            marker.showInfoWindow();
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    private void addOnAutoCompleteTextViewTextChangedObserver(final AutoCompleteTextView autoCompleteTextView) {
        Observable<PlaceAutocompleteResult> autocompleteResponseObservable = RxTextView.textChangeEvents(autoCompleteTextView).debounce(DELAY_IN_MILLIS, TimeUnit.MILLISECONDS)

                .map(new Func1<TextViewTextChangeEvent, String>() {
                    @Override
                    public String call(TextViewTextChangeEvent textViewTextChangeEvent) {
                        return textViewTextChangeEvent.text().toString();
                    }
                })

                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s.length() >= 2;
                    }
                })

                .observeOn(Schedulers.io()).flatMap(new Func1<String, Observable<PlaceAutocompleteResult>>() {
                    @Override
                    public Observable<PlaceAutocompleteResult> call(String s) {
                        return RestClient.INSTANCE.getGooglePlacesClient().autocomplete(s);
                    }
                })

                .observeOn(AndroidSchedulers.mainThread()).retry();


        compositeSubscription.add(autocompleteResponseObservable.subscribe(new Observer<PlaceAutocompleteResult>() {

            private static final String TAG = "PlaceAutocompleteResult";

            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
            }

            @Override
            public void onNext(PlaceAutocompleteResult placeAutocompleteResult) {
                Log.i(TAG, placeAutocompleteResult.toString());

                List<NameAndPlaceId> list = new ArrayList<>();
                for (Prediction prediction : placeAutocompleteResult.predictions) {
                    list.add(new NameAndPlaceId(prediction.description, prediction.placeId));
                }

                ArrayAdapter<NameAndPlaceId> itemsAdapter = new ArrayAdapter<>(Property_Located_Activity.this,
                        android.R.layout.simple_list_item_1, list);
                autoCompleteTextView.setAdapter(itemsAdapter);
                String enteredText = autoCompleteTextView.getText().toString();
                if (list.size() >= 1 && enteredText.equals(list.get(0).name)) {
                    autoCompleteTextView.dismissDropDown();
                } else {
                    autoCompleteTextView.showDropDown();
                }
            }
        }));
    }

    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getApplicationContext());

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                Property_Located_Activity.this.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void addOnAutoCompleteTextViewItemClickedSubscriber(final AutoCompleteTextView autoCompleteTextView) {
        Observable<PlaceDetailsResult> adapterViewItemClickEventObservable = RxAutoCompleteTextView.itemClickEvents(autoCompleteTextView)

                .map(new Func1<AdapterViewItemClickEvent, String>() {
                    @Override
                    public String call(AdapterViewItemClickEvent adapterViewItemClickEvent) {
                        NameAndPlaceId item = (NameAndPlaceId) autoCompleteTextView.getAdapter()
                                .getItem(adapterViewItemClickEvent.position());
                        return item.placeId;
                    }
                })

                .observeOn(Schedulers.io()).flatMap(new Func1<String, Observable<PlaceDetailsResult>>() {
                    @Override
                    public Observable<PlaceDetailsResult> call(String placeId) {
                        return RestClient.INSTANCE.getGooglePlacesClient().details(placeId);
                    }
                })


                .observeOn(AndroidSchedulers.mainThread()).retry();

        compositeSubscription.add(adapterViewItemClickEventObservable.subscribe(new Observer<PlaceDetailsResult>() {

            private static final String TAG = "PlaceDetailsResult";

            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError", e);
            }

            @Override
            public void onNext(PlaceDetailsResult placeDetailsResponse) {
                Log.i(TAG, placeDetailsResponse.toString());
                updateMap(placeDetailsResponse);
            }
        }));
    }


    private void updateMap(PlaceDetailsResult placeDetailsResponse) {
        if (map != null) {
            map.clear();
            Location location = placeDetailsResponse.result.geometry.location;
            LatLng latLng = new LatLng(location.lat, location.lng);
            Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(placeDetailsResponse.result.name));
            marker.showInfoWindow();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            KeyboardHelper.hideKeyboard(Property_Located_Activity.this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }


    //Location Listener methods


    private static class NameAndPlaceId {
        final String name;
        final String placeId;

        NameAndPlaceId(String name, String placeId) {
            this.name = name;
            this.placeId = placeId;
        }

        @Override
        public String toString()

        {
            return name;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_residencial_search, menu);
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        //FOR BACK BUTTON ALSO INCLUDE META DATA IN MANIFEST
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);

    }

}










































































//package com.example.asad.homebuyerproject;
//
//import android.content.Intent;
//import android.support.v4.app.NavUtils;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class Property_Located_Activity extends AppCompatActivity {
//
//
//    private Toolbar toolbar;
//    private Button mNextActivity;
//    String datafrompreviousactivity;
//    private  Intent intent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_property__located);
//
//
//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                datafrompreviousactivity= null;
//            } else {
//                datafrompreviousactivity= extras.getString("SellRentData");
//            }
//        } else {
//            datafrompreviousactivity= (String) savedInstanceState.getSerializable("SellRentData");
//        }
//
//
//        TypeCasting();
//        SetToolbar();
//
//        //FOR BACK BUTTON ENABLED
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mNextActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Toast.makeText(getApplication(),datafrompreviousactivity,Toast.LENGTH_SHORT).show();
//                Intent next = new Intent(Property_Located_Activity.this,Residential_Commercial_Activity.class);
//                //pass data to other activity which get from SellRentActivity
//                next.putExtra("SellRentData", datafrompreviousactivity);
//                startActivity(next);
//            }
//        });
//    }
//
//    public void SetToolbar() {
//        toolbar = (Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);
//        //   Toolbartext.setText("Test Screen");
//
//    }
//
//    public void TypeCasting() {
//
//        mNextActivity=(Button)findViewById(R.id.NextActivity);
//    }
//
//
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//
//            return true;
//        }
//
//        //FOR BACK BUTTON ALSO INCLUDE META DATA IN MANIFEST
//        if (id == android.R.id.home) {
//            NavUtils.navigateUpFromSameTask(this);
//        }
//
//        return super.onOptionsItemSelected(item);
//
//    }
//
//}
