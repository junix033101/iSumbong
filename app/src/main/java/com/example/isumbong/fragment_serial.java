package com.example.isumbong;

import static com.example.isumbong.fragment_accident_info.img_accident;
import static com.example.isumbong.fragment_accident_info.img_license;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;


public class fragment_serial extends Fragment {

    ImageButton home;

    public fragment_serial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_serial, container, false);

        home = view.findViewById(R.id.imageButton_home);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(), public_homepage.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                requireActivity().finish();
                //reset
                public_report_now.state =  0;
                img_accident = null;
                img_license = null;

            }
        });


        return view;
    }
}