package com.example.isumbong;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class fragment_vehicle_info extends Fragment {

    public fragment_vehicle_info() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vehicle_info, container, false);

        String[] type = {"Select Vehicle Type","3 WHEELER", "4 WHEELER", "MOTORCYCLE", "BUS", "TRUCK"};

        Spinner vehicle = view.findViewById(R.id.spinner_type);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1, type);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicle.setAdapter(myAdapter);

        vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selecteditem = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getActivity().getBaseContext(), ""+selecteditem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
}