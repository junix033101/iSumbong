package com.example.isumbong;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class fragment_victim_details extends Fragment {

    ListView mListView;
    String repeat = fragment_novictims.vnum;
    CardView cardView;
//    EditText [] victim_name;
//    EditText [] victim_age;
//    RadioButton male;
//    RadioButton female;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_victim_details, container, false);

        mListView = view.findViewById(R.id.list_view);
        cardView = view.findViewById(R.id.card_view);
      //  victim_name = new EditText[Integer.parseInt(repeat)];


        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);
        getActivity().getIntent();

        return view;

    }
    public class MyAdapter extends BaseAdapter{

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
            if (String.valueOf(i+1) != repeat){
                num.setText(String.valueOf(i+1));
            }

            return convertView;
        }
    }
}