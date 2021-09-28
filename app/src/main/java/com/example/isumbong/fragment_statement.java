package com.example.isumbong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class fragment_statement extends Fragment {

       static EditText statement_field;
       static String Statement;


    public fragment_statement() {
        // Required empty public constructor
    }
    public fragment_statement(String Statement) {

        this.Statement = Statement;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statement, container, false);

        statement_field = view.findViewById(R.id.editTextTextMultiLine_statement);

        //statement.setText(MessageFormat.format("{0}{1}{2}", getLocation, Double.toString(getCoordinatesLat), Double.toString(getCoordinatesLng)));

        statement_field.setText(Statement);

        return view;
    }
}