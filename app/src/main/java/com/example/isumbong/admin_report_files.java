package com.example.isumbong;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class admin_report_files extends AppCompatActivity implements OnMapReadyCallback {
    static RadioButton vReport;
    static RadioButton iReport;
    ArrayAdapter<String> arrayAdapterI;
    ArrayAdapter<String> arrayAdapterV;
   androidx.appcompat.widget.SearchView searchView;
    database db = new database(this);
    String queryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report_files);

        vReport = findViewById(R.id.radioButton_verified_report);
        iReport = findViewById(R.id.radioButton_incident_report);

        getOnClick();
        setIntent();



    }
    private void setIntent(){
        int selected = getIntent().getIntExtra("selected",0);

        switch (selected){
            case 1: vReport.setChecked(true);
                setvReportList();
                break;

            case 2: iReport.setChecked(true);
                setiReportList();
                break;
        }
    }
    private void setiReportList(){
        ListView list = findViewById(R.id.listView_report_files);
        ArrayList<String> rSerial = new ArrayList<>();
        rSerial = db.getReportSerial();
        arrayAdapterI = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,rSerial);
        list.setAdapter(arrayAdapterI);
//        get ItemI
        OnClickI(list);
    }
    private void setvReportList(){
        ListView list1 = findViewById(R.id.listView_report_files1);
        ArrayList<String> vSerial = new ArrayList<>();
        vSerial = db.getVerifiedSerial();
        arrayAdapterV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,vSerial);
        list1.setAdapter(arrayAdapterV);
        //get Item
        OnClickV(list1);
    }

    //radiobutton
    private void getOnClick(){
        vReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(vReport.isChecked()){
                    ListView list = findViewById(R.id.listView_report_files);
                    ListView list1 = findViewById(R.id.listView_report_files1);
                    list1.setVisibility(View.VISIBLE);
                    list.setVisibility(View.INVISIBLE);
                    setvReportList();
                }


            }
        });
        iReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(iReport.isChecked()){
                    ListView list = findViewById(R.id.listView_report_files);
                    ListView list1 = findViewById(R.id.listView_report_files1);
                    list1.setVisibility(View.INVISIBLE);
                    list.setVisibility(View.VISIBLE);
                    setiReportList();
                }

            }
        });
    }

    //search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_admin_search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Serial Code");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(vReport.isChecked()){
                    arrayAdapterV.getFilter().filter(newText);
                }
                if(iReport.isChecked()){
                    arrayAdapterI.getFilter().filter(newText);
                }
                return false;
            }
        });

        return true;
    }
    //verified reports get item
    private void OnClickV(ListView list1){
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                queryString =(String)adapterView.getItemAtPosition(i);
                getArrayItemV(queryString);
            }
        });

    }

    private void getArrayItemV(String queryString){
        //GET VICTIMS ID BASED ON VSERIAL
        int id = db.getIDxVserial(queryString);
        View viewS = getLayoutInflater().inflate(R.layout.admin_builder_vreport, null);

        setNumberDef(id,viewS);
        setVictimDetaisDef(id,viewS);
        setInfoDef(id,viewS);
        setImgsDef(id,viewS);
        setTextDef(viewS);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VERIFIED REPORT ( " +queryString + " )")
                .setView(viewS)
                .setCancelable(false)
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.DeleteVreport(queryString);
                        Toast.makeText(admin_report_files.this, "VERIFIED REPORT DELETED", Toast.LENGTH_SHORT).show();
                        arrayAdapterV.remove(queryString);
                    }
                })
                .setNeutralButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        MapView(viewS, alertDialog);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setNumber(id,viewS);
                setVictimDetais(id,viewS);
                setInfo(id,viewS);
                setText(viewS);
            }
        });

    }
    //DEFAULT SET BUILDER

    public void setNumberDef(int ID, View v){
        EditText number = v.findViewById(R.id.textView_number);
        number.setHint(db.getNumVictims(ID));
        number.setEnabled(false);
    }
    public void setVictimDetaisDef(int ID,View v){
        EditText info = v.findViewById(R.id.textView_info);

        ArrayList<Victim> victim = db.getVictimsInfo(ID);

        String name1 = "";
        for (Victim vc : victim) {
            name1 += vc.toString() + "\n";
        }
        info.setHint(name1);
        info.setEnabled(false);
    }

    private void setInfoDef(int ID, View v){
        EditText license = v.findViewById(R.id.textView_confirm_licensenum);
        EditText plate = v.findViewById(R.id.textView_confirm_plate);
        EditText type = v.findViewById(R.id. textView_confirm_vehicletype);
        EditText statement = v.findViewById(R.id.textView_confirm_statement);
        TextView date = v.findViewById(R.id.textView_date);
        TextView vdate = v.findViewById(R.id.textView_confirm_vdate);
        TextView officer = v.findViewById(R.id.textView_confirm_officer);

        license.setEnabled(false);
        plate.setEnabled(false);
        type.setEnabled(false);
        statement.setEnabled(false);

        license.setHint(db.getLicenseNumber(ID));
        plate.setHint(db.getPlateNumber(ID));
        type.setHint(db.getVehicleType(ID));
        statement.setHint(db.getStatement(ID));
        date.setText(db.getDate(ID));
        vdate.setText(db.getVdate(ID));
        officer.setText(""+db.getName(db.getUser(ID))+" ("+db.getIdNo(db.getUser(ID))+")");
    }
    public void setImgsDef(int ID, View v){
        ImageView acc = v.findViewById(R.id.imageView_confirm_accident);
        ImageView lic = v.findViewById(R.id.imageView_confirm_license);
        ImageView vehicle = v.findViewById(R.id.imageView_confirm_vehicle);
        ImageView or = v.findViewById(R.id.imageView_confirm_or);

        acc.setImageURI(Uri.parse(db.getAccidentImg(ID)));
        lic.setImageURI(Uri.parse(db.getLicenseImg(ID)));
        vehicle.setImageURI(Uri.parse(db.getVehicleImg(ID)));
        or.setImageURI(Uri.parse(db.getOrImg(ID)));
    }
    private void setTextDef(View v){
        TextView view = v.findViewById(R.id.text);
        TextView edit = v.findViewById(R.id.textView_vreport_edit);
        ImageButton acc = v.findViewById(R.id.imageButton_acc);
        ImageButton lic = v.findViewById(R.id.imageButton_lic);
        ImageButton cr = v.findViewById(R.id.imageButton_cr);
        ImageButton or = v.findViewById(R.id.imageButton_or);
        ImageButton loc = v.findViewById(R.id.imageButton_map);
        Button save = v.findViewById(R.id.button_save);

        view.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);
        acc.setVisibility(View.INVISIBLE);
        lic.setVisibility(View.INVISIBLE);
        cr.setVisibility(View.INVISIBLE);
        or.setVisibility(View.INVISIBLE);
        loc.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
    }
    //EDIT BUTTON
    private void setText(View v){
        TextView view = v.findViewById(R.id.text);
        TextView edit = v.findViewById(R.id.textView_vreport_edit);
        ImageButton acc = v.findViewById(R.id.imageButton_acc);
        ImageButton lic = v.findViewById(R.id.imageButton_lic);
        ImageButton cr = v.findViewById(R.id.imageButton_cr);
        ImageButton or = v.findViewById(R.id.imageButton_or);
        ImageButton loc = v.findViewById(R.id.imageButton_map);
        Button save = v.findViewById(R.id.button_save);

        view.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        acc.setVisibility(View.VISIBLE);
        lic.setVisibility(View.VISIBLE);
        cr.setVisibility(View.VISIBLE);
        or.setVisibility(View.VISIBLE);
        loc.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);

        String user = getIntent().getStringExtra("user");
        edit.setText(""+db.getName(user)+" ("+db.getIdNo(user)+")");
    }
    public void setNumber(int ID, View v){
        EditText number = v.findViewById(R.id.textView_number);
        number.setText(db.getNumVictims(ID));
        number.setEnabled(true);
    }
    public void setVictimDetais(int ID,View v){
        EditText info = v.findViewById(R.id.textView_info);

        ArrayList<Victim> victim = db.getVictimsInfo(ID);

        String name1 = "";
        for (Victim vc : victim) {
            name1 += vc.toString() + "\n";
        }
        info.setText(name1);
        info.setEnabled(true);
    }

    private void setInfo(int ID, View v){
        EditText license = v.findViewById(R.id.textView_confirm_licensenum);
        EditText plate = v.findViewById(R.id.textView_confirm_plate);
        EditText type = v.findViewById(R.id. textView_confirm_vehicletype);
        EditText statement = v.findViewById(R.id.textView_confirm_statement);
        TextView date = v.findViewById(R.id.textView_date);
        TextView vdate = v.findViewById(R.id.textView_confirm_vdate);
        TextView officer = v.findViewById(R.id.textView_confirm_officer);

        license.setEnabled(true);
        plate.setEnabled(true);
        type.setEnabled(true);
        statement.setEnabled(true);

        license.setText(db.getLicenseNumber(ID));
        plate.setText(db.getPlateNumber(ID));
        type.setText(db.getVehicleType(ID));
        statement.setText(db.getStatement(ID));
        date.setText(db.getDate(ID));
        vdate.setText(db.getVdate(ID));
        officer.setText(""+db.getName(db.getUser(ID))+" ("+db.getIdNo(db.getUser(ID))+")");
    }

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
        int id = db.getIDxVserial(queryString);
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

    private void OnClickI(ListView list){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                queryString =(String)adapterView.getItemAtPosition(i);
                getArrayItemI(queryString);
            }
        });
    }
    private void getArrayItemI(String queryString){
        int id = db.getReportID(queryString);
        View viewI = getLayoutInflater().inflate(R.layout.activity_admin_create_report, null);

        setTextDefI(viewI);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("INCIDENT REPORT ( " +queryString + " )")
                .setView(viewI)
                .setCancelable(false)
                .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.DeleteIreport(queryString);
                        Toast.makeText(admin_report_files.this, "INCIDENT REPORT DELETED", Toast.LENGTH_SHORT).show();
                        arrayAdapterI.remove(queryString);
                    }
                })
                .setNeutralButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setTextEdit(viewI);
//                setNumber(id,viewI);
//                setVictimDetais(id,viewI);
//                setInfo(id,viewI);
//                setText(viewI);
            }
        });

    }

    private void setTextDefI(View v){
        TextView title = v.findViewById(R.id.textView24);
        Button submit = v.findViewById(R.id.button_create_submit);
        FloatingActionButton attach = v.findViewById(R.id.floatingActionButton_create_attach);
        EditText details = v.findViewById(R.id.editText_create_report_info);


        title.setVisibility(View.INVISIBLE);
        details.setEnabled(false);
        submit.setVisibility(View.INVISIBLE);
        attach.setClickable(false);
    }
    private void setTextEdit(View v){
        TextView title = v.findViewById(R.id.textView24);
        Button submit = v.findViewById(R.id.button_create_submit);
        FloatingActionButton attach = v.findViewById(R.id.floatingActionButton_create_attach);
        EditText details = v.findViewById(R.id.editText_create_report_info);


        title.setVisibility(View.INVISIBLE);
        details.setEnabled(true);
        submit.setVisibility(View.INVISIBLE);
        attach.setClickable(false);
    }

}