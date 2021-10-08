package com.example.isumbong;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static com.example.isumbong.fragment_accident_info.img_accident;
import static com.example.isumbong.fragment_accident_info.img_license;
import static com.example.isumbong.fragment_accident_info.strUriAccident;
import static com.example.isumbong.fragment_accident_info.strUriLicense;
import static com.example.isumbong.fragment_accident_info.text_license;
import static com.example.isumbong.fragment_statement.statement_field;
import static com.example.isumbong.fragment_vehicle_info.VehicleType;
import static com.example.isumbong.fragment_vehicle_info.plate;
import static com.example.isumbong.fragment_vehicle_info.strUriOr;
import static com.example.isumbong.fragment_vehicle_info.strUriVehicle;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rakshakhegde.stepperindicator.StepperIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

//import com.anton46.stepsview.StepsView;


public class public_report_now extends AppCompatActivity implements OnMapReadyCallback {

    static int state = 0;
    FragmentTransaction ft;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    Fragment fragment;
    static FloatingActionButton next;
    static FloatingActionButton prev;
    database db;
    INPUTS input;

    static int victimsid;
    MapView mapview;
    View viewC;

    GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_report_now);

        input = new INPUTS();
        db = new database(this);
        //Stepper
        StepperIndicator stpi = findViewById(R.id.stepperIndicator);
        String[] steps = {"VICTIMS\nINFO", "ACCIDENT\nINFO", "VEHICLE\nINFO", "LOCATION", "STATEMENT", "CONFIRM"};
        stpi.setLabels(steps);
        stpi.showLabels(true);
        // stpi.setLabelSize(26);
        stpi.setLabelColor(public_report_now.this.getResources().getColor(R.color.black));
        stpi.setStepCount(steps.length);
        stpi.setIndicatorColor(public_report_now.this.getResources().getColor(R.color.green1));
        stpi.setLineColor(public_report_now.this.getResources().getColor(R.color.grey));
        stpi.setLineDoneColor(public_report_now.this.getResources().getColor(R.color.green1));
        stpi.setShowDoneIcon(true);


        next = findViewById(R.id.button);
        prev = findViewById(R.id.button_prev);

        //DEFAULT FRAG
        numvitims();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (state < (steps.length - 1)) {

                    state = state + 1;
                    stpi.setCurrentStep(state);
                    //    Toast.makeText(public_report_now.this, "STATE"+state, Toast.LENGTH_SHORT).show();

//                    if (state == 2) { VICTIM DETAILS
                    if (fragment instanceof fragment_victim_details) {
//                            Toast.makeText(public_report_now.this, ""+input.getText_license(), Toast.LENGTH_SHORT).show();
                        if (input.getText_license() == null || input.getText_license().equals(""))
                            fragment = new fragment_accident_info();
                        else
                            fragment = new fragment_accident_info(input.getText_license());
                    }

//                    } else if (state == 3) { ACCIDENT INFO
                    else if (fragment instanceof fragment_accident_info) {
                        //get text license
                        input.setText_license(text_license.getText().toString());

                        //set Vehicle info plate
                        if (input.getPlate() == null || input.getPlate().equals(""))
                            fragment = new fragment_vehicle_info();
                        else
                            fragment = new fragment_vehicle_info(input.getPlate());
                    }

//                    } else if (state == 4) { VEHICLE INFO
                    else if (fragment instanceof fragment_vehicle_info) {
                        //get text plate num
                        input.setPlate(plate.getText().toString());

                        //open maps
                        fragment = new MapsFragment();
                    }

                    //sate == 5 MAPS
                    else if (fragment instanceof MapsFragment) {
                        if (input.getstatement() == null || input.getstatement().equals(""))
                            fragment = new fragment_statement();
                        else
                            fragment = new fragment_statement(input.getstatement());

//                            Toast.makeText(public_report_now.this, ""+getLocation+"\n "+getCoordinatesLat+
//                                    "\n "+getCoordinatesLng, Toast.LENGTH_SHORT).show();
                    }

                    //STATEMENT X CONFIRMATION
                    else if (fragment instanceof fragment_statement) {
                        //get text statement
                        input.setStatement(statement_field.getText().toString());

                        //fragment for confirmation
                        fragment = new fragment_confirm();
                        //ALERT DIALOG FOR CONFIRMATION
                        confirmation(stpi);

                    }

                    ft = getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }

            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (state >= -1) {

                    if (state == 0){
                        next.hide();
                        prev.hide();
                        //reset
                        img_accident = null;
                        img_license = null;
                        fragment_vehicle_info.img_vehicle = null;
                        fragment_vehicle_info.img_or = null;

                        SharedPreferences sharedPref = getSharedPreferences("FileName", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefEditor = sharedPref.edit();
                        prefEditor.putInt("userChoiceSpinner",0);
                        prefEditor.apply();

                    } else {
                        state = state - 1;
                        // Toast.makeText(public_report_now.this, "STATE"+state, Toast.LENGTH_SHORT).show();
                        stpi.setCurrentStep(state);
                        //    text_license.setText(getIntent().getStringExtra("license"));
                    }


                }

                getSupportFragmentManager().popBackStack();

            }
        });
    }

    @Override
    public void onBackPressed() {
        ft = getSupportFragmentManager().beginTransaction();
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof fragment_novictims) {
            Intent intent = new Intent(public_report_now.this, public_homepage.class);
            startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP));
        } else
            super.onBackPressed();
    }

    //state == 1 NUMBER OF VICTIMS
    public void numvitims() {
        ft = getSupportFragmentManager().beginTransaction();
        fragment_novictims fnum = new fragment_novictims();
        ft.add(R.id.fragment_container, fnum, "fnum")
                .addToBackStack(null);
        ft.commit();
        next.hide();
        prev.hide();

    }

    private void getvictimdetails(View viewC) {
        ArrayList<Victim> victim = fragment_victim_details.victims;
        String name1 = "";
        for (Victim v : victim) {
            name1 += v.toString() + "\n";
        }
        TextView name = viewC.findViewById(R.id.textView_info);
        name.setText(name1);

        TextView number = viewC.findViewById(R.id.textView_number);
        number.setText(fragment_novictims.vnum);

    }

    public void confirmation(StepperIndicator stpi) {
        viewC = getLayoutInflater().inflate(R.layout.builder_confirmation, null);


        //insert info input
        getvictimdetails(viewC);
        getAccidentInfo(viewC);
        getVehicleInfo(viewC);
        getStatement(viewC);
        getDate(viewC);
//        MapView(viewC);


        AlertDialog.Builder builder1 = new AlertDialog.Builder(public_report_now.this);
        builder1.setTitle("CONFRIMATION")
                .setView(viewC)
                .setCancelable(false);

        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //stepper
                state -= 1;
                stpi.setCurrentStep(state);
                getSupportFragmentManager().popBackStack();
            }
        });
        builder1.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //stepper and redirect
                if (fragment != null) {
                    fragment = new fragment_serial();
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                    //state+=1;
                    stpi.setCurrentStep(state + 1);
                    next.hide();
                    prev.hide();

                    //add to database
                    VictimDetailsDB();
                    AccidentInfoDB();
                    VehicleInfoDB();
                    StatementDB();
                    LocationDB();


                }
            }
        });

        AlertDialog alertDialog = builder1.create();
        MapView(viewC, alertDialog);
        alertDialog.show();
    }

    public void VictimDetailsDB() {

        //add to database victims details
        boolean check = db.InsertVictims(Integer.parseInt(fragment_novictims.vnum));
        if (check) {
            victimsid = db.victimsID();
            ArrayList<Victim> victims = fragment_victim_details.victims;
            for (int j = 0; j < victims.size(); j++) {
                if (check)
                    check = db.InsertVictimInfo(victims.get(j), victimsid);
                else
                    break;
            }
        }
    }

    public void AccidentInfoDB() {
        boolean check = db.InsertAccidentInfo(strUriAccident, strUriLicense, victimsid, input.getText_license());
        if (check) {
//            Toast.makeText(this, "" + strUriAccident + "" + strUriLicense + "" + victimsid + "" + Text_license, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    private void getAccidentInfo(View viewC) {
        //intialize
        ImageView img_a = viewC.findViewById(R.id.imageView_confirm_accident);
        ImageView img_l = viewC.findViewById(R.id.imageView_confirm_license);
        TextView license = viewC.findViewById(R.id.textView_confirm_licensenum);
        //set

        try {
            img_a.setImageURI(Uri.parse(strUriAccident));
            license.setText(input.getText_license());
            img_l.setImageURI(Uri.parse(strUriLicense));
        } catch (Exception e) {
            Toast.makeText(this, "ERROR NO IMAGE", Toast.LENGTH_SHORT).show();
        }
    }
//    license.setImageURI(Uri.parse(inputs.setImg_accident()));

    private void VehicleInfoDB() {
        boolean check = db.InsertVehicleInfo(strUriVehicle, strUriOr, victimsid, input.getPlate(), VehicleType);
        if (check) {
//            Toast.makeText(this,"" +strUriAccident+""+strUriLicense+""+victimsid+""+Text_license, Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    private void getVehicleInfo(View viewC) {
        //intialize
        ImageView img_v = viewC.findViewById(R.id.imageView_confirm_vehicle);
        ImageView img_o = viewC.findViewById(R.id.imageView_confirm_or);
        TextView plate = viewC.findViewById(R.id.textView_confirm_plate);
        TextView vtype = viewC.findViewById(R.id.textView_confirm_vehicletype);
        //set
        try {
            img_v.setImageURI(Uri.parse(strUriVehicle));
            plate.setText(input.getPlate());
            img_o.setImageURI(Uri.parse(strUriOr));
            vtype.setText(VehicleType);
        } catch (Exception e) {
            Toast.makeText(this, "ERROR NO IMAGE", Toast.LENGTH_SHORT).show();
        }
    }

    private void StatementDB() {
        boolean check = db.InsertStatement(victimsid, input.getstatement());
        if (check) {
//            Toast.makeText(this, "ohk", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    private void getStatement(View viewC) {
        TextView state = viewC.findViewById(R.id.textView_confirm_statement);

        state.setText(input.getstatement());
    }

    private void MapView(View viewC, AlertDialog alertDialog1) {
        mapview = viewC.findViewById(R.id.mapView_confirm);
        mapview.onCreate(alertDialog1.onSaveInstanceState());
        mapview.onResume();
        mapview.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
    }
    private void getDeviceLocation(){
        float DEFAULT_ZOOM = 15f;
        moveCamera(new LatLng(MapsFragment.getCoordinatesLat, MapsFragment.getCoordinatesLng),DEFAULT_ZOOM,""+MapsFragment.getLocation);//minnnneeeeeee
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);
    }

    private void LocationDB(){
        boolean check = db.InsertLocation(victimsid,MapsFragment.getLocation, MapsFragment.getCoordinatesLat,MapsFragment.getCoordinatesLng);
        if (check) {
//            Toast.makeText(this, "ohk", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    static String date;
    private void getDate(View viewC){
        TextView date_field = viewC.findViewById(R.id.textView_date);
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy \n HH:mm:ss", Locale.getDefault());
        date = dateN.format(Calendar.getInstance().getTime());

        date_field.setText(date);

    }



}