package com.example.isumbong;

import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class admin_report_files extends AppCompatActivity {
    static RadioButton vReport;
    static RadioButton iReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report_files);

        int selected = getIntent().getIntExtra("selected",0);

        vReport = findViewById(R.id.radioButton_verified_report);
        iReport = findViewById(R.id.radioButton_incident_report);

        switch (selected){
            case 1: vReport.setChecked(true);break;
            case 2:iReport.setChecked(true);break;
        }

    }
}