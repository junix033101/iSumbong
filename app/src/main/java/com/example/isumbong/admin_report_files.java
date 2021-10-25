package com.example.isumbong;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import java.io.File;
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
    AlertDialog alertDialog;
    String edited;


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
        TextView empty = findViewById(R.id.textView_def);
        ArrayList<String> rSerial = new ArrayList<>();
        rSerial = db.getReportSerial();
        arrayAdapterI = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,rSerial);
        list.setAdapter(arrayAdapterI);
        list.setEmptyView(empty);
//        get ItemI
        OnClickI(list);
    }
    private void setvReportList(){
        ListView list1 = findViewById(R.id.listView_report_files1);
        TextView empty = findViewById(R.id.textView_def);
        ArrayList<String> vSerial = new ArrayList<>();
        vSerial = db.getVerifiedSerial();
        arrayAdapterV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,vSerial);
        list1.setAdapter(arrayAdapterV);
        list1.setEmptyView(empty);
        //get Item
        OnClickV(list1);
    }

    //radiobutton
    private void getOnClick(){
        if(!vReport.isChecked()&& !iReport.isChecked()){
            ListView list = findViewById(R.id.listView_report_files);
            TextView empty = findViewById(R.id.textView_def);
            list.setEmptyView(empty);
        }
        vReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(vReport.isChecked()){
                    ListView list = findViewById(R.id.listView_report_files);
                    ListView list1 = findViewById(R.id.listView_report_files1);
                    TextView empty = findViewById(R.id.textView_def);
                    list1.setVisibility(View.VISIBLE);
                    list.setVisibility(View.INVISIBLE);
                    list1.setEmptyView(empty);
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
                    TextView empty = findViewById(R.id.textView_def);
                    list1.setVisibility(View.INVISIBLE);
                    list.setVisibility(View.VISIBLE);
                    list.setEmptyView(empty);
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

        ArrayList<String> offenses = db.getOffenses(db.getLicenseNumber(id));
        ArrayAdapter<String> adapterOff = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, offenses);

        ArrayList<String> reports_list = db.getReports(db.getLicenseNumber(id));
        ArrayAdapter<String> adapter_rep = new ArrayAdapter<> (this, android.R.layout.simple_dropdown_item_1line, reports_list);
        ImageButton viewBtn = viewS.findViewById(R.id.imageButton_vreport_offenses);
        View viewB = getLayoutInflater().inflate(R.layout.builder_offenses, null);
        Context ctx = admin_report_files.this;


        setNumber(id,viewS);
        setVictimDetais(id,viewS);
        setInfo(id,viewS);
        setImgs(id,viewS);
        offensesButton(viewBtn,viewB, id,adapterOff, adapter_rep,ctx);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("VERIFIED REPORT ( " +queryString + " )")
                .setView(viewS)
                .setCancelable(false)
                .setNeutralButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("DOWNLOAD", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pdf(id, queryString);

                    }
                })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        alertDialog = builder.create();
        MapView(viewS, alertDialog);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
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

        ImageButton edit = viewI.findViewById(R.id.imageButton_edit_report);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditRes(viewI);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("INCIDENT REPORT ( " +queryString + " )")
                .setView(viewI)
                .setCancelable(false)
                .setPositiveButton("DOWNLOAD", new DialogInterface.OnClickListener() {
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
        AlertDialog alertDialog2 = builder.create();
        alertDialog2.show();
        alertDialog2.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DelRes(alertDialog2);
            }
        });
        alertDialog2.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = getIntent().getStringExtra("user");
                boolean fCheck = db.CheckerFile(queryString, user);
                if(fCheck) {
                    alertDialog2.dismiss();
                    AlertDialog.Builder file = new AlertDialog.Builder(admin_report_files.this);
                    file.setTitle("CONFIRM DOWNLOAD")
                            .setMessage("" + queryString + " already exists. Do you want to replace it?")
                            .setCancelable(false)
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    alertDialog2.show();
                                }
                            })
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    pdfIncident(queryString);
                                }
                            }).show();
                }
                else
                    pdfIncident(queryString);
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

        boolean check = db.Checker(queryString);
        if(check){
            editon.setVisibility(View.VISIBLE);
            text1.setVisibility(View.VISIBLE);
            editon.setText(db.getEditDate(queryString));
        }
        else{
            editon.setVisibility(View.INVISIBLE);
            text1.setVisibility(View.INVISIBLE);
        }

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

                boolean check = db.Checker(queryString);
                Log.e("CHECK", ""+check);
                        if(check){
                            db.updateEdit(queryString,edited);
                            Toast.makeText(admin_report_files.this,"UPDATED", Toast.LENGTH_SHORT ).show();
                        }
                        else{
                            boolean checkupdate = db.InsertEdits(queryString,db.getReportOfficer(queryString),edited);
                            if(checkupdate)
                                Toast.makeText(admin_report_files.this,"UPDATED", Toast.LENGTH_SHORT ).show();
                        }

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

        editon.setText(report.setDate());
        edited = report.setDate();
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
                    db.deleteEdit(queryString);
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

    void pdf(int id,String queryString){
        AbstractViewRenderer page = new AbstractViewRenderer(this, R.layout.test) {

            @Override
            protected void initView(View view) {
                setNumber(id,view);
                setVictimDetais(id,view);
                page1(id,view);
            }
        };
        AbstractViewRenderer page2 = new AbstractViewRenderer(this, R.layout.page2) {
            @Override
            protected void initView(View view) {
                page2(id,view);
            }
        };
        AbstractViewRenderer page3 = new AbstractViewRenderer(this, R.layout.page3) {
            @Override
            protected void initView(View view) {
                page3(id,view);
            }
        };
        AbstractViewRenderer page4 = new AbstractViewRenderer(this, R.layout.page4) {
            @Override
            protected void initView(View view) {
                page4(id,view);
            }
        };
        AbstractViewRenderer page5 = new AbstractViewRenderer(this, R.layout.page5) {
            @Override
            protected void initView(View view) {
                page5(id,view);
            }
        };

// you can reuse the bitmap if you want
        page.setReuseBitmap(true);
        page2.setReuseBitmap(true);
        page3.setReuseBitmap(true);
        page4.setReuseBitmap(true);
        page5.setReuseBitmap(true);
        PdfDocument doc = new PdfDocument(this);

// add as many pages as you have
        doc.addPage(page);
        doc.addPage(page2);
        doc.addPage(page3);
        doc.addPage(page4);
        doc.addPage(page5);

        doc.setRenderWidth(page.getWidth());
        doc.setRenderHeight(3000);
        doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
        doc.setProgressTitle(R.string.title);
        doc.setProgressMessage(R.string.mssg);
        doc.setFileName(queryString);
        doc.setSaveDirectory(this.getExternalFilesDir(null));

        String path = this.getExternalFilesDir(null)+"/"+queryString+".pdf";

        doc.setInflateOnMainThread(false);
        doc.setListener(new PdfDocument.Callback() {
            @Override
            public void onComplete(File file) {
                Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                Intent intent = new Intent(admin_report_files.this, admin_dl_files.class);
                intent.putExtras(getIntent());
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                String user = getIntent().getStringExtra("user");
                //insert database
                boolean fCheck = db.CheckerFile(queryString, user);
                if(fCheck){
                    db.updateFile(queryString,user);
                    Toast.makeText(admin_report_files.this, "PDF file downloaded", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean check = db.InsertPDFs(queryString,user,path);
                    if(check){
                        Toast.makeText(admin_report_files.this, "PDF file downloaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
            }
        });

        doc.createPdf(this);
    }

    private void page1(int ID, View v){
        ImageView a = v.findViewById(R.id.imageView_confirm_accident);
        a.setImageURI(Uri.parse(db.getAccidentImg(ID)));
        TextView date = v.findViewById(R.id.textView_date);
        date.setText(db.getDate(ID));
    }
    private void page2 (int ID,View v){
        ImageView l = v.findViewById(R.id.imageView_confirm_license);
        l.setImageURI(Uri.parse(db.getLicenseImg(ID)));
        TextView plate = v.findViewById(R.id.textView_confirm_plate);
        plate.setText(db.getPlateNumber(ID));
        TextView type = v.findViewById(R.id. textView_confirm_vehicletype);
        type.setText(db.getVehicleType(ID));

        TextView license = v.findViewById(R.id.textView_confirm_licensenum2);
        license.setText(db.getLicenseNumber(ID));

    }
    private void page3(int ID, View v){
        ImageView c = v.findViewById(R.id.imageView_confirm_vehicle2);
        c.setImageURI(Uri.parse(db.getVehicleImg(ID)));
        ImageView o = v.findViewById(R.id.imageView_confirm_or);
        o.setImageURI(Uri.parse(db.getOrImg(ID)));

    }
    private void page4(int ID, View v){
        TextView loc = v.findViewById(R.id.textView_location);
        String lat= Double.toString(db.getLatitude(ID));
        String lng = Double.toString(db.getLongitude(ID));
        String pin = db.getLocation(ID);
        String address = pin+"\nLATITUDE: "+lat+"\nLONGITUDE: "+lng;
        loc.setText(address);

        TextView statement = v.findViewById(R.id.textView_confirm_statement);

        statement.setText(db.getStatement(ID));

    }
    private void page5(int ID, View v){
        TextView vdate = v.findViewById(R.id.textView47);
        TextView officer = v.findViewById(R.id.textView_page_verified);

        vdate.setText(db.getVdate(ID));
        officer.setText(""+db.getName(db.getUser(ID))+" ("+db.getIdNo(db.getUser(ID))+")");
    }

    void pdfIncident(String queryString){
        AbstractViewRenderer page1;
        String sector = db.getPnpSector(admin_login.admin_code);
        if(sector.equals("Cebu Provincial Jail")){
             page1 = new AbstractViewRenderer(this, R.layout.cebu_city_jail) {

                @Override
                protected void initView(View view) {
                    page1_CCJ(queryString,view);
                }
            };
        }else{
            page1 = new AbstractViewRenderer(this, R.layout.citom_page1) {

                @Override
                protected void initView(View view) {
                    page1_CCJ(queryString,view);
                }
            };
        }

        AbstractViewRenderer page2 = new AbstractViewRenderer(this, R.layout.cebu_city_jail_page2) {
            @Override
            protected void initView(View view) {
                page2_CCJ(queryString,view);
            }
        };


// you can reuse the bitmap if you want
        page1.setReuseBitmap(true);
        page2.setReuseBitmap(true);

        PdfDocument doc = new PdfDocument(this);

// add as many pages as you have
        doc.addPage(page1);
        doc.addPage(page2);


        doc.setRenderWidth(page1.getWidth());
        doc.setRenderHeight(page1.getHeight());
        doc.setOrientation(PdfDocument.A4_MODE.PORTRAIT);
        doc.setProgressTitle(R.string.title);
        doc.setProgressMessage(R.string.mssg);
        doc.setFileName(queryString);
        doc.setSaveDirectory(this.getExternalFilesDir(null));

        Log.e("PATH:", ""+this.getExternalFilesDir(null)+"/"+queryString+".pdf");
        String path = this.getExternalFilesDir(null)+"/"+queryString+".pdf";

        doc.setInflateOnMainThread(false);
        doc.setListener(new PdfDocument.Callback() {
            @Override
            public void onComplete(File file) {
                Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
                Intent intent = new Intent(admin_report_files.this, admin_dl_files.class);
                intent.putExtras(getIntent());
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                String user = getIntent().getStringExtra("user");

                //insert database
                boolean fCheck = db.CheckerFile(queryString, user);
                if(fCheck){
                    db.updateFile(queryString,user);
                    Toast.makeText(admin_report_files.this, "PDF file downloaded", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean check = db.InsertPDFs(queryString,user,path);
                    if(check){
                        Toast.makeText(admin_report_files.this, "PDF file downloaded", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onError(Exception e) {
                Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
            }
        });

        doc.createPdf(this);
    }

    private void page1_CCJ(String queryString, View v){
        TextView officer = v.findViewById(R.id.textView_create_officer);
        TextView sector = v.findViewById(R.id.textView_create_sector);
        TextView date = v.findViewById(R.id.textView_create_date);
        TextView email = v.findViewById(R.id.textView_create_email);
        EditText details = v.findViewById(R.id.editText_create_report_info);

        details.setHint(db.getReportStatement(queryString));
        officer.setText(db.getReportOfficer(queryString));
        sector.setText(db.getReporSector(queryString));
        date.setText(db.getReportDate(queryString));
        email.setText(db.getReportEmail(queryString));
    }
    private void page2_CCJ(String queryString, View v){

        LinearLayout linearLayout = v.findViewById(R.id.linear_layout);

        db = new database(this);
        TextView textView = new TextView(this);
        textView.setText(db.getReportAttached(queryString));
        linearLayout.addView(textView);

        TextView date = v.findViewById(R.id.textView_ireport_edit_date);
        boolean check = db.Checker(queryString);
        if(check){
            date.setText(db.getEditDate(queryString));
        }
    }
    public void offensesButton(ImageButton viewBtn, View viewB, int ID, ArrayAdapter adapterOff, ArrayAdapter adapter_rep, Context ctx){


        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                admin_view_serial dialog = new admin_view_serial();
                 Log.e("CHECK", "button is working");

                dialog.radioBtn(viewB,ID,adapterOff, adapter_rep);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
                builder1.setTitle("LICENSE INFORMATION")
                        .setView(viewB)
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



}