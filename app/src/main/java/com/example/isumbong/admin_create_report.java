package com.example.isumbong;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class admin_create_report extends AppCompatActivity implements OnMapReadyCallback {

    database db;
    int counter = 0;
    String queryString1;
    String text1 = "";
    String queryString="";
    String reportSerial;
//    ArrayList<String>text1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_report);

        setInfo();
        reportSerial = generateString(8);

        FloatingActionButton attach = findViewById(R.id.floatingActionButton_create_attach);
        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog();

            }
        });

        Button submit = findViewById(R.id.button_create_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReport();
            }
        });


    }
    public void setInfo(){
        TextView officer = findViewById(R.id.textView_create_officer);
        TextView sector = findViewById(R.id.textView_create_sector);
        TextView date = findViewById(R.id.textView_create_date);
        TextView email = findViewById(R.id.textView_create_email);

        db = new database(this);
        String user = getIntent().getStringExtra("user");
        String DnT = setDate();

        officer.setText(""+db.getName(user)+" ("+db.getIdNo(user)+")");
        sector.setText(db.getPnpSector(admin_login.admin_code));
        date.setText(DnT);
        email.setText(db.getEmail(user));
    }
    public String setDate(){
        SimpleDateFormat dateN = new SimpleDateFormat("MMMM dd, yyyy (HH:mm:ss)", Locale.getDefault());
        return dateN.format(Calendar.getInstance().getTime());
    }

    public void dialog(){
        db = new database(this);
        ArrayList<String> vserial = new ArrayList<>();
        vserial = db.getVerifiedSerial();

        Dialog dialog = new Dialog(admin_create_report.this);
        dialog.setContentView(R.layout.admin_verified_serials);
        dialog.getWindow().setLayout(650,800);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        EditText search = dialog.findViewById(R.id.editText_search_verified);
        ListView listView = dialog.findViewById(R.id.listview_verified_serial);
        TextView tView = dialog.findViewById(R.id.textView_def);
        ArrayAdapter<String> verifiedSerial = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,vserial);
        listView.setAdapter(verifiedSerial);
        listView.setEmptyView(tView);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                verifiedSerial.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
                queryString =(String)adapterView.getItemAtPosition(i);
                queryString1=queryString;
                attachFiles(queryString);
                dialog.dismiss() ;
            }
        });
    }


    @SuppressLint("ResourceAsColor")
    private void attachFiles(String queryString){

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout);

        counter ++;

        TextView textView = new TextView(admin_create_report.this);
        textView.setId(counter);
        textView.setText(queryString);

        String text = textView.getText().toString();
//        text1.add(text+"\n");
        text1 += text+"\n";

