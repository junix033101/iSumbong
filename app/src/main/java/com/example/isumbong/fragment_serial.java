package com.example.isumbong;

import static com.example.isumbong.fragment_accident_info.img_accident;
import static com.example.isumbong.fragment_accident_info.img_license;
import static com.example.isumbong.public_report_now.victimsid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.Random;


public class fragment_serial extends Fragment {


    TextView serial;
    database db ;
    INPUTS input;
    public fragment_serial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_serial, container, false);

        serial = view.findViewById(R.id.textView_serial);


                String Serial = Serial();
                serial.setText(Serial);
                SerialDB(Serial,public_report_now.date);
                ReportDB(Serial);

        public_report_now.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect
                Intent intent = new Intent( getActivity(), public_homepage.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                requireActivity().finish();

                reset();


            }
        });

        return view;
    }
    private void reset(){
        public_report_now.state =  0;
        img_accident = null;
        img_license = null;
        fragment_vehicle_info.img_vehicle = null;
        fragment_vehicle_info.img_or = null;
//        fragment_vehicle_info.SpinnerPos = 0;

        SharedPreferences sharedPref = getActivity().getSharedPreferences("FileName", Context.MODE_PRIVATE);

        SharedPreferences.Editor prefEditor = sharedPref.edit();
        prefEditor.putInt("userChoiceSpinner",0);
        prefEditor.apply();


    }
    private String Serial(){
        String zeros = "00000000";
        Random rnd = new Random();
        String s = Integer.toString(rnd.nextInt(0X1000000), 16);
        s = zeros.substring(s.length()) + s;
        return s;
    }

    private void SerialDB(String Serial, String date){
        db = new database(requireContext());
        boolean check = db.InsertSerial(victimsid, Serial, date);
        if (check) {
            Toast.makeText(requireContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show();
    }
    private void ReportDB(String Serial){
        db = new database(requireContext());
        String lic = getArguments().getString("lic");
        db.InsertOffenses(lic, public_report_now.date, Serial);

    }
}