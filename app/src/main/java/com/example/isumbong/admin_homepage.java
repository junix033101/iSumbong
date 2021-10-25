package com.example.isumbong;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class admin_homepage extends AppCompatActivity {

    MenuItem menuItem;
    SearchView searchView;
    SearchView.SearchAutoComplete searchAutoComplete;

    static ArrayList<String> serials = new ArrayList<>();
    static ArrayAdapter<String> arrayAdapter;

    database db;

    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        add = findViewById(R.id.button_admin_create);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddReport();
            }
        });

        openReportFiles();
        openAccount();
        openFiles();
        RecentAccident();
        RecentVerified();
        RecentReport();

    }

        //here lies search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //set search bar
        getMenuInflater().inflate(R.menu.activity_admin_search_bar,menu);
        menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();

        searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setDropDownBackgroundResource(R.color.dropdownDARK);

        //set suggestions
        db = new database(this);
        serials = db.getSerial();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,serials);
        searchAutoComplete.setAdapter(arrayAdapter);
        searchView.setQueryHint("Search Serial Code");
//        searchView.setSubmitButtonEnabled(true);

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
//                Toast.makeText(admin_homepage.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(admin_homepage.this, admin_view_serial.class);
                intent.putExtra("clicked_serial", queryString);
                intent.putExtras(getIntent());
                startActivity(intent);

            }
        });
        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                AlertDialog alertDialog = new AlertDialog.Builder(admin_homepage.this).create();
