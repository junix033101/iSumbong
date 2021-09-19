package com.example.isumbong;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class builder_confirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder_confirm);



        TextView name = findViewById(R.id.textView_info);
        TextView number = findViewById(R.id.textView_number);
        number.setText("asdasdasdasd");
        Toast.makeText(this, number.getText().toString(), Toast.LENGTH_SHORT).show();
        name.setText("fragment_victim_details.b_name.getText().toString()");
    }
}
