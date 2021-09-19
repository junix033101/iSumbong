package com.example.isumbong;

import static com.example.isumbong.MapsFragment.getCoordinatesLat;
import static com.example.isumbong.MapsFragment.getCoordinatesLng;
import static com.example.isumbong.MapsFragment.getLocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.text.MessageFormat;


public class fragment_statement extends Fragment {

       EditText statement;
       String Statement;


    public fragment_statement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statement, container, false);

        statement = view.findViewById(R.id.editTextTextMultiLine_statement);

        statement.setText(MessageFormat.format("{0}{1}{2}", getLocation, Double.toString(getCoordinatesLat), Double.toString(getCoordinatesLng)));

        Statement = statement.getText().toString();

        return view;
    }
}