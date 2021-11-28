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

public class admin_report_files extends AppCompatActivity{
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

//        getOnClick();
        setIntent();

    }
    private void setIntent(){


        selected = getIntent().getIntExtra("selected",0);

        switch (selected){
            case 1: vReport.setChecked(true);

                break;

            case 2: iReport.setChecked(true);

                break;
            default:break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, admin_homepage.class);
        intent.putExtras(getIntent());
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }



}