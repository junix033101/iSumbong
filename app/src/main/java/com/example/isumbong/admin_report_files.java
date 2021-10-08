package com.example.isumbong;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class admin_report_files extends AppCompatActivity {
    static RadioButton vReport;
    static RadioButton iReport;
    database db = new database(this);
    ArrayAdapter<String> arrayAdapterI;
    ArrayAdapter<String> arrayAdapterV;
   androidx.appcompat.widget.SearchView searchView;

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
//        getArrayItemI(list);
    }
    private void setvReportList(){
        ListView list = findViewById(R.id.listView_report_files);
        ArrayList<String> vSerial = new ArrayList<>();
        vSerial = db.getVerifiedSerial();
        arrayAdapterV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,vSerial);
        list.setAdapter(arrayAdapterV);
        //get Item
        OnClickI(list);
    }

    private void getOnClick(){
        vReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(vReport.isChecked())
                setvReportList();

            }
        });
        iReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(iReport.isChecked())
                setiReportList();
            }
        });
    }

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
    private void OnClickI(ListView list){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String queryString =(String)adapterView.getItemAtPosition(i);
                getArrayItemV(queryString);
            }
        });

    }

    private void getArrayItemV(String queryString){

        admin_create_report Abuilder = new admin_create_report();
        //GET VICTIMS ID BASED ON VSERIAL
        int id = Abuilder.getInfoVserial(queryString);

        View viewS = getLayoutInflater().inflate(R.layout.admin_builder_attached_serial, null);
        Abuilder.setNumber(id,viewS);
        Abuilder.setVictimDetais(id,viewS);
        Abuilder.setImgs(id,viewS);
        Abuilder.Infos(id,viewS);

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

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}