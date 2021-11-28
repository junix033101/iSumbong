package com.example.isumbong;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class admin_login extends AppCompatActivity {

    static String admin_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        ///for code
        LayoutInflater inflater = admin_login.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.builder_admin_code,null);
        EditText code = view.findViewById(R.id.Text_admin_code);

        AlertDialog.Builder builder = new AlertDialog.Builder(admin_login.this);
        builder.setView(view)
                .setTitle("Enter Code")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent (admin_login.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    admin_code = code.getText().toString();
                    Toast.makeText(admin_login.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

            }
        });


        //redirect signup
        Button signup = findViewById(R.id.button_signup1);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(admin_login.this, admin_signup.class);
                startActivity(intent.putExtras(getIntent()));
            }
        });


        Button signin = findViewById(R.id.button_sign_in);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(admin_login.this, admin_homepage.class);
                    startActivity(intent);
            }
        });



    }

}