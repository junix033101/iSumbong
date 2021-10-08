package com.example.isumbong;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class fragment_victim_details extends Fragment {

    ListView mListView;
    String repeat = com.example.isumbong.fragment_novictims.vnum;
    CardView cardView;
    static ArrayList<String> inputAge = new ArrayList<String>();
    static ArrayList<Victim> victims;
    Victim victim;
    String gender;

    public static EditText b_name;
//    EditText [] victim_name;
//    EditText [] victim_age;
//    RadioButton male;
//    RadioButton female;
EditText et;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_victim_details, container, false);

        mListView = view.findViewById(R.id.list_view);
        cardView = view.findViewById(R.id.card_view);
      //  victim_name = new EditText[Integer.parseInt(repeat)];
        victims = new ArrayList<Victim>(Integer.parseInt(fragment_novictims.vnum));
        for(int i=0;i<Integer.parseInt(repeat);i++){
            victims.add(new Victim("Name","Age","Gender"));
        }
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter( adapter);


        return view;

    }
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Integer.parseInt(repeat);
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            convertView = getLayoutInflater().inflate(R.layout.card_view, viewGroup, false );
            //initialization
            TextView num = convertView.findViewById(R.id.view_num);
            EditText et =convertView.findViewById(R.id.Text_victim_age);
            EditText name =convertView.findViewById(R.id.Text_victim_name);
            RadioButton rbtn_male = convertView.findViewById(R.id.radioButton_male);
            RadioButton rbtn_female = convertView.findViewById(R.id.radioButton_female);

            //set text
            et.setText(victims.get(i).getAge());
            name.setText(victims.get(i).getName());

            //set radio button
            if(victims.get(i).getGender() == "male")
                rbtn_male.setChecked(true);
            else if(victims.get(i).getGender() == "female")
                rbtn_female.setChecked(true);


            //set to unclickable
            et.setEnabled(false);
            name.setEnabled(false);
            rbtn_male.setEnabled(false);
            rbtn_female.setEnabled(false);




            //set victim numbers
            if (String.valueOf(i+1) != repeat){
                num.setText(String.valueOf(i+1));
            }
//            Toast.makeText(getActivity(), et.getText().toString(), Toast.LENGTH_SHORT).show();
            inputAge.add(et.getText().toString());


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView num = view.findViewById(R.id.view_num);
//                    Toast.makeText(getActivity(), num.getText().toString(), Toast.LENGTH_SHORT).show();

                    //dialog pop up
                    LayoutInflater inflater = requireActivity().getLayoutInflater();
                    View view1 = inflater.inflate(R.layout.builder_victim_info,null);
                    TextView b_num = view1.findViewById(R.id.textview_builder_num);
                    b_name = view1.findViewById(R.id.Text_builder_name);
                    EditText b_age = view1.findViewById(R.id.Textview_builder_age);
                    RadioButton b_female = view1.findViewById(R.id.radioButton_builder_female);
                    RadioButton b_male = view1.findViewById(R.id.radioButton_builder_male);
                    String bnum = num.getText().toString();

                    //set edit text to input
                    if(!name.getText().toString().matches("Name")&&
                            !et.getText().toString().matches("Age")){
                        b_name.setText((victims.get(i).getName()));
                        b_age.setText((victims.get(i).getAge()));
                    }
                    //set radio button
                    if(victims.get(i).getGender() == "male")
                        b_male.setChecked(true);
                    else if(victims.get(i).getGender() == "female")
                        b_female.setChecked(true);


                    b_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b_male.isChecked()){
                                gender = "male";
                            }
                        }
                    });
                    b_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b_female.isChecked()){
                                gender = "female";
                            }
                        }
                    });


                    b_num.setText(bnum);

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(requireActivity());
                    builder1.setView(view1)
                            .setTitle("INPUT DETAILS")
                            .setCancelable(false);

                    builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder1.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog alertDialog = builder1.create();
                    alertDialog.show();
                    //to not close the dialog
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            victim= new Victim(b_name.getText().toString(),b_age.getText().toString(),gender);
                            victims.set(Integer.parseInt(num.getText().toString())-1,victim);
//                          Toast.makeText(getActivity(), victims.get(Integer.parseInt(num.getText().toString())-1).toString(), Toast.LENGTH_SHORT).show(); pang debug

                            if(b_name.getText().toString().matches("")||b_age.getText().toString().matches("")|| b_male.getText().toString().matches("")
                            || b_male.getText().toString().matches("")){
                                Toast.makeText(getActivity(), "There is an empty field!", Toast.LENGTH_SHORT).show();
                            }
                            else
                                alertDialog.dismiss();

                            MyAdapter adapter = new MyAdapter();
                            mListView.setAdapter(adapter);

                        }
                    });
                }
            });

            return convertView;

        }
    }
}