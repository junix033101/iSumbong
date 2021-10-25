package com.example.isumbong;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class admin_view_serial extends AppCompatActivity implements OnMapReadyCallback {
    database db = new database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_serial);

        Button verify = findViewById(R.id.button_verify);

        setSerial();
        String serial = SerialString();
        int ID = getVictimID(serial);
        setVictimsInfoNumber(ID);
        setVictimsInfoDetails(ID);
        setImgs(ID);
        Infos(ID);


        //location
        MapView map = findViewById(R.id.mapView_confirm);
        map.onCreate(savedInstanceState);
        map.onResume();
        map.getMapAsync(this);

        boolean check = db.CheckVerifiedSerial(serial);
        if (check){
            verify.setText("REPORT VERIFIED");
        }
        else{
            verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String vdate = setDate();
                    builder(ID, serial, vdate);
                    verify.setText("VERIFY");
                    getIntent();
                }
            });
        }

        database db1 = new database(this);
        editButton(ID);

        ArrayList<String> offenses = db1.getOffenses(db1.getLicenseNumber(ID));
        ArrayAdapter<String> adapterOff = new ArrayAdapter<>(admin_view_serial.this, android.R.layout.simple_dropdown_item_1line, offenses);

        ArrayList<String> reports_list = db1.getReports(db1.getLicenseNumber(ID));
        ArrayAdapter<String> adapter = new ArrayAdapter<> (this, android.R.layout.simple_dropdown_item_1line, reports_list);

        offensesButton(ID, adapterOff, adapter);

    }

    private void setSerial(){
        TextView serial = findViewById(R.id.textView_admin_view_serial);

        Intent intent = getIntent();
        String clickedSerial = intent.getStringExtra("clicked_serial");
        serial.setText(clickedSerial);
    }
    private String SerialString(){
        Intent intent = getIntent();
        return intent.getStringExtra("clicked_serial");
    }

    private int getVictimID(String clickedSerial){
        //get id based on serial number
        return db.getVictimxSerial(clickedSerial);
    }
    private void setVictimsInfoNumber(int ID){
        TextView number = findViewById(R.id.textView_number);
        number.setText(db.getNumVictims(ID));
    }
    private void setVictimsInfoDetails(int ID){
        TextView info = findViewById(R.id.textView_info);

        ArrayList<Victim> victim = db.getVictimsInfo(ID);

        String name1 = "";
        for (Victim v : victim) {
            name1 += v.toString() + "\n";
        }
        info.setText(name1);
    }
    private void setImgs(int ID){
        ImageView acc = findViewById(R.id.imageView_confirm_accident);
        ImageView lic = findViewById(R.id.imageView_confirm_license);
        ImageView vehicle = findViewById(R.id.imageView_confirm_vehicle);
        ImageView or = findViewById(R.id.imageView_confirm_or);

        acc.setImageURI(Uri.parse(db.getAccidentImg(ID)));
        lic.setImageURI(Uri.parse(db.getLicenseImg(ID)));
        vehicle.setImageURI(Uri.parse(db.getVehicleImg(ID)));
        or.setImageURI(Uri.parse(db.getOrImg(ID)));
    }

    private void Infos(int ID){
        TextView license = findViewById(R.id.textView_confirm_licensenum);
        TextView plate = findViewById(R.id.textView_confirm_plate);
        TextView type = findViewById(R.id. textView_confirm_vehicletype);
        TextView statement = findViewById(R.id.textView_confirm_statement);
        TextView date = findViewById(R.id.textView_date);

        license.setText(db.getLicenseNumber(ID));
        plate.setText(db.getPlateNumber(ID));
        type.setText(db.getVehicleType(ID));
        statement.setText(db.getStatement(ID));
        date.setText(db.getDate(ID));
    }

    private String setDate(){
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy \n HH:mm:ss", Locale.getDefault());
        return dateN.format(Calendar.getInstance().getTime());
    }


    GoogleMap mMap;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        int ID = getVictimID(SerialString());
        getDeviceLocation(ID);
    }
    private void getDeviceLocation(int ID){
        float DEFAULT_ZOOM = 15f;
        moveCamera(new LatLng(db.getLatitude(ID), db.getLongitude(ID)),DEFAULT_ZOOM,""+db.getLocation(ID));
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        mMap.clear();

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);
    }
    private void builder(int ID, String serial, String vdate){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VERIFICATION")
                .setMessage("Are you sure to verify this report? Changes can't be made after verifying")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String user = getIntent().getStringExtra("user");
                        boolean check = db.InsertVerifiedSerial(ID,serial,vdate,user);
                        if (check) {
                            Toast.makeText(admin_view_serial.this, "Verified", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(admin_view_serial.this, admin_report_files.class);
                            intent.putExtra("selected",1);
                            intent.putExtra("user",user);
//                            intent.putExtras(getIntent());
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(admin_view_serial.this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("NO",null)
                .show();
    }

    private void editButton(int ID){
        Button edit = findViewById(R.id.button_edit);
        String serial = SerialString();
        boolean check = db.CheckVerifiedSerial(serial);
        if(check){
            edit.setVisibility(View.INVISIBLE);
        }
        else{
            edit.setVisibility(View.VISIBLE);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(admin_view_serial.this, public_report_now.class);
                    intent.putExtra("for_update",true);
                    intent.putExtra("id", ID);
                    intent.putExtra("edit","edit");
                    intent.putExtras(getIntent());
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private void offensesButton(int ID ,ArrayAdapter adapterOff, ArrayAdapter adapter){
        ImageButton Boffense = findViewById(R.id.imageButton_offenses);

        Boffense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewS = getLayoutInflater().inflate(R.layout.builder_offenses, null);

                radioBtn(viewS, ID, adapterOff, adapter);

                AlertDialog.Builder builder = new AlertDialog.Builder(admin_view_serial.this);
                builder.setTitle("LICENSE INFORMATION")
                        .setView(viewS)
                        .setCancelable(false)
                        .setNeutralButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }
    public void radioBtn(View viewS, int ID, ArrayAdapter adapterOff, ArrayAdapter adapter){
        RadioButton offense = viewS.findViewById(R.id.radioButton_off_offenses);
        RadioButton reports = viewS.findViewById(R.id.radioButton_off_reports);
        ListView list = viewS.findViewById(R.id.listView_offenses);
        TextView def = viewS.findViewById(R.id.textView_def);


        if(!offense.isChecked() && !reports.isChecked())
            list.setEmptyView(def);

        offense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(offense.isChecked()) {

                    list.setAdapter(adapterOff);
                    list.setEmptyView(def);

                }
            }
        });
        reports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(reports.isChecked()){

                    list.setAdapter(adapter);
                    list.setEmptyView(def);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(admin_view_serial.this, admin_homepage.class);
        intent.putExtras(getIntent());
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}