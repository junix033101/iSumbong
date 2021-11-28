package com.example.isumbong;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class admin_signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        EditText user = findViewById(R.id.Text_user);
        EditText pass = findViewById(R.id.Text_password);
        EditText repass = findViewById(R.id.Text_repassword);
        EditText name = findViewById(R.id.Text_name);
        EditText id = findViewById(R.id.Text_ID);
        EditText email = findViewById(R.id.Text_email);


    }
}