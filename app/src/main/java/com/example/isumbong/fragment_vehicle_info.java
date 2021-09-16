package com.example.isumbong;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class fragment_vehicle_info extends Fragment {

    Button btn_vehicle;
    ImageView img_vehicle;
    Uri uri  ;
    Spinner vehicle;
    static EditText plate;
    static String Plate;
    static Bundle b2;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;


    public fragment_vehicle_info() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vehicle_info, container, false);


        btn_vehicle = view.findViewById(R.id.button_vehicle);
        img_vehicle = view.findViewById(R.id.imageView_vehicle);
        plate = view.findViewById(R.id.Text_plate);
        Plate = plate.getText().toString();
//        edit = prefs.edit().putString("plate",Plate);
////        edit.apply();


        //check pref
        //String p = prefs.getString("plate",null);

        String[] type = {"Select Vehicle Type","3 WHEELER", "4 WHEELER", "MOTORCYCLE", "BUS", "TRUCK"};

        vehicle = view.findViewById(R.id.spinner_type);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1, type);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicle.setAdapter(myAdapter);

        vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selecteditem = adapterView.getItemAtPosition(i).toString();
                   // Toast.makeText(getActivity().getBaseContext(), ""+selecteditem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ActivityResultLauncher<Intent> launcher_v =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        uri = result.getData().getData();
                        img_vehicle.setImageURI(uri);
                        // Use the uri to load the image
                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });


        btn_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(requireActivity())
                        .crop()
                        .cropSquare()
                        .maxResultSize(512, 512, true)
                        .createIntentFromDialog(new Function1() {
                            public Object invoke(Object var1) {
                                this.invoke((Intent) var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it) {
                                Intrinsics.checkNotNullParameter(it, "it");
                                launcher_v.launch(it);
                            }
                        });
            }
        });

        return view;
    }
}