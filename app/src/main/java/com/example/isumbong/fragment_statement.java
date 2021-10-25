package com.example.isumbong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class fragment_statement extends Fragment {

       static EditText statement_field;
       static String Statement;


    public fragment_statement() {
        // Required empty public constructor
    }
    public fragment_statement(String Statement) {

        this.Statement = Statement;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statement, container, false);

        statement_field = view.findViewById(R.id.editTextTextMultiLine_statement);

        String check = getActivity().getIntent().getStringExtra("edit");
        try {
            if(check!=null) {
                setEditInfo();
            }
            else{
                statement_field.setText(Statement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        Statement = db.getStatement(ID);
        statement_field.setText(Statement);
    }

}