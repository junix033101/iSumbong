package com.example.isumbong;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class public_report_now extends AppCompatActivity {

    static int state = 0;
    FragmentTransaction ft;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    Fragment fragment;
    static FloatingActionButton next;
    static FloatingActionButton prev;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_report_now);
//
//        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                Toast.makeText(public_report_now.this, "COunt"+getSupportFragmentManager().getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
//            }
//        });

        StepsView mStepsView = findViewById(R.id.stepsView);

        String[] steps = {"VICTIMS\nINFO", "ACCIDENT\nINFO", "VEHICLE\nINFO", "LOC", "STATE","CONFIRM", "FINISH"};
        mStepsView.setLabels(steps)
                .setBarColorIndicator(public_report_now.this.getResources().getColor(R.color.material_blue_grey_800))
                .setProgressColorIndicator(public_report_now.this.getResources().getColor(R.color.green))
                .setLabelColorIndicator(public_report_now.this.getResources().getColor(android.R.color.holo_green_dark))
                .setCompletedPosition(state)
                .drawView();

        next = findViewById(R.id.button);
        prev = findViewById(R.id.button_prev);
        Button ok = findViewById(R.id.button_no_ok);
//        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) next.getLayoutParams();
//        p.setBehavior(null); //should disable default animations
//        p.setAnchorId(View.NO_ID); //should let you set visibility
//        next.setLayoutParams(p);


        //DEFAULT FRAG

        numvitims();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (state < (steps.length-1)) {

//                    if (fragment instanceof fragment_novictims) {
//                        state = 0;
//                        mStepsView.setCompletedPosition(state).drawView();
//                        Toast.makeText(public_report_now.this, "Input no. of victims!", Toast.LENGTH_SHORT).show();
//                    }
                        state = state + 1;
                        mStepsView.setCompletedPosition(state).drawView();
                        Toast.makeText(public_report_now.this, "STATE"+state, Toast.LENGTH_SHORT).show();


//                        if (fragment instanceof fragment_novictims){
//                            fragment = new fragment_victim_details();
//
//                        }

//                    if (state == 2) {
                        if (fragment instanceof fragment_victim_details){
                            fragment = new fragment_accident_info();

                        }
//                    } else if (state == 3) {
                        else if (fragment instanceof fragment_accident_info)
                            fragment = new fragment_vehicle_info();
//                    } else if (state == 4) {
                        else if (fragment instanceof fragment_vehicle_info)
                            fragment = new fragment_location();
                        else if(fragment instanceof fragment_location){
                            fragment = new fragment_statement();
                        }
                        else if(fragment instanceof fragment_statement){
                            Toast.makeText(public_report_now.this, "build a bitch", Toast.LENGTH_SHORT).show();
                        }
                        if (state == 6)
                            fragment = new fragment_serial();



//                    } else if (state == 6) {
//                        else
//                            fragment = new fragment_serial();


//                    }

                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                }
//                else if(state == steps.length-1){
//                    next.setVisibility(View.GONE);
//                    prev.setVisibility(View.GONE);
//                }

            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (state >= -1) {

                    if(state==0){
                        next.hide();
                        prev.hide();
                    }


                    else{
                        state = state - 1;
                        Toast.makeText(public_report_now.this, "STATE"+state, Toast.LENGTH_SHORT).show();
                        mStepsView.setCompletedPosition(state).drawView();
                    }





                }
//                if(getSupportFragmentManager().findFragmentByTag("details")!=null){
//                    Toast.makeText(public_report_now.this, "POTAAAAA", Toast.LENGTH_SHORT).show();
//                    state = 1;
//                    mStepsView.setCompletedPosition(0).drawView();
//                    Toast.makeText(public_report_now.this, "STATE"+state, Toast.LENGTH_SHORT).show();
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.remove(getSupportFragmentManager().findFragmentByTag("details")).commit();
//                   // getSupportFragmentManager().popBackStack("details",FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                }
                getSupportFragmentManager().popBackStack();




//                if(fragment instanceof fragment_victim_details){
//                    state = 1;
//                    Toast.makeText(public_report_now.this, "POTAAAAA", Toast.LENGTH_SHORT).show();
//
////                       Intent intent = new Intent(public_report_now.this,public_homepage.class);
////                       startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP));
//                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        ft = getSupportFragmentManager().beginTransaction();
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment instanceof fragment_novictims){
            Intent intent = new Intent(public_report_now.this, public_homepage.class);
            startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP));
        }
        else
            super.onBackPressed();
    }

    public void numvitims(){
        ft = getSupportFragmentManager().beginTransaction();
        fragment_novictims fnum = new fragment_novictims();
        ft.replace(R.id.fragment_container, fnum, "fnum")
                .addToBackStack(null);
        ft.commit();
        next.hide();
        prev.hide();

    }

}