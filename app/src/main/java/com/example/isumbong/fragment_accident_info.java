package com.example.isumbong;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.drjacky.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;


public class fragment_accident_info extends Fragment {

    ImageView accident;
    ImageView license;

//    static Uri Img_accident;


    int CAMERA_ACTION_CODE = 1;
    static Uri img_accident;
    static Uri img_license;
    Button btn_accident;
    Button btn_license;

    static EditText text_license;
    static String Text_license;

    static String strUriAccident;
    static String strUriLicense;

     static INPUTS inputs;

    public fragment_accident_info() {
        // Required empty public constructor
    }

    public fragment_accident_info(String Text_license) {

        this.Text_license = Text_license;
    }
//    public fragment_accident_info(String img_accident, String img_license){
//        this.img_accident = img_accident;
//        this.img_license = img_license;
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_accident_info, container, false);
//        accident = view.findViewById(R.id.)
      //  btn_license = view.findViewById(R.id.button_license);

        btn_accident = view.findViewById(R.id.button_accident);
        accident = view.findViewById(R.id.imageView_accident);
        btn_license = view.findViewById(R.id.button_license);
        license = view.findViewById(R.id.imageView_license);
        text_license = view.findViewById(R.id.Text_license);


        //setImg
        if (img_accident != null) {
            accident.setImageURI(img_accident);
        }
        if(img_license != null){
            license.setImageURI(img_license);
        }

        String check = getActivity().getIntent().getStringExtra("edit");
        try {
            if(check != null) {
                setEditInfo();
            }
            else{
                //setText
                text_license.setText(Text_license);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //image upload
        //accident
        ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                         img_accident = result.getData().getData();
                         accident.setImageURI(img_accident);
                         //set img uri to string
                        strUriAccident = img_accident.toString();
                    }
                    else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });
//sample uri
//        database db = new database(requireContext());
//        license.setImageURI(Uri.parse(inputs.setImg_accident()));



        //license
        ActivityResultLauncher<Intent> launcher_l =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        img_license = result.getData().getData();
                        license.setImageURI(img_license);
                        strUriLicense = img_license.toString();
                        // Use the uri to load the image
                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });


        btn_accident.setOnClickListener(new View.OnClickListener() {
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
                                launcher.launch(it);
                            }
                        });
            }
        });

        btn_license.setOnClickListener(new View.OnClickListener() {
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

                            public final void invoke(@NotNull Intent i) {
                                Intrinsics.checkNotNullParameter(i, "i");
                                launcher_l.launch(i);
                            }
                        });
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        Text_license = db.getLicenseNumber(ID);
        text_license.setText(Text_license);
        if (img_accident == null) {
            strUriAccident = db.getAccidentImg(ID);
            accident.setImageURI(Uri.parse(strUriAccident));
        }
        if (img_license == null) {
            strUriLicense = db.getLicenseImg(ID);
            license.setImageURI(Uri.parse(strUriLicense));
        }
    }
}