//        textView.setBackgroundColor(R.color.grey);
//        textView.setTextColor(android.R.color.black);
        textView.setClickable(true);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    getAttachedView(queryString, linearLayout, textView);
            }
        });

        linearLayout.addView(textView);

    }
    public int getInfoVserial(String queryString){
        return db.getIDxVserial(queryString);
    }

    public void getAttachedView(String queryString, LinearLayout linearLayout, TextView textView){

        //GET VICTIMS ID BASED ON VSERIAL
        int id = getInfoVserial(queryString);

        admin_report_files layout = new admin_report_files();

//        ArrayList<String> offenses = db.getOffenses(db.getLicenseNumber(id));
//        ArrayAdapter<String> adapterOff = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, offenses);
//
//        ArrayList<String> reports_list = db.getReports(db.getLicenseNumber(id));
//        ArrayAdapter<String> adapter_rep = new ArrayAdapter<> (this, android.R.layout.simple_dropdown_item_1line, reports_list);

        View viewS = getLayoutInflater().inflate(R.layout.admin_builder_attached_serial, null);

//        ImageButton viewBtn = viewS.findViewById(R.id.imageButton_attached_off);
        View viewB = getLayoutInflater().inflate(R.layout.builder_offenses, null);
        Context ctx = admin_create_report.this;

        setNumber(id,viewS);
        setVictimDetais(id,viewS);
        setImgs(id,viewS);
        Infos(id,viewS);

//        layout.offensesButton(viewBtn,viewB,id,adapterOff, adapter_rep, ctx);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(queryString)
                .setView(viewS)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("REMOVE", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        linearLayout.removeView(textView);
                        String obj = textView.getText().toString();
//                        text1.removeIf(text1 -> text1.contains(obj));
                        text1 = text1.replace(textView.getText().toString(),"");
                    }
                });
        AlertDialog alertDialog = builder.create();
        MapView(viewS, alertDialog);
        alertDialog.show();

    }

    private void submitReport(){
        String serials = text1;
        String details = getStatement();

        if(details.matches("") && serials.matches("")){
            Toast.makeText(admin_create_report.this, "There is an empty field!", Toast.LENGTH_SHORT).show();
        }
        else if(details.matches("")){
           Toast.makeText(admin_create_report.this, "Incident details field is empty", Toast.LENGTH_SHORT).show();
        }
        else if(serials.matches("")){
            Toast.makeText(admin_create_report.this, "Reference report files are required", Toast.LENGTH_SHORT).show();
        }


        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("INCIDENT REPORT "+""+reportSerial)
                    .setMessage("Proceed creating this report?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            boolean check = db.InsertIncidentReport(getType(),getOfficer(),getEmail(),getSector(),getDate(),getStatement(),serials,reportSerial);
                            if (check) {
                                Toast.makeText(admin_create_report.this, "Incident Report Created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(admin_create_report.this, admin_report_files.class);
                                intent.putExtras(getIntent()).putExtra("selected",2);
                                startActivity(intent);

                            } else
                                Toast.makeText(admin_create_report.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                          dialogInterface.dismiss();
                        }
                    })
                    .show();
            }
    }

    //SET BUILDER INFORMATION
    public void setNumber(int ID, View v){
        TextView number = v.findViewById(R.id.textView_number);
        number.setText(db.getNumVictims(ID));
    }
    public void setVictimDetais(int ID,View v){
        TextView info = v.findViewById(R.id.textView_info);

        ArrayList<Victim> victim = db.getVictimsInfo(ID);

        String name1 = "";
        for (Victim vc : victim) {
            name1 += vc.toString() + "\n";
        }
        info.setText(name1);
    }
    public void setImgs(int ID, View v){
        ImageView acc = v.findViewById(R.id.imageView_confirm_accident);
        ImageView lic = v.findViewById(R.id.imageView_confirm_license);
        ImageView vehicle = v.findViewById(R.id.imageView_confirm_vehicle);
        ImageView or = v.findViewById(R.id.imageView_confirm_or);

        acc.setImageURI(Uri.parse(db.getAccidentImg(ID)));
        lic.setImageURI(Uri.parse(db.getLicenseImg(ID)));
        vehicle.setImageURI(Uri.parse(db.getVehicleImg(ID)));
        or.setImageURI(Uri.parse(db.getOrImg(ID)));
    }
    public void Infos(int ID, View v){
        TextView license = v.findViewById(R.id.textView_confirm_licensenum);
        TextView plate = v.findViewById(R.id.textView_confirm_plate);
        TextView type = v.findViewById(R.id. textView_confirm_vehicletype);
        TextView statement = v.findViewById(R.id.textView_confirm_statement);
        TextView date = v.findViewById(R.id.textView_date);
        TextView vdate = v.findViewById(R.id.textView_confirm_vdate);
        TextView officer = v.findViewById(R.id.textView_confirm_officer);

        license.setText(db.getLicenseNumber(ID));
        plate.setText(db.getPlateNumber(ID));
        type.setText(db.getVehicleType(ID));
        statement.setText(db.getStatement(ID));
        date.setText(db.getDate(ID));
        vdate.setText(db.getVdate(ID));
        officer.setText(""+db.getName(db.getUser(ID))+" ("+db.getIdNo(db.getUser(ID))+")");
    }

    //SOMETHING WRONG HERE!!!!
    MapView mapview;
    private void MapView(View viewS, AlertDialog builder) {
        mapview = viewS.findViewById(R.id.mapView_confirm);
        mapview.onCreate(builder.onSaveInstanceState());
        mapview.onResume();
        mapview.getMapAsync(this);
    }

    GoogleMap mMap;

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        int id = getInfoVserial(queryString1);
        getDeviceLocation(id);
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

    private String getType(){
        TextView type = findViewById(R.id.textView_incident);
        return type.getText().toString();
    }
    private String getOfficer(){
        TextView officer = findViewById(R.id.textView_create_officer);
        return officer.getText().toString();
    }
    private String getEmail(){
        TextView email = findViewById(R.id.textView_create_email);
        return email.getText().toString();
    }
    private String getSector(){
        TextView sector = findViewById(R.id.textView_create_sector);
        return sector.getText().toString();
    }
    private String getDate(){
        TextView date = findViewById(R.id.textView_create_date);
        return date.getText().toString();
    }
    private String getStatement(){
        TextView statement = findViewById(R.id.editText_create_report_info);
        return statement.getText().toString();
    }


private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String generateString(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        String serial = builder.toString();
        return serial;
    }







}