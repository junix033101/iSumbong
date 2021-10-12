package com.example.isumbong;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    int selected;

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


         selected = getIntent().getIntExtra("selected",0);

        switch (selected){
            case 1: vReport.setChecked(true);
                setvReportList();
                break;

            case 2: iReport.setChecked(true);
                setiReportList();
                break;
            default:break;
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

        setNumber(id,viewS);
        setVictimDetais(id,viewS);
        setInfo(id,viewS);
        setImgs(id,viewS);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VERIFIED REPORT ( " +queryString + " )")
                .setView(viewS)
                .setCancelable(false)
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
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
                vDel(alertDialog,queryString);
            }
        });
    }

    private void vDel(AlertDialog dialog, String queryString){
        String user = getIntent().getStringExtra("user");
        String cUser = user;
        String oUser = db.Username(queryString);
        if (cUser.equals(oUser)){
            //delete ENTER CODE BUILDER
            dialog.dismiss();
            VdelBuilder(dialog);
        }
        else{
            Toast.makeText(admin_report_files.this, "ONLY THE OWNER CAN DELETE THIS REPORT", Toast.LENGTH_SHORT).show();
        }
    }
    private void VdelBuilder(AlertDialog dialog){
        View viewV = getLayoutInflater().inflate(R.layout.admin_delete_code, null);

        EditText code = viewV.findViewById(R.id.textView_del_code);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("ENTER CHIEF CODE")
                .setView(viewV)
                .setMessage("Pls obtain a code from your chief officer to be able to delete this report. Any deleted report can't be retrieved.")
                .setCancelable(false)
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialog.show();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();
        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = code.getText().toString();
                String sector = db.getChiefCode(db.getPnpSector(admin_login.admin_code));
                if(input.equals(sector)){
                    db.DeleteVreport(queryString);
                    Toast.makeText(admin_report_files.this, "VERIFIED REPORT DELETED", Toast.LENGTH_SHORT).show();
                    arrayAdapterV.remove(queryString);
                    alertDialog1.dismiss();
                }
                else{
                    Toast.makeText(admin_report_files.this, "INVALID CODE", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





    //DEFAULT SET BUILDER

    public void setNumber(int ID, View v){
        TextView number = v.findViewById(R.id.textView_number);
        number.setText(db.getNumVictims(ID));
        number.setEnabled(true);
    }
    public void setVictimDetais(int ID,View v){
        TextView info = v.findViewById(R.id.textView_info);

        ArrayList<Victim> victim = db.getVictimsInfo(ID);

        String name1 = "";
        for (Victim vc : victim) {
            name1 += vc.toString() + "\n";
        }
        info.setText(name1);
        info.setEnabled(true);
    }

    private void setInfo(int ID, View v){
        TextView license = v.findViewById(R.id.textView_confirm_licensenum);
        TextView plate = v.findViewById(R.id.textView_confirm_plate);
        TextView type = v.findViewById(R.id. textView_confirm_vehicletype);
        TextView statement = v.findViewById(R.id.textView_confirm_statement);
        TextView date = v.findViewById(R.id.textView_date);
        TextView vdate = v.findViewById(R.id.textView_verified_on);
        TextView officer = v.findViewById(R.id.textView_officer);

        license.setText(db.getLicenseNumber(ID));
        plate.setText(db.getPlateNumber(ID));
        type.setText(db.getVehicleType(ID));
        statement.setText(db.getStatement(ID));
        date.setText(db.getDate(ID));
        vdate.setText(db.getVdate(ID));
        officer.setText(""+db.getName(db.getUser(ID))+" ("+db.getIdNo(db.getUser(ID))+")");
    }
    private void setImgs(int ID, View v){
        ImageView a = v.findViewById(R.id.imageView_confirm_accident);
        ImageView l = v.findViewById(R.id.imageView_confirm_license);
        ImageView c = v.findViewById(R.id.imageView_confirm_vehicle);
        ImageView o = v.findViewById(R.id.imageView_confirm_or);

        a.setImageURI(Uri.parse(db.getAccidentImg(ID)));
        l.setImageURI(Uri.parse(db.getLicenseImg(ID)));
        c.setImageURI(Uri.parse(db.getVehicleImg(ID)));
        o.setImageURI(Uri.parse(db.getOrImg(ID)));

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





    //incident report
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
//        int id = db.getReportID(queryString);
        View viewI = getLayoutInflater().inflate(R.layout.builder_incident_report_files, null);

        setTextDefI(viewI);
        setInfoI(queryString, viewI);

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

                EditRes(viewI);
            }
        });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelRes(alertDialog);
            }
        });

    }

    private void setTextDefI(View v){
        TextView title = v.findViewById(R.id.textView24);
        Button submit = v.findViewById(R.id.button_create_submit);
        FloatingActionButton attach = v.findViewById(R.id.floatingActionButton_create_attach);
        EditText details = v.findViewById(R.id.editText_create_report_info);
        TextView text1 = v.findViewById(R.id.textView_edit_on);
        TextView editon = v.findViewById(R.id.textView_ireport_edit_date);

        title.setVisibility(View.INVISIBLE);
        details.setEnabled(false);
        submit.setVisibility(View.INVISIBLE);
        attach.setClickable(false);
        text1.setVisibility(View.INVISIBLE);
        editon.setVisibility(View.INVISIBLE);

    }
    private void setTextEdit(View v){
        TextView title = v.findViewById(R.id.textView24);
        Button submit = v.findViewById(R.id.button_create_submit);
        FloatingActionButton attach = v.findViewById(R.id.floatingActionButton_create_attach);
        EditText details = v.findViewById(R.id.editText_create_report_info);

        title.setVisibility(View.INVISIBLE);
        details.setEnabled(true);
        submit.setVisibility(View.VISIBLE);
        attach.setClickable(true);

        attach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(admin_report_files.this, "ATTACHED FILES CAN'T BE EDITED", Toast.LENGTH_SHORT).show();
            }
        });



        editSubmit(v);

    }

    private void editSubmit(View v){
        Button submit = v.findViewById(R.id.button_create_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText details = v.findViewById(R.id.editText_create_report_info);
                setTextDefI(v);
                showEdit(v);
                db.updateIReport(details.getText().toString(),queryString);

            }
        });
    }


    private void setInfoI(String queryString, View v){
        TextView officer = v.findViewById(R.id.textView_create_officer);
        TextView sector = v.findViewById(R.id.textView_create_sector);
        TextView date = v.findViewById(R.id.textView_create_date);
        TextView email = v.findViewById(R.id.textView_create_email);
        EditText details = v.findViewById(R.id.editText_create_report_info);
        LinearLayout linearLayout = v.findViewById(R.id.linear_layout);

        db = new database(this);
        TextView textView = new TextView(this);
        textView.setText(db.getReportAttached(queryString));
        linearLayout.addView(textView);


        details.setText(db.getReportStatement(queryString));
        officer.setText(db.getReportOfficer(queryString));
        sector.setText(db.getReporSector(queryString));
        date.setText(db.getReportDate(queryString));
        email.setText(db.getReportEmail(queryString));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, admin_homepage.class);
        intent.putExtras(getIntent());
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    private void showEdit(View v){
        TextView text1 = v.findViewById(R.id.textView_edit_on);
        TextView editon = v.findViewById(R.id.textView_ireport_edit_date);
        admin_create_report report = new admin_create_report();

        text1.setVisibility(View.VISIBLE);
        editon.setVisibility(View.VISIBLE);

        String user = getIntent().getStringExtra("user");
        editon.setText(report.setDate());
    }

    //incident reports
    private void EditRes(View viewI){
        String user = getIntent().getStringExtra("user");
        String cUser = ""+db.getName(user)+" ("+db.getIdNo(user)+")";
        String oUser = db.getReportOfficer(queryString);
        if (cUser.equals(oUser)){
            setTextEdit(viewI);
        }
        else
            Toast.makeText(admin_report_files.this, "ONLY THE OWNER CAN EDIT THIS REPORT", Toast.LENGTH_SHORT).show();
    }
    private void DelRes(AlertDialog alertDialog){
        String user = getIntent().getStringExtra("user");
        String cUser = ""+db.getName(user)+" ("+db.getIdNo(user)+")";
        String oUser = db.getReportOfficer(queryString);
        if (cUser.equals(oUser)){
            //delete ENTER CODE BUILDER
            alertDialog.dismiss();
            delBuilder(alertDialog);
        }
        else{
            Toast.makeText(admin_report_files.this, "ONLY THE OWNER CAN DELETE THIS REPORT", Toast.LENGTH_SHORT).show();
        }


    }

    private void delBuilder(AlertDialog alertDialog){

        View viewD = getLayoutInflater().inflate(R.layout.admin_delete_code, null);

        EditText code = viewD.findViewById(R.id.textView_del_code);

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("ENTER CHIEF CODE")
                .setView(viewD)
                .setMessage("Pls obtain a code from your chief officer to be able to delete this report. Any deleted report can't be retrieved.")
                .setCancelable(false)
                .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.show();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alertDialog2 = builder1.create();
        alertDialog2.show();
        alertDialog2.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = code.getText().toString();
                String sector = db.getChiefCode(db.getPnpSector(admin_login.admin_code));
                if(input.equals(sector)) {
                    db.DeleteIreport(queryString);
                    Toast.makeText(admin_report_files.this, "INCIDENT REPORT DELETED", Toast.LENGTH_SHORT).show();
                    arrayAdapterI.remove(queryString);
                    alertDialog2.dismiss();
                }
                else{
                    Toast.makeText(admin_report_files.this, "INVALID CODE", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}