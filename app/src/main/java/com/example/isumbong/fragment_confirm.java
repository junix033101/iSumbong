package com.example.isumbong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class fragment_confirm extends Fragment {


    public fragment_confirm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);


//        AlertDialog.Builder builder1 = new AlertDialog.Builder(public_report_now.this);
//                        builder1.setTitle("CONFRIMATION")
//                                .setCancelable(false)
//                                .setMessage("Please confirm all inputted data");
//                        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                state -=1;
//                                stpi.setCurrentStep(state);
//                                getSupportFragmentManager().popBackStack();
//
//                            }
//                        });
//                        builder1.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if(fragment !=null){
//                                    fragment = new fragment_serial();
//                                    ft = getSupportFragmentManager().beginTransaction();
//                                    ft.replace(R.id.fragment_container, fragment)
//                                            .addToBackStack(null)
//                                            .commit();
//                                    //state+=1;
//                                    stpi.setCurrentStep(state+1);
//                                    next.hide();
//                                    prev.hide();
//                                }
//
//                                    }
//                                });
//                        AlertDialog alertDialog = builder1.create();
//                        alertDialog.show();
//                    }
//

        return view;
    }
}