//                alertDialog.setMessage("Search keyword is " + query);
//                alertDialog.show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    private void openAddReport(){
        Intent intent = new Intent(this, admin_create_report.class);
        intent.putExtras(getIntent());
        startActivity(intent);
    }

    private void openReportFiles(){
        ImageButton report = findViewById(R.id.imageButton_reports);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_homepage.this, admin_report_files.class);
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });
    }
    private void openAccount(){
        ImageButton acc = findViewById(R.id.imageButton_account);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_homepage.this, admin_account.class);
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });

    }
    private void openFiles(){
        ImageButton files = findViewById(R.id.imageButton_files);
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_homepage.this, admin_dl_files.class);
                intent.putExtras(getIntent());
                startActivity(intent);
            }
        });

    }
    private void RecentAccident(){
        db = new database(this);
        TextView location = findViewById(R.id.textView_cardview_loc);
        TextView date = findViewById(R.id.textView_cardview_date);
        TextView victims = findViewById(R.id.textView_cardview_victims);
        ImageView acc_img = findViewById(R.id.imageView_cardview_acc);

        TextView title_d = findViewById(R.id.textView202);
        TextView title_l = findViewById(R.id.textView204);
        TextView title_v = findViewById(R.id.textView203);
        TextView null_a = findViewById(R.id.textView_null_acc);

        TextView serial = findViewById(R.id.textView_title_acc);

        boolean check = db.CheckAccident();
        if(check){
            acc_img.setVisibility(View.VISIBLE);
            title_d.setVisibility(View.VISIBLE);
            title_l.setVisibility(View.VISIBLE);
            title_v.setVisibility(View.VISIBLE);
            date.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            victims.setVisibility(View.VISIBLE);
            null_a.setVisibility(View.INVISIBLE);

            try {
                Cursor latestAccident=db.getRecentReport();
                int id = latestAccident.getInt(1);
                date.setText(latestAccident.getString(3));
                location.setText(db.getLocation(id));
                ArrayList<Victim> vic = db.getVictimsInfo(id);
                String names="";
                for(Victim v: vic ){
                    names += v.getName()+"\n";
                }
                victims.setText(names);

                acc_img.setImageURI(Uri.parse(db.getAccidentImg(id)));
                serial.setText(latestAccident.getString(2));

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else{

            acc_img.setVisibility(View.INVISIBLE);
            title_d.setVisibility(View.INVISIBLE);
            title_l.setVisibility(View.INVISIBLE);
            title_v.setVisibility(View.INVISIBLE);
            date.setVisibility(View.INVISIBLE);
            location.setVisibility(View.INVISIBLE);
            victims.setVisibility(View.INVISIBLE);
            null_a.setVisibility(View.VISIBLE);

        }

    }
    private void RecentVerified(){
        TextView location = findViewById(R.id.textView_cardview_Vloc);
        TextView date = findViewById(R.id.textView_cardview_Vdate);
        TextView victims = findViewById(R.id.textView_cardview_Vvictims);
        ImageView acc_img = findViewById(R.id.imageView_cardview_Vacc);

        TextView vd = findViewById(R.id.textView46);
        TextView vl = findViewById(R.id.textView48);
        TextView vv = findViewById(R.id.textView50);
        TextView null_v = findViewById(R.id.textView_null_Vreport);
        TextView serialv = findViewById(R.id.textView_title_v);

        boolean check = db.CheckVerified();
        if(check){
            try {
                acc_img.setVisibility(View.VISIBLE);
                vd.setVisibility(View.VISIBLE);
                vl.setVisibility(View.VISIBLE);
                vv.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                victims.setVisibility(View.VISIBLE);
                null_v.setVisibility(View.INVISIBLE);

                int id = 0;
                String names;
                if (db!=null) {
                    Cursor latestVerified=db.getRecentVerified();
                    id = latestVerified.getInt(1);
                    date.setText(latestVerified.getString(3));
                    location.setText(db.getLocation(id));
                    ArrayList<Victim> vic = db.getVictimsInfo(id);
                    names = "";
                    for(Victim v: vic ){
                        names += v.getName()+"\n";
                    }
                    victims.setText(names);
                    acc_img.setImageURI(Uri.parse(db.getAccidentImg(id)));
                    serialv.setText(latestVerified.getString(2));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            acc_img.setVisibility(View.INVISIBLE);
            vd.setVisibility(View.INVISIBLE);
            vl.setVisibility(View.INVISIBLE);
            vv.setVisibility(View.INVISIBLE);
            date.setVisibility(View.INVISIBLE);
            location.setVisibility(View.INVISIBLE);
            victims.setVisibility(View.INVISIBLE);
            null_v.setVisibility(View.VISIBLE);
        }




    }
    private void RecentReport(){
        TextView officer= findViewById(R.id.textView_cardview_officer);
        TextView date = findViewById(R.id.textView_cardview_Idate);
        TextView sector = findViewById(R.id.textView_cardview_sector);
        TextView details = findViewById(R.id.textView_cardview_info);

        TextView idate = findViewById(R.id.textView52);
        TextView io = findViewById(R.id.textView55);
        TextView is = findViewById(R.id.textView57);
        TextView null_i = findViewById(R.id.textView_null_Ireport);
        TextView seriali = findViewById(R.id.textView_title_i);

        boolean check = db.CheckIncident();
        if(check){
            try {
                date.setVisibility(View.VISIBLE);
                officer.setVisibility(View.VISIBLE);
                sector.setVisibility(View.VISIBLE);
                idate.setVisibility(View.VISIBLE);
                io.setVisibility(View.VISIBLE);
                is.setVisibility(View.VISIBLE);
                details.setVisibility(View.VISIBLE);
                null_i.setVisibility(View.INVISIBLE);

                if (db!=null) {
                    Cursor latestIncedent=db.getRecentIncedent();
                    officer.setText(latestIncedent.getString(3));
                    sector.setText(latestIncedent.getString(5));
                    details.setText(latestIncedent.getString(7));
                    seriali.setText(latestIncedent.getString(1));
                    boolean check1 = db.Checker(latestIncedent.getString(1));
                    if(check1){
                        String dateEdit = db.getEditDate(latestIncedent.getString(1));
                        date.setText(dateEdit);
                    }
                    else
                        date.setText(latestIncedent.getString(6));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{

            date.setVisibility(View.INVISIBLE);
            officer.setVisibility(View.INVISIBLE);
            sector.setVisibility(View.INVISIBLE);
            idate.setVisibility(View.INVISIBLE);
            io.setVisibility(View.INVISIBLE);
            is.setVisibility(View.INVISIBLE);
            details.setVisibility(View.INVISIBLE);
            null_i.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(admin_homepage.this, admin_login.class);
                        intent.putExtras(getIntent());
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void setRecentAcc(){

    }




}