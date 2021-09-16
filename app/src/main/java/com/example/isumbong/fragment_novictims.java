package com.example.isumbong;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rakshakhegde.stepperindicator.StepperIndicator;

import java.util.Objects;


public class fragment_novictims extends Fragment {

    public fragment_novictims() {
        // Required empty public constructor
    }

    Fragment fragment;
    FragmentTransaction ft;
    Button ok ;
    EditText number;
    static public String vnum;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    String plate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_novictims, container, false);

        ok = view.findViewById(R.id.button_no_ok);
        number = view.findViewById(R.id.Text_number);
        //prefs = requireActivity().getSharedPreferences("mypref",0);



            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //get input
                    vnum = number.getText().toString();
                    //pref
//                    edit = prefs.edit().putString("number",vnum);
//                    edit.apply();





                    if(vnum.matches("")){
                        Toast.makeText(getActivity().getBaseContext(), "Empty field!",Toast.LENGTH_SHORT).show();
                    }
                    else if (vnum.matches("0"))
                        Toast.makeText(getActivity().getBaseContext(), "Invalid input",Toast.LENGTH_SHORT).show();
                    else{
                        ft = getFragmentManager().beginTransaction();
                        fragment = new fragment_victim_details();
                        ft.add(R.id.fragment_container,fragment,"details")
                                .addToBackStack("details")
                                .commit();
                        //button
//                        ok.setVisibility(View.GONE);
                        public_report_now.next.show();
                        public_report_now.prev.show();
                    }

                }
            });





        return view;

    }

}