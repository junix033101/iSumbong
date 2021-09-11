package com.example.isumbong;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class fragment_novictims extends Fragment {

    public fragment_novictims() {
        // Required empty public constructor
    }

    Fragment fragment;
    FragmentTransaction ft;
    Button ok ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_novictims, container, false);

        EditText no_victims = view.findViewById(R.id.Text_no_victims);
        ok = view.findViewById(R.id.button_no_ok);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getBaseContext(),"Password does not match", Toast.LENGTH_SHORT).show();


                ft = getFragmentManager().beginTransaction();
                fragment = new fragment_victim_details();
                ft.replace(R.id.fragment_container,fragment,"details")
                        .addToBackStack("details")
                        .commit();
                ok.setVisibility(View.GONE);
//                public_report_now.state++;
//                public_report_now.fuck(state);
                public_report_now.next.show();
                public_report_now.prev.show();


            }
        });


        return view;
    }
}