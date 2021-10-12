package com.example.isumbong;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.github.drjacky.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class fragment_vehicle_info extends Fragment {

    Button btn_vehicle;
    Button btn_or;

    ImageView vehicle;
    ImageView or;
    static Uri img_vehicle;
    static Uri img_or;

    static Spinner spnr_vehicle;

    static EditText plate;
    static String Plate;

    static String strUriVehicle;
    static String strUriOr;

    static String VehicleType;
    static int SpinnerPos;
    String selecteditem;
    ArrayAdapter<String> myAdapter;



    public fragment_vehicle_info() {
        // Required empty public constructor
    }
    public fragment_vehicle_info(String Plate) {

        this.Plate = Plate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vehicle_info, container, false);


        btn_vehicle = view.findViewById(R.id.button_vehicle);
        btn_or = view.findViewById(R.id.button_OR);
        vehicle = view.findViewById(R.id.imageView_vehicle);
        or = view.findViewById(R.id.imageView_OR);
        plate = view.findViewById(R.id.Text_plate);



        String[] type = {"Select Vehicle Type","3 WHEELER", "4 WHEELER", "MOTORCYCLE", "BUS", "TRUCK"};

        spnr_vehicle = view.findViewById(R.id.spinner_type);
         myAdapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1, type);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_vehicle.setAdapter(myAdapter);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("FileName", Context.MODE_PRIVATE);
        int spinnerValue = sharedPref.getInt("userChoiceSpinner",-1);
        if(spinnerValue != -1) {
            // set the selected value of the spinner
            spnr_vehicle.setSelection(spinnerValue);
        }

        //set img
        if(img_vehicle != null){
            vehicle.setImageURI(img_vehicle);

        }
        if(img_or != null){
            or.setImageURI(img_or);
        }


        String check = getActivity().getIntent().getStringExtra("edit");
        try {
            if(check != null) {
                setEditInfo();
            }
            else{
                plate.setText(Plate);


                spnr_vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selecteditem = adapterView.getItemAtPosition(i).toString();

                        SpinnerPos = spnr_vehicle.getSelectedItemPosition();
                        SharedPreferences.Editor prefEditor = sharedPref.edit();
                        prefEditor.putInt("userChoiceSpinner",SpinnerPos);
                        prefEditor.apply();

                        if(selecteditem.matches("Select Vehicle Type")){
                            selecteditem = "";
                        }
                        else{
                            VehicleType = selecteditem;
                        }


                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActivityResultLauncher<Intent> launcher_v =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        img_vehicle = result.getData().getData();
                        vehicle.setImageURI(img_vehicle);
                        strUriVehicle = img_vehicle.toString();
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

        ActivityResultLauncher<Intent> launcher_or =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        img_or = result.getData().getData();
                        or.setImageURI(img_or);
                        strUriOr = img_or.toString();
                        // Use the uri to load the image
                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });


        btn_or.setOnClickListener(new View.OnClickListener() {
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
                                launcher_or.launch(it);
                            }
                        });
            }
        });

        return view;
    }

    private void setEditInfo(){
        int id = getActivity().getIntent().getIntExtra("id",0);
        Boolean check = getActivity().getIntent().getExtras().getBoolean("for_update");
        if(check){
            setNum(id);
        }
    }

    private void setNum(int ID){
        database db = new database(requireContext());
        plate.setText(db.getPlateNumber(ID));
        if (img_vehicle == null) {
            strUriVehicle = db.getVehicleImg(ID);
            vehicle.setImageURI(Uri.parse(strUriVehicle));
        }
        if (img_or == null) {
            strUriOr = db.getOrImg(ID);
            or.setImageURI(Uri.parse(strUriOr));
        }
        VehicleType = db.getVehicleType(ID);
        SpinnerPos = myAdapter.getPosition(VehicleType);
        spnr_vehicle.setSelection(SpinnerPos);
    }
}