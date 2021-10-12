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
    TextView num ;
    TextView b_num ;
    EditText b_age ;
    RadioButton b_female ;
    RadioButton b_male ;
    String bnum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_victim_details, container, false);

        mListView = view.findViewById(R.id.list_view);
        cardView = view.findViewById(R.id.card_view);
      //  victim_name = new EditText[Integer.parseInt(repeat)];
        String check = getActivity().getIntent().getStringExtra("edit");
        int id = getActivity().getIntent().getIntExtra("id",0);
        database db = new database(requireContext());
        victims = new ArrayList<Victim>(Integer.parseInt(fragment_novictims.vnum));
        for(int i=0;i<Integer.parseInt(repeat);i++){
            victims.add(new Victim("Name","Age","Gender"));
        }
        if(check != null){
            victims =db.getVictimsInfo(id);
        }

        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter( adapter);


        return view;

    }
    public class MyAdapter extends BaseAdapter {
        EditText et;
        RadioButton rbtn_male;
        RadioButton rbtn_female;

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
            et =convertView.findViewById(R.id.Text_victim_age);
            EditText name =convertView.findViewById(R.id.Text_victim_name);
            rbtn_male = convertView.findViewById(R.id.radioButton_male);
            rbtn_female= convertView.findViewById(R.id.radioButton_female);


            et.setText(victims.get(i).getAge());
            name.setText(victims.get(i).getName());
            //set radio button
            if(victims.get(i).getGender().equals("male"))
                rbtn_male.setChecked(true);
            else if(victims.get(i).getGender().equals("female"))
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
                     b_num = view1.findViewById(R.id.textview_builder_num);
                    b_name = view1.findViewById(R.id.Text_builder_name);
                    b_age = view1.findViewById(R.id.Textview_builder_age);
                     b_female = view1.findViewById(R.id.radioButton_builder_female);
                     b_male = view1.findViewById(R.id.radioButton_builder_male);
                    bnum = num.getText().toString();

                    //set text
                    String check = getActivity().getIntent().getStringExtra("edit");
                    try {
                            //set edit text to input
                            if(!name.getText().toString().matches("Name")&&
                                    !et.getText().toString().matches("Age")){
                                b_name.setText((victims.get(i).getName()));
                                b_age.setText((victims.get(i).getAge()));
                            }
                            //set radio button
                            if(victims.get(i).getGender().equals("male"))
                                b_male.setChecked(true);
                            else if(victims.get(i).getGender().equals("female"))
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
                                String check = getActivity().getIntent().getStringExtra("edit");
                                    victim = new Victim(b_name.getText().toString(), b_age.getText().toString(), gender);
                                    victims.set(Integer.parseInt(num.getText().toString()) - 1, victim);
//                          Toast.makeText(getActivity(), victims.get(Integer.parseInt(num.getText().toString())-1).toString(), Toast.LENGTH_SHORT).show(); pang debug

                                    if (b_name.getText().toString().matches("") || b_age.getText().toString().matches("") || b_male.getText().toString().matches("")
                                            || b_male.getText().toString().matches("")) {
                                        Toast.makeText(getActivity(), "There is an empty field!", Toast.LENGTH_SHORT).show();
                                    } else
                                        alertDialog.dismiss();

                                    MyAdapter adapter = new MyAdapter();
                                    mListView.setAdapter(adapter);

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            return convertView;

        }
        private void setEditInfo(View convertView, int i){
            int id = getActivity().getIntent().getIntExtra("id",0);
            Boolean check = getActivity().getIntent().getExtras().getBoolean("for_update");
            if(check){
//                setNum(id,convertView,i);
            }
        }

        private void setNum(int ID,View convertView,int i){
            EditText name =convertView.findViewById(R.id.Text_victim_name);
            database db = new database(requireContext());
            ArrayList<Victim> victim = db.getVictimsInfo(ID);
            name.setText(victim.get(i).getName());
            et.setText(victim.get(i).getAge());
            if(victim.get(i).getGender().equals("male"))
                rbtn_male.setChecked(true);
            else if(victim.get(i).getGender().equals("female"))
                rbtn_female.setChecked(true);
        }

        private void setBuilder(int i){
            int id = getActivity().getIntent().getIntExtra("id",0);
            Boolean check = getActivity().getIntent().getExtras().getBoolean("for_update");
            if(check){
                setBuilderInfo(id,i);
            }
        }

        private void setBuilderInfo(int id, int i ){


            database db = new database(requireContext());
            ArrayList<Victim> victim = db.getVictimsInfo(id);
            b_name.setText(victim.get(i).getName());
            b_age.setText(victim.get(i).getAge());
            if(victim.get(i).getGender().equals("male"))
                b_male.setChecked(true);
            else if(victim.get(i).getGender().equals("female"))
                b_female.setChecked(true);
            b_num.setText(bnum);


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

        }

    }

}