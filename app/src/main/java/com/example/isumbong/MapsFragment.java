package com.example.isumbong;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//import com.google.android.gms.location.places.Places;


public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMap;

    //camera and location
    private static final float DEFAULT_ZOOM = 15f;

    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps;
    List<Address> list;
    List<Address> list1;

    //places
    String apikey;
    PlacesClient placesClient;
    String address;
    public static String getLocation;
    public static double getCoordinatesLat;
    public static double getCoordinatesLng;

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
//        Toast.makeText(getActivity(), "MAP IS READY", Toast.LENGTH_SHORT).show();
        mMap = googleMap;


        if(mLocationPermissionGranted){
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

//            init();

        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

//        mSearchText = view.findViewById(R.id.input_search);
        mGps = view.findViewById(R.id.ic_gps);
        apikey="AIzaSyBbB_T3Fyy_glsuCqo4P_EZNH59H4n8Ubo";


        getLocationPermission();
        init();



        return view;
    }

//    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
//            new LatLng(-40, -168), new LatLng(71, 136));

    private void init(){

        //autocomplete text
        if(!Places.isInitialized()){
            Places.initialize(requireContext(),apikey);
        }
        placesClient = Places.createClient(requireContext());
        final AutocompleteSupportFragment autocompleteSupportFragment =
                (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.NAME, Place.Field.ADDRESS));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                final LatLng latLng = place.getLatLng();
                //for debug
//                Toast.makeText(getActivity(), ""+latLng.latitude+latLng.longitude, Toast.LENGTH_SHORT).show();

                //insert geolocation
                moveCamera(new LatLng(latLng.latitude, latLng.longitude),DEFAULT_ZOOM,""+place.getAddress());
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
        //current location button
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }
    //
//    private void geoLocate(){
//        Geocoder geocoder = new Geocoder(requireActivity());
//        list = new ArrayList<>();
//        try{
//            list = geocoder.getFromLocationName(searchString, 1);
//        }catch (IOException e){
//            Toast.makeText(getActivity(), "NO INTERNET AVAILABLE", Toast.LENGTH_SHORT).show();
//
//        }
//        if(list.size() >0){
//            Address address = list.get(0);
//            Toast.makeText(getActivity(), address.toString(), Toast.LENGTH_SHORT).show();
//
//            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),DEFAULT_ZOOM,
//                    address.getAddressLine(0));
//
//        }
//    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(MapsFragment.this);

        }
    }

    private void getDeviceLocation(){
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        try{
            if(mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){



                            Log.d(TAG, "onComplete: found location!");
//                            Toast.makeText(getActivity(), "FOUND", Toast.LENGTH_SHORT).show();
                            Location currentLocation = (Location) task.getResult();

                            //get address
                            Geocoder geocoder = new Geocoder(requireContext());

                            if (currentLocation!=null) {
                                try{
                                    list = geocoder.getFromLocation(currentLocation.getLatitude(),currentLocation.getLongitude(),1);
                                }catch (IOException e){
                                    Toast.makeText(getActivity(), "NO INTERNET AVAILABLE", Toast.LENGTH_SHORT).show();
                                }
                                if(list != null && list.size()>0){
                                    address = list.get(0).getAddressLine(0);
                                }
                                moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),DEFAULT_ZOOM,""+address);//minnnneeeeeee
                            }

                        }else{
                            Log.d(TAG, "onComplete: nuullll");
                            Toast.makeText(getActivity(), "NULL", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
        }

    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "movecamera:moving camera to lat:  "+latLng.latitude +" long: "+ latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

//        if(!title.equals("My location")){
            //search marker
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
//        }
        getLocation = title;
        getCoordinatesLat = latLng.latitude;
        getCoordinatesLng = latLng.longitude;
        hideSoftKeyboard();

    }


    private void getLocationPermission(){
        Dexter.withContext(getActivity())
                .withPermission(FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        mLocationPermissionGranted = true;
                        initMap();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if(permissionDeniedResponse.isPermanentlyDenied()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setTitle("Permission Permanently Denied")
                                    .setMessage("Permission to access device location is permanently denied\n" +
                                            "Please proceed to settings to allow permission")
                                    .setNegativeButton("CANCEL",null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package",requireActivity().getPackageName(),null));
                                            startActivity(intent);
                                        }
                                    })
                                    .show();
                        }else {
                            Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                            mLocationPermissionGranted = false;
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();

    }

    private void hideSoftKeyboard(){
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



}